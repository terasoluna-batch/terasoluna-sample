@ECHO OFF
CD /D %~DP0
CALL CLASSPATH.bat

java -cp .:C:\Users\btkomodan.RDH\.m2\repository\* jp.terasoluna.fw.batch.executor.SyncBatchExecutor B000001

ECHO %ERRORLEVEL%

pause