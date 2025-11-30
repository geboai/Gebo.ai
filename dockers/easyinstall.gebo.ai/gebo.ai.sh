#!/bin/bash
wait_for() { # host port timeout
  local host="$1" port="$2"  timeout="${3:-60}" serv="$4" t=0
  echo "Waiting for service $serv $host:$port up to ${timeout}s..."
  until (echo > /dev/tcp/$host/$port) >/dev/null 2>&1; do
    sleep 1; 
    t=$((t+1)); 
    if [ "$t" -ge "$timeout" ]; then
      echo "Timeout waiting for $serv on $host:$port"
      return 1
    fi
  done
  echo "$serv is up."
}

# Examples (same container network):
wait_for 127.0.0.1 27017 90 MongoDB   || { echo "Mongo not ready"; exit 1; }
wait_for 127.0.0.1 6333  90 QDrant  || { echo "Qdrant not ready"; exit 1; }
#Next temporarily commented
#wait_for 127.0.0.1 7687  90 Neo4j   || { echo "Neo4j (bolt) not ready"; exit 1; }

exec java $JAVA_TOOL_OPTIONS -jar /opt/gebo.ai/gebo.ai.app-1.0.0.0-SNAPSHOT-bootable.jar