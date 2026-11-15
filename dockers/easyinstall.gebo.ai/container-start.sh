#!/bin/bash
# 0) Best-effort: raise vm.max_map_count for OpenSearch (needs --privileged or
#    a host sysctl; harmless otherwise - OpenSearch falls back to dev-mode warnings)
sysctl -w vm.max_map_count=262144 >/dev/null 2>&1 || true

# 0b) Gebo.ai runs as the non-root user gebo (uid 10001) since the Java 25
#     images. Volumes created by an older image are root-owned: hand them over
#     once. Only the top directory is checked, so this is a no-op on every later
#     start and never walks a large work directory twice. /opt/gebo.ai/shares is
#     left alone - it is content to READ, usually a read-only host mount.
for d in /opt/gebo.ai/home /opt/gebo.ai/work /opt/gebo.ai/logs /var/log/gebo.ai; do
  if [ -d "$d" ] && [ "$(stat -c %u "$d")" != "10001" ]; then
    echo "[container-start] re-owning $d for uid 10001"
    chown -R 10001:10001 "$d"
  fi
done

# 1) Inizializza Mongo se necessario
/bin/mongo-init.sh
# 2) Avvia supervisord in foreground
exec /usr/bin/supervisord -n -c /etc/supervisor/supervisord.conf
