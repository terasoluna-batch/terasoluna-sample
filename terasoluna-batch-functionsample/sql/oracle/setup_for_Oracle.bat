@ECHO OFF
CD /D %~DP0

rem	使用するDBユーザ、パスワード、接続文字列を変更する場合は、
rem	下記記述書式を参考にしてください。
rem	【記述書式】
rem	sqlplus <使用するDBユーザ>/<パスワード>@<ネットサービス名> @terasoluna_functionsample.sql

sqlplus sample/sample@SAMPLEDB @terasoluna_functionsample.sql

pause