#!/bin/bash

export DATA_DIR="${MONGO_DATA:-/data/db}"

# Se Mongo è già inizializzato, esci
if [ -f "${DATA_DIR}/WiredTiger" ] || [ -f "${DATA_DIR}/WiredTiger.turtle" ]; then
  echo "[mongo-init] Data dir già inizializzato, salto."
  exit 0
fi

echo "[mongo-init] Prima inizializzazione di MongoDB..."

# Avvia mongod in background SOLO su localhost e SENZA auth per creare l'utente
mongod --config /etc/mongod.conf --bind_ip 127.0.0.1 --noauth &
MONGOD_PID=$!

# Attendi che sia pronto
for i in $(seq 1 60); do
  if mongosh  --eval "db.adminCommand('ping')" >/dev/null 2>&1; then
    break
  fi
  echo "[mongo-init] Attendo mongod... ($i)"
  sleep 1
done

if ! mongosh --eval "db.adminCommand('ping')" >/dev/null 2>&1; then
  echo "[mongo-init] ERRORE: mongod non è partito." >&2
  exit 1
fi

# Crea utente root su db admin
USER_NAME="${MONGO_INITDB_ROOT_USERNAME:-mogoroot}"
USER_PWD="${MONGO_INITDB_ROOT_PASSWORD:-mongopwd}"

echo "[mongo-init] Creo utente admin '${USER_NAME}'."
mongosh  <<'JS'
const user = process.env.MONGO_INITDB_ROOT_USERNAME || "mongoroot";
const pwd  = process.env.MONGO_INITDB_ROOT_PASSWORD || "mongopwd";
const dbAdmin = db.getSiblingDB("admin");

// se l'utente esiste già non fallire
try {
  dbAdmin.createUser({
    user: user,
    pwd: pwd,
    roles: [ { role: "root", db: "admin" } ]
  });
  print("Utente creato.");
} catch (e) {
  if (e.codeName === "DuplicateKey") {
    print("Utente già esistente, ok.");
  } else {
    throw e;
  }
}
JS

# Arresta mongod temporaneo
mongosh --quiet --eval "db.getSiblingDB('admin').shutdownServer()" || true
wait ${MONGOD_PID} || true

echo "[mongo-init] Inizializzazione completata."
