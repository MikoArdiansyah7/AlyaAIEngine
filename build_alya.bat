@echo off
REM ============================================================
REM  Alya AI Engine — debug build script
REM  Mirrors the same workflow used for ControlOS.
REM  Edit the paths below if your JDK / SDK / project location differ.
REM ============================================================

set JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-21.0.5.11-hotspot
set ANDROID_HOME=C:\Users\E15\AppData\Local\Android\Sdk

REM If you extracted the project somewhere other than C:\AlyaAIEngine,
REM change the line below to match.
cd C:\AlyaAIEngine

gradlew.bat assembleDebug

echo.
echo Build finished. APK should be at:
echo   app\build\outputs\apk\debug\app-debug.apk
pause
