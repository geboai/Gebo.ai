@echo off
REM create-image.bat — Build geboai/platform base image with SBOM attestation
REM
REM Usage:
REM   create-image.bat              (local, single-platform, --load)
REM   create-image.bat --push       (push to Docker Hub, multiplatform)
REM

set "ACTION=--load"
set "PLATFORMS=linux/amd64"

if /i "%~1"=="--push" (
  set "ACTION=--push"
  set "PLATFORMS=linux/amd64,linux/arm64"
)

docker image rm geboai/platform:2.5 --force 2>nul
docker buildx build --platform %PLATFORMS% --sbom=true -t geboai/platform:2.5 %ACTION% .
