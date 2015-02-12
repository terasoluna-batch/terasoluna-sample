-- 対象DBMS    :Oracle12c
-- テーブル削除
@drop_all_tables.sql
-- シーケンス削除
@drop_all_sequence.sql

-- シーケンス生成
@create_sequence_job_control.sql

-- テーブル生成
@create_table_job_control.sql
@create_table_nyusyukkin.sql

-- データ挿入
@insert_all_data.sql

QUIT;
