#!/bin/bash
mvn clean install  -P swagger-on -P under-development
# Cloud-only integration suite: docker + the credentials in the environment.
mvn clean install -f integration-tests/pom.xml
# Local-inference suite: additionally needs an ollama server with the models
# pulled (see ollama-integration-tests/README.md). Drop this line on a machine
# that has no ollama.
mvn clean install -f ollama-integration-tests/pom.xml
