@ECHO OFF
REM sleep.bat [•b”]
SET /A WTIME=(%1+0)*1000
ECHO WScript.Sleep %WTIME% > tmp.vbs
CSCRIPT //NoLogo tmp.vbs
DEL tmp.vbs
SET WTIME=
