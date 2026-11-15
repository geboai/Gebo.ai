# syntax=docker/dockerfile:1
# ===========================================================================
# geboai/platform:3.0 - hardened Java 25 (LTS) runtime base
#
# The base of geboai/gebo.ai and of every Jib microservice image
# (gebo.microservices.apps.parent, `docker` profile).
#
# Built with the same recipe as Canonical's ubuntu/jre rock (github.com/
# canonical/jre-rock, jre/21-jre-24.04/rockcraft.yaml): chisel slices of Ubuntu
# 24.04 LTS packages for the OS layer, plus a jlinked OpenJDK runtime made from
# Ubuntu's own openjdk-25 build. The result has no shell, no package manager, no
# coreutils and no curl. It runs as the non-root uid 10001.
#
# Why not `FROM ubuntu/jre:25-...` directly? It was evaluated and rejected:
#   * the 25 rock ships the whole JDK (javac, jshell, jlink, jpackage...) plus
#     ~730 MB of native .debuginfo: 356 MB compressed against 56 MB for the 21
#     rock, and it runs as root;
#   * like every ubuntu/jre rock, it has NO compiled locale. The JVM then falls
#     back to ASCII for file names (sun.jnu.encoding=ANSI_X3.4-1968), and every
#     non-ASCII name - an accented Italian document on a Windows SMB share or an
#     NTFS volume, Japanese, emoji - throws InvalidPathException. Verified on
#     ext4, ntfs3 and CIFS mounts. -Dsun.jnu.encoding=UTF-8 does NOT help: the
#     JDK ignores it. Only a UTF-8 locale does, which is why libc-bin_locale
#     (C.utf8) is cut below and LANG/LC_ALL are set to C.UTF-8.
#
# Swap to the Canonical rock when it is lean again: the runtime contract that
# derived images rely on is only JAVA_HOME, `java` on PATH, /usr/bin/tini,
# LANG=C.UTF-8, uid 10001 and the /opt/gebo.ai layout.
# ===========================================================================
ARG UBUNTU_RELEASE=24.04

FROM ubuntu:${UBUNTU_RELEASE} AS builder
ARG UBUNTU_RELEASE
# Set by buildx (amd64 | arm64). Matches both the chisel release asset names and
# the Debian arch suffix of /usr/lib/jvm/java-<N>-openjdk-<arch>.
ARG TARGETARCH
ARG CHISEL_VERSION=v1.5.1
ARG JAVA_MAJOR=25
ARG GEBO_UID=10001
# The JRE module set: Canonical's ubuntu/jre:21 list (java.se + the jdk.* runtime
# modules) plus jdk.incubator.vector (Lucene) and jdk.jcmd (jcmd/jstack/jmap,
# the only in-container diagnostics an image without a shell has). Checked with
# jdeps against every jar in the monolith's bootable jar: the only modules it
# references that are left out are jdk.compiler (referenced by lombok, a
# compile-time annotation processor) and jdk.xml.dom (removed from JDK 25).
ARG JRE_MODULES=java.se,java.smartcardio,jdk.charsets,jdk.crypto.cryptoki,jdk.crypto.ec,jdk.dynalink,jdk.httpserver,jdk.incubator.vector,jdk.jcmd,jdk.jdwp.agent,jdk.jfr,jdk.jsobject,jdk.localedata,jdk.management,jdk.management.agent,jdk.management.jfr,jdk.naming.dns,jdk.naming.rmi,jdk.net,jdk.nio.mapmode,jdk.sctp,jdk.security.auth,jdk.security.jgss,jdk.unsupported,jdk.zipfs
ENV DEBIAN_FRONTEND=noninteractive

# Build-only tools. None of this reaches the final image except what is copied
# into /rootfs below: the JDK is only used to jlink the runtime, ca-certificates-
# java only generates the Java trust store, tini only provides tini-static, zstd
# only reads chisel's manifest. The apt lists are kept: this is a throwaway stage
# and the scanner metadata step below reads them.
RUN apt-get update \
 && apt-get install -y --no-install-recommends \
      binutils ca-certificates ca-certificates-java curl tini zstd \
      openjdk-${JAVA_MAJOR}-jdk-headless

