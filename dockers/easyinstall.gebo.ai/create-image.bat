@echo off
REM ==========================================================================
REM create-image.bat — Build geboai/easyinstall.gebo.ai all-in-one image locally
REM ==========================================================================
REM
REM Copies the freshly built bootable jar and Maven SBOM into the build context,
REM then builds the all-in-one image locally and loads into the Docker daemon
REM (single-platform). The SBOM is COPY'd into the image at /opt/gebo.ai/sbom.cdx.json.
REM The BuildKit SBOM attestation (--sbom) is only generated on push
REM (push-sbom-images.bat).
REM
REM Prerequisites:
REM   mvn -f gebo.apps.parent\gebo.ai.app\pom.xml -P bootables package -DskipTests
REM --------------------------------------------------------------------------

set "REPO_ROOT=%~dp0..\.."
set "VERSION=1.0.2.1-SNAPSHOT"
set "JAR=%REPO_ROOT%\gebo.apps.parent\gebo.ai.app\target\gebo.ai.app-%VERSION%-bootable.jar"
set "SBOM=%REPO_ROOT%\gebo.apps.parent\gebo.ai.app\target\classes\META-INF\sbom\application.cdx.json"

if not exist "%JAR%" (
  echo ERROR: Bootable jar not found at %JAR%
  echo Build it first:  mvn -f gebo.apps.parent\gebo.ai.app\pom.xml -P bootables package -DskipTests
  exit /b 1
)
if not exist "%SBOM%" (
  echo ERROR: SBOM not found at %SBOM%
  exit /b 1
)

copy /Y "%JAR%" .
copy /Y "%SBOM%" .

docker image rm geboai/easyinstall.gebo.ai --force 2>nul
docker buildx build --network=host --platform linux/amd64 --load --build-arg JAVA_EXTRA_SECURITY_DIR=/opt/gebo.ai -t geboai/easyinstall.gebo.ai -t geboai/easyinstall.gebo.ai:%VERSION% .
