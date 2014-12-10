@ECHO OFF
CD /D %~DP0

ECHO 終了ファイル作成
TYPE NUL > C:\tmp\batch_terminate_file

ECHO 5秒ウェイト
call sleep 5

ECHO 終了ファイル消去
DEL C:\tmp\batch_terminate_file

pause
