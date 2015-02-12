@ECHO OFF
PUSHD %~DP0..\..\

CALL mvn clean compile jar:jar

COPY target\*.jar lib\

POPD

pause