#!/bin/bash
set -e

APP_USER="gebo-ai"
APP_GROUP="gebo-ai"
APP_HOME="/home/gebo-ai"
GEBO_HOME="$APP_HOME/home"
GEBO_WORK="/var/gebo-ai/data"
GEBO_CONFIG_FOLDER="/etc/gebo-ai"
GEBO_ADDITIONAL_CONFIG_FILE="$GEBO_CONFIG_FOLDER/application.properties"
GEBO_LOG_BASE="/var/log/gebo-ai"
SERVICE_NAME="gebo-ai.service"
APP_DIR="$APP_HOME/gebo-ai"
SERVICE_DST="/etc/systemd/system/$SERVICE_NAME"

LOG_OUT="/tmp/gebo-ai-install-log.txt"
LOG_ERR="/tmp/gebo-ai-install-err.txt"

echo "Starting Gebo.ai post-install" > "$LOG_OUT"
echo "" > "$LOG_ERR"

echo "Creating user and group if needed..." >> "$LOG_OUT"

# Crea gruppo se non esiste (utile su alcune distro)
if ! getent group "$APP_GROUP" >/dev/null 2>&1; then
    groupadd "$APP_GROUP" >> "$LOG_OUT" 2>> "$LOG_ERR"
fi

# Crea utente se non esiste
if ! id -u "$APP_USER" >/dev/null 2>&1; then
    useradd -r -m -d "$APP_HOME" -s /bin/bash -g "$APP_GROUP" "$APP_USER" >> "$LOG_OUT" 2>> "$LOG_ERR"
fi

echo "Creating folders..." >> "$LOG_OUT"
mkdir -p "$GEBO_HOME" "$GEBO_WORK" "$GEBO_CONFIG_FOLDER" "$GEBO_LOG_BASE" >> "$LOG_OUT" 2>> "$LOG_ERR"
chown -R "$APP_USER:$APP_GROUP" "$GEBO_HOME" "$GEBO_WORK" "$GEBO_LOG_BASE" >> "$LOG_OUT" 2>> "$LOG_ERR"

echo "Writing systemd unit..." >> "$LOG_OUT"

cat > "$SERVICE_DST" <<EOF
[Unit]
Description=Gebo.ai Service
After=network.target mongod.service neo4j.service qdrant.service
Requires=mongod.service neo4j.service qdrant.service

[Service]
User=$APP_USER
Group=$APP_GROUP
Environment=GEBO_HOME=$GEBO_HOME
Environment=GEBO_WORK_DIRECTORY=$GEBO_WORK
Environment=SPRING_CONFIG_ADDITIONAL_LOCATION=file:/etc/gebo-ai/
WorkingDirectory=$APP_HOME
ExecStart=$APP_DIR/bin/gebo-ai
Restart=on-failure
StandardOutput=append:$GEBO_LOG_BASE/service.log
StandardError=append:$GEBO_LOG_BASE/service.err

[Install]
WantedBy=multi-user.target
EOF

chmod 644 "$SERVICE_DST"

echo "Writing default external configuration (if not present)..." >> "$LOG_OUT"

# NON sovrascrivere eventuale file già customizzato dall'admin
if [ ! -f "$GEBO_ADDITIONAL_CONFIG_FILE" ]; then
    cat > "$GEBO_ADDITIONAL_CONFIG_FILE" << 'EOF'
logging.level.root=INFO
logging.level.org.springframework=INFO
logging.level.org.springframework.web=INFO
logging.level.org.springframework.core=INFO
logging.level.org.springframework.core.codec=INFO

server.port=12999
server.compression.enabled=true
server.compression.mime-types=text/html,text/xml,text/plain,text/css,text/javascript,application/javascript,application/json
server.compression.min-response-size=1024
server.http2.enabled=true
server.servlet.contextPath=/
spring.servlet.multipart.enabled=true
spring.servlet.multipart.maxFileSize=100MB
spring.servlet.multipart.maxRequestSize=100MB

#Neo4J connectivity configuration
#ai.gebo.neo4j.enabled=true
#spring.neo4j.uri=bolt://localhost:7687
#spring.neo4j.authentication.username=neo4j
#spring.neo4j.authentication.password=secret

#Eventual custom token secret for unique JWT in installation
#ai.gebo.security.auth.tokenSecret=04ca023b3...
#ai.gebo.security.auth.tokenExpirationMsec=120000
#ai.gebo.security.cors.allowedOrigins=http://localhost:12999,http://localhost:4200

#Qdrant connectivity configuration
#ai.gebo.vectorstore.use=QDRANT
#ai.gebo.vectorstore.qdrant.host=127.0.0.1
#ai.gebo.vectorstore.qdrant.port=6334
#ai.gebo.vectorstore.qdrant.tls=false

#Work directory can be configured using UI
ai.gebo.config.setupConfiguresWorkdir=true

#MongoDB connectivity data
#ai.gebo.mongodb.enabled=true
#ai.gebo.mongodb.databaseName=gebo-ai
#ai.gebo.mongodb.connectionString=mongodb://mongoroot:mongopwd@localhost:27017/gebo-ai?authSource=admin

#Allow user connecting configuring wich share(s) to configure from UI
ai.gebo.filesystem.allowFilesystemSharesUI=true
ai.gebo.filesystem.shares=
EOF
else
    echo "Config file $GEBO_ADDITIONAL_CONFIG_FILE already exists, skipping overwrite" >> "$LOG_OUT"
fi

echo "Reloading systemd and enabling service..." >> "$LOG_OUT"

if command -v systemctl >/dev/null 2>&1; then
    systemctl daemon-reload   >> "$LOG_OUT" 2>> "$LOG_ERR"
    systemctl enable "$SERVICE_NAME"   >> "$LOG_OUT" 2>> "$LOG_ERR"
    systemctl restart "$SERVICE_NAME"  >> "$LOG_OUT" 2>> "$LOG_ERR"
fi

echo "Gebo.ai post-install completed" >> "$LOG_OUT"

exit 0
