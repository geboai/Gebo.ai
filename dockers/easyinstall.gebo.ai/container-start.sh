#!/bin/bash
# 1) Inizializza Mongo se necessario
/bin/mongo-init.sh
# 2) Avvia supervisord in foreground
exec /usr/bin/supervisord -n -c /etc/supervisor/supervisord.conf
