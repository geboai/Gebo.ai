#!/bin/bash
export JAVA_TOOL_OPTIONS="-Dsun.jnu.encoding=UTF-8 -Dfile.encoding=UTF-8 --add-opens=java.base/java.nio.charset=ALL-UNNAMED --enable-native-access=ALL-UNNAMED -Xmx1g -XX:+AggressiveHeap -XX:+PrintGCDetails -Xlog:gc:/var/log/neo4j/gc.log"

NEO4J_PASS="${NEO4J_PASSWORD:-neo4j}"

# Set the initial admin password on first boot only, marking it as NOT requiring
# a change on first login (--require-password-change=false, Neo4j 5.x).
# Fails (non-zero) if the password was already set, which is fine.
if neo4j-admin dbms set-initial-password --require-password-change=false "${NEO4J_PASS}"; then
  echo "[neo4j-entry] Initial admin password set (no change required)."
else
  echo "[neo4j-entry] Initial password already set, continuing."
fi

# Fallback for data dirs initialised by an older image / the Debian package:
# after the server is up, clear the "must change password" flag. When a password
# change is required Neo4j only allows access to the `system` database and only
# the FROM...TO form, so we change to the same password (which clears the flag).
(
  for i in $(seq 1 120); do
    if (echo > /dev/tcp/127.0.0.1/7687) >/dev/null 2>&1; then break; fi
    sleep 1
  done
  sleep 3
  cypher-shell -a bolt://127.0.0.1:7687 -u neo4j -p "${NEO4J_PASS}" -d system \
    "ALTER CURRENT USER SET PASSWORD FROM '${NEO4J_PASS}' TO '${NEO4J_PASS}';" \
    >/var/log/neo4j/password-change-clear.log 2>&1 \
    && echo "[neo4j-entry] Password change flag cleared." \
    || echo "[neo4j-entry] Password change clear skipped (not needed or failed)."
) &

exec /usr/bin/neo4j console
