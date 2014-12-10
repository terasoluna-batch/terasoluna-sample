rem	使用するDBユーザ、パスワード、接続文字列を変更する場合は、
rem	下記記述書式を参考にしてください。
rem	【記述書式】
rem	sqlplus <使用するDBユーザ>/<パスワード> @terasoluna_tutorial_batch.sql
cd /d %~dp0
sqlplus sample/sample@XE @terasoluna_tutorial_batch.sql

pause