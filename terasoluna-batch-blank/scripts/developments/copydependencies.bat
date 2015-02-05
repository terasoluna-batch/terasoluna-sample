@ECHO OFF
PUSHD %~DP0..\..\

RMDIR /S /Q lib

CALL mvn dependency:copy-dependencies -DoutputDirectory=.\lib

POPD

pause