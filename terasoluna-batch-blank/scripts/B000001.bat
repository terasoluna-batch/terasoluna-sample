@ECHO OFF
CD /D %~DP0
CALL classpath.bat

java jp.terasoluna.fw.batch.executor.SyncBatchExecutor B000001

ECHO %ERRORLEVEL%

PAUSE