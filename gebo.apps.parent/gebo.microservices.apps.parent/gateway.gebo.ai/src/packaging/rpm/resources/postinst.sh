#!/bin/bash
set -e

APP_USER="gebo-ai-gateway"
APP_GROUP="gebo-ai-gateway"
APP_HOME="/home/gebo-ai-gateway"
GEBO_CONFIG_FOLDER="/etc/gebo-ai-gateway"
GEBO_ADDITIONAL_CONFIG_FILE="$GEBO_CONFIG_FOLDER/application.properties"
GEBO_LOG_BASE="/var/log/gebo-ai-gateway"
SERVICE_NAME="gebo-ai-gateway.service"
APP_DIR="$APP_HOME/gebo-ai-gateway"
SERVICE_DST="/etc/systemd/system/$SERVICE_NAME"

LOG_OUT="/tmp/gebo-ai-gateway-install-log.txt"
LOG_ERR="/tmp/gebo-ai-gateway-install-err.txt"

echo "Starting Gebo.ai Gateway post-install" > "$LOG_OUT"
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
mkdir -p "$GEBO_CONFIG_FOLDER" "$GEBO_LOG_BASE" >> "$LOG_OUT" 2>> "$LOG_ERR"
chown -R "$APP_USER:$APP_GROUP" "$GEBO_LOG_BASE" >> "$LOG_OUT" 2>> "$LOG_ERR"

echo "Writing systemd unit..." >> "$LOG_OUT"

cat > "$SERVICE_DST" <<EOF
[Unit]
Description=Gebo.ai API Gateway Service
After=network.target

[Service]
User=$APP_USER
Group=$APP_GROUP
Environment=SPRING_CONFIG_ADDITIONAL_LOCATION=file:$GEBO_CONFIG_FOLDER/
WorkingDirectory=$APP_HOME
ExecStart=$APP_DIR/bin/gebo-ai-gateway
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
logging.level.org.springframework.cloud.gateway=INFO

server.port=8080

# Spring Cloud Gateway (reactive / WebFlux server) route definitions.
# Enable discovery locator to auto-route to load-balanced backends, or
# declare explicit routes targeting lb://<service-id> backends.
spring.cloud.gateway.server.webflux.discovery.locator.enabled=false

# Example explicit, load-balanced route:
#spring.cloud.gateway.server.webflux.routes[0].id=example-backend
#spring.cloud.gateway.server.webflux.routes[0].uri=lb://example-backend
#spring.cloud.gateway.server.webflux.routes[0].predicates[0]=Path=/api/**
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

echo "Gebo.ai Gateway post-install completed" >> "$LOG_OUT"

exit 0
