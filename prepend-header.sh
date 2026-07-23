#!/bin/bash

# Verifica che il file HEADER esista
if [[ ! -f HEADER ]]; then
  echo "Errore: il file HEADER non esiste nella directory corrente."
  exit 1
fi

# Salva il contenuto dell'header
HEADER_CONTENT=$(cat HEADER)

# Trova tutti i file .java nel folder corrente e nelle sottocartelle
find . -type f -name "*.html" | while read -r java_file; do
  echo "Modificando: $java_file"

  # Usa mktemp per creare un file temporaneo sicuro
  tmpfile=$(mktemp)

  # Scrive prima l'header, poi il contenuto originale
  {
    echo "$HEADER_CONTENT"
    echo
    cat "$java_file"
  } > "$tmpfile"

  # Sovrascrive il file originale
  mv "$tmpfile" "$java_file"
done

echo "Header inserito in tutti i file .java."

