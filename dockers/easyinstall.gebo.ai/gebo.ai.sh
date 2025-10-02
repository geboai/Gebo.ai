#!/bin/bash
wait_for() { # host port timeout
  local host="$1" port="$2" timeout="${3:-60}" t=0
  echo "Waiting for $host:$port up to ${timeout}s..."
  until (echo > /dev/tcp/$host/$port) >/dev/null 2>&1; do
    sleep 1; t=$((t+1)); [ $t -ge $timeout ] && return 1
  done
}

# Examples (same container network):
wait_for 127.0.0.1 27017 30   || { echo "Mongo not ready"; exit 1; }
wait_for 127.0.0.1 6333  30   || { echo "Qdrant not ready"; exit 1; }
wait_for 127.0.0.1 7687  30   || { echo "Neo4j (bolt) not ready"; exit 1; }

exec java $JAVA_TOOL_OPTIONS -jar /opt/gebo.ai/gebo.ai.app-0.9.9.8-SNAPSHOT-bootable.jar