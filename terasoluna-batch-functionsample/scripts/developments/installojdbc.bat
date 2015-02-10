@ECHO OFF

REM インストールするjarファイルの名前
SET FILE_NAME=ojdbc7.jar
REM インストールするjarのgroupId (変更不要)
SET GROUP_ID=com.oracle
REM インストールするjarのartifactId (ファイル名と揃える)
SET ARTIFACT_ID=ojdbc7
REM インストールするjarのバージョン
SET VERSION=12.1.0.1

REM インストール
CD /D %~DP0
CALL mvn install:install-file -Dfile=%FILE_NAME% -DgroupId=%GROUP_ID% -DartifactId=%ARTIFACT_ID% -Dversion=%VERSION% -Dpackaging=jar -DgeneratePom=true -DcreateChecksum=true

pause