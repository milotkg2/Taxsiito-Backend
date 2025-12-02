@REM ----------------------------------------------------------------------------
@REM Maven Wrapper startup batch script
@REM ----------------------------------------------------------------------------

@echo off
@setlocal

set MAVEN_PROJECTBASEDIR=%~dp0
set MAVEN_WRAPPER_JAR=%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.jar
set MAVEN_WRAPPER_URL="https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.2.0/maven-wrapper-3.2.0.jar"

@REM Download Maven Wrapper if not present
if not exist "%MAVEN_WRAPPER_JAR%" (
    echo Downloading Maven Wrapper...
    powershell -Command "& {Invoke-WebRequest -Uri %MAVEN_WRAPPER_URL% -OutFile '%MAVEN_WRAPPER_JAR%'}"
)

@REM Find Java
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto execute
echo Error: JAVA_HOME is not set and no 'java' command could be found in your PATH.
goto error

:findJavaFromJavaHome
set JAVA_EXE=%JAVA_HOME%\bin\java.exe
if exist "%JAVA_EXE%" goto execute
echo Error: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
goto error

:execute
"%JAVA_EXE%" ^
  -Dmaven.multiModuleProjectDirectory="%MAVEN_PROJECTBASEDIR%" ^
  -jar "%MAVEN_WRAPPER_JAR%" %*
if ERRORLEVEL 1 goto error
goto end

:error
set ERROR_CODE=1

:end
@endlocal & set ERROR_CODE=%ERROR_CODE%
exit /B %ERROR_CODE%

