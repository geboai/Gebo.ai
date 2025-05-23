#!/bin/bash
mvn clean install  -P swagger-on -P under-development
mvn clean install -f integration-tests/pom.xml
