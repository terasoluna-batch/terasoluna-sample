@ECHO OFF
CD /D %~DP0

REM	使用するDBユーザ、パスワード、接続文字列を変更する場合は、
REM	下記記述書式を参考にしてください。
REM	【記述書式】
REM	sqlplus <使用するDBユーザ>/<パスワード>@<ネットサービス名> @terasoluna_functionsample.sql

sqlplus sample/sample@SAMPLEDB @terasoluna_functionsample.sql

PAUSE