xcopy ..\..\gebo.apps.parent\gebo.ai.app\target\gebo.ai.app-1.0.0.0-rc1-bootable.jar .
xcopy ..\..\gebo.apps.parent\gebo.ai.app\target\classes\META-INF\sbom\application.cdx.json
docker image rm geboai/easyinstall.gebo.ai --force
docker build --build-arg JAVA_EXTRA_SECURITY_DIR=/opt/gebo.ai -t geboai/easyinstall.gebo.ai -t geboai/easyinstall.gebo.ai:1.0.0.0-rc1 .