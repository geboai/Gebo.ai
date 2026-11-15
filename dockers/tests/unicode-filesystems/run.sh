#!/usr/bin/env bash
#
# run.sh - can a Gebo.ai image read Unicode file names on mounted Windows-style
# filesystems?
#
# The images must read shares mounted from Windows: Docker Desktop host paths, SMB/CIFS
# shares of Windows file servers, NTFS volumes. Whether the JVM can open a file
# named "Fattura n° 12 – perché è così.txt", "会議の議事録.txt" or
# "Report 🚀 Q4 ✅.txt" depends only on the image's locale: with no UTF-8 locale the
# JDK encodes file names as ASCII (sun.jnu.encoding=ANSI_X3.4-1968) and throws
# InvalidPathException. -Dsun.jnu.encoding=UTF-8 does NOT help (the JDK ignores it),
# which is why this is a test of the IMAGE, not of the application.
#
# The same fixture (names.txt: accents, German, French, Spanish, CJK, Korean,
# Cyrillic, Greek, Arabic, Hebrew, Devanagari, emoji/surrogate pairs, a decomposed
# accent, Windows-legal punctuation) is put on four filesystems, each mounted
# read-only at /opt/gebo.ai/shares of the image under test:
#
#   ext4          a host directory (bind mount) - what Docker Desktop also presents
#   ntfs          a real NTFS volume, kernel ntfs3 driver, on a loop device
#   smb-utf8      a Samba share mounted with the kernel CIFS client, iocharset=utf8
#   smb-default   the same share with the kernel's default iocharset
#
# UnicodeFsTest walks the tree, opens every file and checks it contains its own name,
# then looks every expected name up directly - with the image's own java, as the
# image's own user.
#
# Usage:   dockers/tests/unicode-filesystems/run.sh <image> [<image> ...]
#   e.g.   dockers/tests/unicode-filesystems/run.sh geboai/gebo.ai geboai/platform:3.0
# Exit status is non-zero if any image fails on any filesystem.
#
# Needs: a Linux Docker host (the NTFS and SMB rows use privileged helper containers,
# a loop device and the kernel's ntfs3/cifs modules; a row that cannot be set up is
# reported as SKIP). Images with a `java` on PATH are tested by overriding the
# entrypoint. easyinstall runs the test as root unless you pass --user gebo via
# EXTRA_RUN_ARGS.
#
set -euo pipefail
HERE="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
[ $# -ge 1 ] || { sed -n '2,/^set -euo/p' "$0" | sed '$d'; exit 2; }

RUN_ID="gebo-unitest-$$"
WORK="$(mktemp -d "${TMPDIR:-/tmp}/${RUN_ID}.XXXXXX")"
# mktemp makes it 0700: the images run as uid 10001 and must be able to read it
chmod 755 "$WORK"
HELPER_IMAGE="ubuntu:24.04"
JDK_IMAGE="ubuntu/jdk:25-26.04_stable"
LOOP=""
cleanup() {
  docker rm -f "${RUN_ID}-smb" >/dev/null 2>&1 || true
  for v in ntfs smb-utf8 smb-default; do docker volume rm -f "${RUN_ID}-$v" >/dev/null 2>&1 || true; done
  if [ -n "$LOOP" ]; then
    docker run --rm --privileged -v /dev:/dev "$HELPER_IMAGE" losetup -d "$LOOP" >/dev/null 2>&1 || true
  fi
  rm -rf -- "${WORK:?}"
}
trap cleanup EXIT

echo ">> compiling UnicodeFsTest (Java 21 bytecode, runs on any 21+ image)"
cp "$HERE/UnicodeFsTest.java" "$HERE/names.txt" "$HERE/mkfixture.sh" "$WORK/"
docker run --rm --user "$(id -u):$(id -g)" --entrypoint javac -v "$WORK:/t" -w /t "$JDK_IMAGE" \
  --release 21 -encoding UTF-8 UnicodeFsTest.java

declare -A MOUNT
echo ">> ext4: host directory"
mkdir -p "$WORK/ext4" && "$WORK/mkfixture.sh" "$WORK/ext4" "$WORK/names.txt"
MOUNT[ext4]="$WORK/ext4"

echo ">> ntfs: loop device, kernel ntfs3"
truncate -s 64M "$WORK/ntfs.img"
if docker run --rm --privileged -v /dev:/dev -v "$WORK:/work" "$HELPER_IMAGE" bash -c '
     set -e
     apt-get update -qq >/dev/null && apt-get install -y -qq ntfs-3g >/dev/null 2>&1
     mkntfs -F -Q -L GEBOTEST /work/ntfs.img >/dev/null 2>&1
     loop=$(losetup -f --show /work/ntfs.img); echo "$loop" > /work/loopdev
     mkdir -p /mnt/n && mount -t ntfs3 -o iocharset=utf8 "$loop" /mnt/n
     /work/mkfixture.sh /mnt/n /work/names.txt
     umount /mnt/n' >/dev/null 2>&1; then
  LOOP="$(cat "$WORK/loopdev")"
  docker volume create --driver local --opt type=ntfs3 --opt device="$LOOP" \
    --opt o=ro,iocharset=utf8 "${RUN_ID}-ntfs" >/dev/null
  MOUNT[ntfs]="${RUN_ID}-ntfs"
fi

echo ">> smb: Samba share, kernel CIFS client"
docker run -d --name "${RUN_ID}-smb" -v "$WORK:/work:ro" "$HELPER_IMAGE" bash -c '
  set -e
  apt-get update -qq >/dev/null && DEBIAN_FRONTEND=noninteractive apt-get install -y -qq samba >/dev/null 2>&1
  mkdir -p /srv/share && /work/mkfixture.sh /srv/share /work/names.txt && chmod -R a+rX /srv/share
  printf "[global]\n server min protocol = SMB2_10\n map to guest = Bad User\n unix charset = UTF-8\n disable netbios = yes\n smb ports = 445\n[share]\n path = /srv/share\n read only = yes\n guest ok = yes\n" > /etc/samba/smb.conf
  touch /tmp/ready; exec smbd --foreground --no-process-group' >/dev/null
for _ in $(seq 1 90); do docker exec "${RUN_ID}-smb" test -f /tmp/ready 2>/dev/null && break; sleep 2; done
SMB_IP="$(docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' "${RUN_ID}-smb")"
if docker exec "${RUN_ID}-smb" test -f /tmp/ready 2>/dev/null; then
  sleep 2
  for v in utf8 default; do
    o="addr=$SMB_IP,guest,vers=3.1.1,ro"; [ "$v" = utf8 ] && o="$o,iocharset=utf8"
    docker volume create --driver local --opt type=cifs --opt "device=//$SMB_IP/share" \
      --opt "o=$o" "${RUN_ID}-smb-$v" >/dev/null
    MOUNT[smb-$v]="${RUN_ID}-smb-$v"
  done
fi

status=0
printf '\n%-40s %-12s %s\n' IMAGE FILESYSTEM RESULT
for image in "$@"; do
  for fs in ext4 ntfs smb-utf8 smb-default; do
    if [ -z "${MOUNT[$fs]:-}" ]; then printf '%-40s %-12s %s\n' "$image" "$fs" "SKIP (could not set up)"; continue; fi
    # shellcheck disable=SC2086
    out="$(docker run --rm --entrypoint java -e JAVA_TOOL_OPTIONS= ${EXTRA_RUN_ARGS:-} \
            -v "$WORK:/t:ro" -v "${MOUNT[$fs]}:/opt/gebo.ai/shares:ro" "$image" \
            -cp /t UnicodeFsTest /opt/gebo.ai/shares /t/names.txt 2>&1)" && rc=0 || rc=$?
    line="$(printf '%s\n' "$out" | grep -m1 '^RESULT' || printf '%s\n' "$out" | grep -m1 -E 'FAIL|Exception' || echo "no output")"
    if [ "$rc" -eq 0 ]; then verdict=PASS; else verdict=FAIL; status=1; fi
    printf '%-40s %-12s %s  %s\n' "$image" "$fs" "$verdict" "$line"
  done
done
exit $status
