set path=C:\Program Files\PostgreSQL\9.3\bin\;%path%
psql -h 127.0.0.1 -U postgres -d functionsampledb -f insert_job_control.sql

PAUSE