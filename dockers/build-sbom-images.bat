@echo off
REM ==========================================================================
REM build-sbom-images.bat — Build gebo.ai Docker images locally
REM ==========================================================================
REM
REM Builds all 3 images (or a single one) and loads them into the local Docker
REM daemon (single-platform linux/amd64). The Maven SBOM is COPY'd into each
REM image. The BuildKit SBOM attestation (--sbom) is NOT used here — use
REM push-sbom-images.bat for that (pushes to a registry with --sbom=true).
REM
REM Usage:
REM   build-sbom-images.bat                    (build all 3 images)
REM   build-sbom-images.bat platform            (build only geboai/platform)
REM   build-sbom-images.bat gebo.ai             (build only geboai/gebo.ai)
REM   build-sbom-images.bat easyinstall         (build only geboai/easyinstall.gebo.ai)
REM
REM Prerequisites:
REM   mvn -f gebo.apps.parent\gebo.ai.app\pom.xml -P bootables package -DskipTests
REM --------------------------------------------------------------------------

setlocal enabledelayedexpansion

set "REPO_ROOT=%~dp0.."
set "VERSION=1.0.2.1-SNAPSHOT"
set "JAR=%REPO_ROOT%\gebo.apps.parent\gebo.ai.app\target\gebo.ai.app-%VERSION%-bootable.jar"
set "SBOM=%REPO_ROOT%\gebo.apps.parent\gebo.ai.app\target\classes\META-INF\sbom\application.cdx.json"

set "TARGET=%~1"
if "%TARGET%"=="" set "TARGET=all"

if /i "%TARGET%"=="platform" goto :build_platform
if /i "%TARGET%"=="gebo.ai" goto :build_gebo_ai
if /i "%TARGET%"=="easyinstall" goto :build_easyinstall
if /i "%TARGET%"=="all" goto :build_all
echo Unknown target: %TARGET% ^(use: platform, gebo.ai, easyinstall, or all^)
exit /b 1

:build_platform
echo === Building geboai/platform:2.5 (local) ===
docker buildx build --network=host --platform linux/amd64 --load -t geboai/platform:2.5 "%REPO_ROOT%\dockers\gebo.ai.platform"
if /i "%TARGET%"=="platform" goto :done
goto :build_gebo_ai

:build_gebo_ai
if not exist "%JAR%" goto :err_jar
if not exist "%SBOM%" goto :err_sbom
echo === Building geboai/gebo.ai (local) ===
copy /Y "%JAR%" "%REPO_ROOT%\dockers\gebo.ai\"
copy /Y "%SBOM%" "%REPO_ROOT%\dockers\gebo.ai\"
docker buildx build --network=host --platform linux/amd64 --load -t geboai/gebo.ai -t geboai/gebo.ai:%VERSION% "%REPO_ROOT%\dockers\gebo.ai"
if /i "%TARGET%"=="gebo.ai" goto :done
goto :build_easyinstall

:build_easyinstall
if not exist "%JAR%" goto :err_jar
if not exist "%SBOM%" goto :err_sbom
echo === Building geboai/easyinstall.gebo.ai (local) ===
copy /Y "%JAR%" "%REPO_ROOT%\dockers\easyinstall.gebo.ai\"
copy /Y "%SBOM%" "%REPO_ROOT%\dockers\easyinstall.gebo.ai\"
docker buildx build --network=host --platform linux/amd64 --load -t geboai/easyinstall.gebo.ai -t geboai/easyinstall.gebo.ai:%VERSION% "%REPO_ROOT%\dockers\easyinstall.gebo.ai"
if /i "%TARGET%"=="easyinstall" goto :done

:build_all
goto :done

:err_jar
echo ERROR: Bootable jar not found at %JAR%
exit /b 1

:err_sbom
echo ERROR: SBOM not found at %SBOM%
exit /b 1

:done
echo === Done ===
exit /b 0
