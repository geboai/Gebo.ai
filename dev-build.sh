#!/bin/bash
mvn clean package -DskipTests  -P swagger-on -P under-development
