#!/bin/bash
# 0) Best-effort: raise vm.max_map_count for OpenSearch (needs --privileged or
#    a host sysctl; harmless otherwise - OpenSearch falls back to dev-mode warnings)
sysctl -w vm.max_map_count=262144 >/dev/null 2>&1 || true

# 1) Inizializza Mongo se necessario
/bin/mongo-init.sh
# 2) Avvia supervisord in foreground
exec /usr/bin/supervisord -n -c /etc/supervisor/supervisord.conf
