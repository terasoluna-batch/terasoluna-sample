REM	使用するDBユーザ、パスワード、接続文字列を変更する場合は、
REM	下記記述書式を参考にしてください。
REM	【記述書式】
REM	sqlplus <使用するDBユーザ>/<パスワード> @terasoluna_tutorial_batch.sql
CD /D %~DP0
sqlplus sample/sample@XE @terasoluna_tutorial_batch.sql

PAUSE