@echo off
REM ==========================================================================
REM create-image.bat — Build geboai/gebo.ai monolith image with SBOM attestation
REM ==========================================================================
REM
REM Copies the freshly built bootable jar and Maven SBOM into the build context,
REM then builds the image. The SBOM is available both as a BuildKit attestation
REM and as an in-image file at /opt/gebo.ai/sbom.cdx.json.
REM
REM Usage:
REM   create-image.bat              (local, single-platform, --load)
REM   create-image.bat --push       (push to Docker Hub, multiplatform)
REM --------------------------------------------------------------------------

set "REPO_ROOT=%~dp0..\.."
set "VERSION=1.0.2.1-SNAPSHOT"
set "JAR=%REPO_ROOT%\gebo.apps.parent\gebo.ai.app\target\gebo.ai.app-%VERSION%-bootable.jar"
set "SBOM=%REPO_ROOT%\gebo.apps.parent\gebo.ai.app\target\classes\META-INF\sbom\application.cdx.json"

set "ACTION=--load"
set "PLATFORMS=linux/amd64"

if /i "%~1"=="--push" (
  set "ACTION=--push"
  set "PLATFORMS=linux/amd64,linux/arm64"
)

if not exist "%JAR%" (
  echo ERROR: Bootable jar not found at %JAR%
  echo Build it first:  mvn -f gebo.apps.parent\gebo.ai.app\pom.xml -P swagger-on package -DskipTests
  exit /b 1
)
if not exist "%SBOM%" (
  echo ERROR: SBOM not found at %SBOM%
  exit /b 1
)

REM Copy artifacts into build context
copy /Y "%JAR%" .
copy /Y "%SBOM%" .

docker image rm geboai/gebo.ai --force 2>nul
docker buildx build --platform %PLATFORMS% --sbom=true --build-arg JAVA_EXTRA_SECURITY_DIR=/opt/gebo.ai -t geboai/gebo.ai -t geboai/gebo.ai:%VERSION% %ACTION% .
