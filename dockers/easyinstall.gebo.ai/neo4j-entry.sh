#!/bin/bash
export JAVA_TOOL_OPTIONS="-Dsun.jnu.encoding=UTF-8 -Dfile.encoding=UTF-8 --add-opens=java.base/java.nio.charset=ALL-UNNAMED --enable-native-access=ALL-UNNAMED -Xmx1g -XX:+AggressiveHeap -XX:+PrintGCDetails -Xlog:gc:/var/log/neo4j/gc.log"

NEO4J_PASS="${NEO4J_PASSWORD:-neo4j}"

# Set the initial admin password on first boot only, marking it as NOT requiring
# a change on first login (--require-password-change=false, Neo4j 5.x).
# The auth store was removed at image build time, so on a fresh volume this
# succeeds; on an already-initialised volume it fails (non-zero) and we continue.
if neo4j-admin dbms set-initial-password --require-password-change=false "${NEO4J_PASS}"; then
  echo "[neo4j-entry] Initial admin password set to '${NEO4J_PASS}' (no change required)."
else
  echo "[neo4j-entry] Auth store already initialised, continuing with existing password."
fi

exec /usr/bin/neo4j console
