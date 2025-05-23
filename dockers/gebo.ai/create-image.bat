xcopy ..\..\gebo.apps.parent\gebo.ai.app\target\gebo.ai.app-bootable-0.9.9.7-SNAPSHOT.jar .
docker image rm geboai/gebo.ai --force
docker build --build-arg JAVA_EXTRA_SECURITY_DIR=/opt/gebo.ai -t geboai/gebo.ai -t geboai/gebo.ai:0.9.9.7-SNAPSHOT .