# chisel, pinned and checksum-verified (it verifies the Ubuntu archive
# signatures of every package it slices on its own).
RUN set -eux; \
    f="chisel_${CHISEL_VERSION}_linux_${TARGETARCH}.tar.gz"; \
    cd /tmp; \
    curl -fsSLO "https://github.com/canonical/chisel/releases/download/${CHISEL_VERSION}/${f}"; \
    curl -fsSLO "https://github.com/canonical/chisel/releases/download/${CHISEL_VERSION}/${f}.sha384"; \
    sha384sum -c "${f}.sha384"; \
    tar -xzf "${f}" -C /usr/local/bin chisel; \
    rm -f "${f}" "${f}.sha384"

# The OS layer. Same slice set as the ubuntu/jre rock (glibc, libgcc, libstdc++,
# zlib, pcsclite, nss) plus:
#   ca-certificates_data  /etc/ssl/certs/ca-certificates.crt for native TLS users
#   libc-bin_locale       the C.utf8 locale - UTF-8 file names, see the header
#   libc-bin_nsswitch     /etc/nsswitch.conf, so /etc/hosts is read before DNS
# libstdc++/libgcc are also what the native libraries bundled in the app need:
# onnxruntime, DJL tokenizers, netty epoll/tcnative/quic, conscrypt and JNA all
# load on this layer (verified as uid 10001).
RUN set -eux; \
    mkdir /rootfs; \
    chisel cut --release "ubuntu-${UBUNTU_RELEASE}" --root /rootfs \
      base-files_base base-files_chisel base-files_release-info base-passwd_data \
      ca-certificates_data libc-bin_locale libc-bin_nsswitch \
      libc6_libs libgcc-s1_libs libstdc++6_libs zlib1g_libs \
      libpcsclite1_libs libnss3_libs

# Make the image's packages visible to vulnerability scanners and SBOM tools
# (Trivy, Grype/Syft, Docker Scout, BuildKit's --sbom). They read dpkg's
# database, which a chiselled root does not have: without this they see ZERO
# packages and report a clean image whatever glibc, nss or openssl it carries
# (verified with Trivy). One stanza per package chisel cut, at the exact version
# and architecture its manifest records, in the distroless layout
# (/var/lib/dpkg/status.d/<name>). Source: lets advisories filed against the
# source package (glibc, nss, openssl...) match. Plus openjdk-<N>-jre-headless,
# the package the jlinked runtime below is built from.
RUN set -eux; \
    status=/rootfs/var/lib/dpkg/status.d; \
    mkdir -p "$status"; \
    zstd -dc /rootfs/var/lib/chisel/manifest.wall \
      | sed -nE 's/.*"kind":"package","name":"([^"]+)","version":"([^"]+)".*"arch":"([^"]+)".*/\1 \2 \3/p' \
      | while read -r name version arch; do \
          src="$(apt-cache show "$name" | sed -n 's/^Source: \([^ ]*\).*/\1/p' | head -n1)"; \
          { printf 'Package: %s\nStatus: install ok installed\nArchitecture: %s\nVersion: %s\n' "$name" "$arch" "$version"; \
            if [ -n "$src" ]; then printf 'Source: %s\n' "$src"; fi; } > "$status/$name"; \
        done; \
    dpkg-query -W -f='Package: ${Package}\nStatus: install ok installed\nArchitecture: ${Architecture}\nVersion: ${Version}\nSource: ${source:Package}\n' \
      "openjdk-${JAVA_MAJOR}-jre-headless" > "$status/openjdk-${JAVA_MAJOR}-jre-headless"; \
    ls "$status"

