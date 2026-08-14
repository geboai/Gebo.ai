@echo off
REM ==========================================================================
REM push-sbom-images.bat — Push gebo.ai Docker images to a registry (multiplatform)
REM ==========================================================================
REM
REM Builds all 3 images (or a single one) for linux/amd64,linux/arm64 with SBOM
REM attestation enabled and pushes them to Docker Hub. This is the only script
REM that pushes — create-image.bat and build-sbom-images.bat are local-only.
REM
REM Usage:
REM   push-sbom-images.bat                      (push all 3 images)
REM   push-sbom-images.bat platform              (push only geboai/platform)
REM   push-sbom-images.bat gebo.ai              (push only geboai/gebo.ai)
REM   push-sbom-images.bat easyinstall          (push only geboai/easyinstall.gebo.ai)
REM
REM Prerequisites:
REM   - docker buildx enabled:  docker buildx create --use --name sbom-builder
REM   - Logged in to Docker Hub:  docker login
REM   - The monolith bootable+swagger jar built:
REM       mvn -f gebo.apps.parent\gebo.ai.app\pom.xml -P bootables package -DskipTests
REM --------------------------------------------------------------------------

setlocal enabledelayedexpansion

set "REPO_ROOT=%~dp0.."
REM Read the project's own <version> from the root pom.xml, taking the first
REM <version> and stopping at <parent> so we don't pick up spring-boot-starter-
REM parent's version instead. Mirrors lib-multiplatform-images.sh — keeps this
REM aligned with whatever the actual build produces instead of a hardcoded
REM literal that goes stale. This script pushes, so a stale tag here would
REM publish under the wrong version, not just fail locally.
set "VERSION="
for /f "usebackq delims=" %%L in ("%REPO_ROOT%\pom.xml") do (
  if not defined VERSION (
    set "LINE=%%L"
    echo(!LINE! | findstr /c:"<parent>" >nul && goto :version_done
    echo(!LINE! | findstr /c:"<version>" >nul && (
      set "VERSION=!LINE:*<version>=!"
      set "VERSION=!VERSION:</version>=!"
    )
  )
)
:version_done
if not defined VERSION (
  echo ERROR: Could not read project version from %REPO_ROOT%\pom.xml
  exit /b 1
)
set "PLATFORMS=linux/amd64,linux/arm64"
set "JAR=%REPO_ROOT%\gebo.apps.parent\gebo.ai.app\target\gebo.ai.app-%VERSION%-bootable.jar"
set "SBOM=%REPO_ROOT%\gebo.apps.parent\gebo.ai.app\target\classes\META-INF\sbom\application.cdx.json"

set "TARGET=%~1"
if "%TARGET%"=="" set "TARGET=all"

if /i "%TARGET%"=="platform" goto :push_platform
if /i "%TARGET%"=="gebo.ai" goto :push_gebo_ai
if /i "%TARGET%"=="easyinstall" goto :push_easyinstall
if /i "%TARGET%"=="all" goto :push_all
echo Unknown target: %TARGET% ^(use: platform, gebo.ai, easyinstall, or all^)
exit /b 1

:push_platform
echo === Pushing geboai/platform:2.5 with SBOM (%PLATFORMS%) ===
docker buildx build --platform %PLATFORMS% --sbom=true --push -t geboai/platform:2.5 "%REPO_ROOT%\dockers\gebo.ai.platform"
if /i "%TARGET%"=="platform" goto :done
goto :push_gebo_ai

:push_gebo_ai
if not exist "%JAR%" goto :err_jar
if not exist "%SBOM%" goto :err_sbom
echo === Pushing geboai/gebo.ai with SBOM (%PLATFORMS%) ===
copy /Y "%JAR%" "%REPO_ROOT%\dockers\gebo.ai\"
copy /Y "%SBOM%" "%REPO_ROOT%\dockers\gebo.ai\"
docker buildx build --platform %PLATFORMS% --sbom=true --push -t geboai/gebo.ai -t geboai/gebo.ai:%VERSION% "%REPO_ROOT%\dockers\gebo.ai"
if /i "%TARGET%"=="gebo.ai" goto :done
goto :push_easyinstall

:push_easyinstall
if not exist "%JAR%" goto :err_jar
if not exist "%SBOM%" goto :err_sbom
echo === Pushing geboai/easyinstall.gebo.ai with SBOM (%PLATFORMS%) ===
copy /Y "%JAR%" "%REPO_ROOT%\dockers\easyinstall.gebo.ai\"
copy /Y "%SBOM%" "%REPO_ROOT%\dockers\easyinstall.gebo.ai\"
docker buildx build --platform %PLATFORMS% --sbom=true --push -t geboai/easyinstall.gebo.ai -t geboai/easyinstall.gebo.ai:%VERSION% "%REPO_ROOT%\dockers\easyinstall.gebo.ai"
if /i "%TARGET%"=="easyinstall" goto :done

:push_all
goto :done

:err_jar
echo ERROR: Bootable jar not found at %JAR%
exit /b 1

:err_sbom
echo ERROR: SBOM not found at %SBOM%
exit /b 1

:done
echo === Done ===
echo.
echo Inspect SBOM attestations with:
echo   docker buildx imagetools inspect geboai/gebo.ai:%VERSION% --format json
exit /b 0
