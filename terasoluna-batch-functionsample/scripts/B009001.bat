@ECHO OFF
CD /D %~DP0
CALL CLASSPATH.bat

java jp.terasoluna.fw.batch.executor.SyncBatchExecutor B009001

ECHO %ERRORLEVEL%

pause