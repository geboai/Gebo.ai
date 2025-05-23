call mvn clean install  -P swagger-on -P under-development
call mvn clean install --fail-at-end -f integration-tests/pom.xml
