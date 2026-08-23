call mvn clean install  -P swagger-on -P under-development
rem Cloud-only integration suite: docker + the credentials in the environment.
call mvn clean install --fail-at-end -f integration-tests/pom.xml
rem Local-inference suite: additionally needs an ollama server with the models
rem pulled (see ollama-integration-tests/README.md). Drop this line on a machine
rem that has no ollama.
call mvn clean install --fail-at-end -f ollama-integration-tests/pom.xml