# The Java runtime, the non-root user and the Gebo.ai layout.
RUN set -eux; \
    jdk="/usr/lib/jvm/java-${JAVA_MAJOR}-openjdk-${TARGETARCH}"; \
    "$jdk/bin/jlink" --add-modules "${JRE_MODULES}" \
      --strip-debug --no-header-files --no-man-pages \
      --output /rootfs/opt/java/openjdk; \
    # Ubuntu's JDK keeps native debug symbols in separate .debuginfo files that
    # jlink copies over (that is the 730 MB in the stock 25 rock).
    find /rootfs/opt/java/openjdk -name '*.debuginfo' -delete; \
    # Trust store: Ubuntu's Mozilla CA bundle as converted by ca-certificates-java,
    # i.e. what /usr/lib/jvm/*/lib/security/cacerts points at on a normal install.
    cp -L /etc/ssl/certs/java/cacerts /rootfs/opt/java/openjdk/lib/security/cacerts; \
    mkdir -p /rootfs/usr/bin; \
    ln -s ../../opt/java/openjdk/bin/java /rootfs/usr/bin/java; \
    # tini as PID 1 for the derived images: forwards SIGTERM to the JVM and reaps
    # orphans (the app spawns stdio MCP servers). Static, so it needs nothing.
    cp /usr/bin/tini-static /rootfs/usr/bin/tini; \
    echo "gebo:x:${GEBO_UID}:${GEBO_UID}:Gebo.ai:/home/gebo:/usr/sbin/nologin" >> /rootfs/etc/passwd; \
    echo "gebo:x:${GEBO_UID}:" >> /rootfs/etc/group; \
    # ---------------------------------------------------------------------
    # The Gebo.ai runtime layout, created HERE so every image built on this base
    # already has it - and owned by uid 10001 so the app can write to it. The
    # microservice images (Jib) only SET GEBO_HOME / GEBO_WORK_DIRECTORY - Jib
    # cannot run a mkdir - so without this the work directory would not exist,
    # and GLocalPersistentFolderDiscoveryServiceImpl throws ("The configurated
    # work directory ... does not exists or is not a directory") the first time
    # any content endpoint is materialized. A fresh named volume mounted on one
    # of these paths is seeded from the image, ownership included.
    #
    #   home    GEBO_HOME            - the application's own home area
    #   work    GEBO_WORK_DIRECTORY  - DURABLE user data: chat SESSIONS_AREA,
    #                                  per-endpoint content mirrors, the document
    #                                  cache, uploads, Google Workspace OAuth
    #                                  tokens. Mongo only INDEXES it; the bytes
    #                                  live here, so losing it leaves dangling
    #                                  references.
    #   config  Spring's additional config location - mounted by the deployment,
    #           deliberately NOT a volume anywhere (a stale volume would mask the
    #           application.yml shipped by a newer image on upgrade). Root-owned:
    #           the app only reads it.
    #   logs    security-log.jsonl, the append-only GDPR Art.32 / NIS2 Art.21
    #           audit trail (logback's ${GEBO_LOG_BASE:-logs} under WORKDIR
    #           /opt/gebo.ai), plus gc.log
    #   shares  host filesystem shares exposed to the filesystem content handler
    #           (read-only mounts in every shipped compose file)
    #
    # No VOLUME is declared here on purpose: a base image should describe the
    # layout, not impose an anonymous volume on every derived image. The concrete
    # images (dockers/gebo.ai, dockers/easyinstall.gebo.ai) and the Jib `docker`
    # profile declare their own. See dockers/PERSISTENCE.md.
    # ---------------------------------------------------------------------
    mkdir -p /rootfs/home/gebo \
             /rootfs/opt/gebo.ai/home \
             /rootfs/opt/gebo.ai/work \
             /rootfs/opt/gebo.ai/config \
             /rootfs/opt/gebo.ai/logs \
             /rootfs/opt/gebo.ai/shares; \
    chown "${GEBO_UID}:${GEBO_UID}" /rootfs/home/gebo /rootfs/opt/gebo.ai \
          /rootfs/opt/gebo.ai/home /rootfs/opt/gebo.ai/work \
          /rootfs/opt/gebo.ai/logs /rootfs/opt/gebo.ai/shares

FROM scratch
COPY --from=builder /rootfs/ /
LABEL org.opencontainers.image.title="Gebo.ai platform" \
      org.opencontainers.image.version="3.0" \
      org.opencontainers.image.description="Hardened Java 25 runtime base of the Gebo.ai images: chiselled Ubuntu 24.04 + jlinked Ubuntu OpenJDK 25, no shell, non-root" \
      org.opencontainers.image.vendor="Gebo.ai" \
      org.opencontainers.image.source="https://github.com/geboai/Gebo.ai" \
      org.opencontainers.image.licenses="MPL-2.0"
ENV JAVA_HOME=/opt/java/openjdk \
    PATH=/opt/java/openjdk/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin \
    LANG=C.UTF-8 \
    LC_ALL=C.UTF-8
# Numeric, so Kubernetes' runAsNonRoot can verify it without reading /etc/passwd.
USER 10001:10001
WORKDIR /opt/gebo.ai
