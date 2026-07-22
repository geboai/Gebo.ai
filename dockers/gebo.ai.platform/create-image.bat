@echo off
REM create-image.bat — Build geboai/platform base image locally
REM
REM Builds locally and loads into the Docker daemon (single-platform).
REM The SBOM attestation (--sbom) is only generated on push (push-sbom-images.bat).

docker image rm geboai/platform:2.5 --force 2>nul
docker buildx build --network=host --platform linux/amd64 --load -t geboai/platform:2.5 .
