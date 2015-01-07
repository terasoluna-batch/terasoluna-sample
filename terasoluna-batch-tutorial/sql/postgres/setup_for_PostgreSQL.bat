rem 使用するDBユーザ、接続文字列を変更する場合は、
rem 下記記述書式を参考にしてください。
rem 【記述書式】
rem psql -h <DBサーバIP> -U <使用するDBユーザ> -d <使用するDB> -f terasoluna_tutorial_batch.sql
cd /d %~dp0
set path="C:\Program Files (x86)\PostgreSQL\8.4\bin\";%path%
psql -h 127.0.0.1 -U postgres -d terasoluna -f terasoluna_tutorial_batch.sql

pause