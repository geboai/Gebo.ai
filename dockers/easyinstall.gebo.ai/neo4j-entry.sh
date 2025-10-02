#!/bin/bash
export AUTH_FILE="/var/lib/neo4j/data/dbms/auth"
export  JAVA_TOOL_OPTIONS="-Dsun.jnu.encoding=UTF-8 -Dfile.encoding=UTF-8 --add-opens=java.base/java.nio.charset=ALL-UNNAMED --enable-native-access=ALL-UNNAMED  -Xmx1g -XX:+AggressiveHeap  -XX:+PrintGCDetails -Xlog:gc:/var/log/neo4j/gc.log"

if [ ! -f "\$AUTH_FILE" ]; then
  echo "Setting initial Neo4j password..."
  neo4j-admin dbms set-initial-password "\${NEO4J_PASSWORD:-neo4jmaster}" || true
fi
exec /usr/bin/neo4j console