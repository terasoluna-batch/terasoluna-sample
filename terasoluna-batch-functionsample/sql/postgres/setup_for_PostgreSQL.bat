@ECHO OFF
CD /D %~DP0

REM 使用するDBユーザ、パスワード、接続文字列を変更する場合は、
REM 下記記述書式を参考にしてください。
REM 【記述書式】
REM psql -h <DBサーバIP> -U <使用するDBユーザ> -d <使用するDB> -f terasoluna_functionsample.sql

SET PATH=C:\Program Files\PostgreSQL\9.3\bin\;%PATH%
psql -h 127.0.0.1 -U postgres -d functionsampledb -f terasoluna_functionsample.sql

PAUSE