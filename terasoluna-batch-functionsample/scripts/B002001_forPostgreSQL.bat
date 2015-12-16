@ECHO OFF
CD /D %~DP0

SET CD_BACK=%CD%

CD ..\sql\postgres\

CALL init_job_control.bat


CD %CD_BACK%
SET CD_BACK=

CALL classpath.bat

java jp.terasoluna.fw.batch.executor.AsyncBatchExecutor

ECHO %ERRORLEVEL%

PAUSE