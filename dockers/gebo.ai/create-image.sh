docker image rm gebo.ai --force
docker build --build-arg JAVA_EXTRA_SECURITY_DIR=/opt/gebo.ai -t gebo.ai .