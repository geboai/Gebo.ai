xcopy ..\..\gebo.apps.parent\gebo.ai.app\target\gebo.ai.app-0.9.9.8-bootable.jar .
xcopy ..\..\gebo.apps.parent\gebo.ai.app\target\classes\META-INF\sbom\application.cdx.json
docker image rm geboai/easyinstall.gebo.ai --force
docker build --build-arg JAVA_EXTRA_SECURITY_DIR=/opt/gebo.ai -t geboai/easyinstall.gebo.ai -t geboai/easyinstall.gebo.ai:0.9.9.8 .