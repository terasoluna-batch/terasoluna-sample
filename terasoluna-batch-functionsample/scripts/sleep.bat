@echo off
REM Sleep.bat [•b”]
set /a wtime=(%1+0)*1000
echo WScript.Sleep %wtime% > tmp.vbs
cscript //NoLogo tmp.vbs
del tmp.vbs
set wtime=
