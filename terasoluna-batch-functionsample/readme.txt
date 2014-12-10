*******************************************************************************
      TERASOLUNA Batch Framework for Java 3.x
      機能サンプル 導入手順について

      Copyright 2007-2011 NTT DATA Corporation.
*******************************************************************************

■  概要：

  このreadmeは、TERASOLUNA Batch Framework for Javaの
  機能サンプルプロジェクトを導入する手順書です。
  下記手順に従うことにより、TERASOLUNAフレームワークの機能サンプルの
  実行環境を準備することができます。

■  前提条件：

  開発環境には、あらかじめ下記のものが用意されている必要があります。
  また併記されているバージョンを元に動作確認を行なっていますが、
  このバージョン以外の環境で動作を制限することを示すものではありません。
  また、これらのインストール及び設定の手順については、
  別途Web上の利用ガイド等を参照してください。 

  ・Java 2 Runtime Environment Standard Edition 1.6
  ・Eclipse SDK 3.4.2 
  ・PostgreSQL Database Server 8.4
    または
  ・Oracle11g
        
■  プロジェクトの実行：

  ①JDBCドライバの配置
    PostgreSQL,OracleのJDBCドライバは付属していないので、利用者各自で別途入手する必要があります。
      ・PostgreSQL
          http://jdbc.postgresql.org/download.html
      ・Oracle
          http://www.oracle.com/technetwork/database/enterprise-edition/jdbc-112010-090769.html
      ※付属のbatファイルからジョブを起動する場合は、
        取得したドライバを以下のフォルダに配置してください。
      ・「terasoluna-batch-functionSample\lib」
      
  ②ZIPファイルの展開
    terasoluna-batch4j-funcsample_(バージョン番号).zipを「C:\」直下に展開します。
      ・例「C:\terasoluna-batch4j-funcsample_(バージョン番号)\」
       ※指定されたディレクトリは固定ではないため、適宜読み替えて実行してください。
       ここでは、（Windows XPの）C:\に展開すると仮定し、手順を進めます。

  ③データベース環境の設定、初期化(ジョブ実行前に必ず実行する)
   ◇PostgreSQLの場合
    1.前提条件(環境により変更可能)
      pgAdminを起動し、新しいデータベースを作成する。
        名前･･･functionsampledb
        オーナー･･･sample
        エンコーディング･･･UTF8
        Template･･･（なし）
        テーブル空間･･･pg_default

    2.「setup_for_PostgreSQL.bat」の編集
       「/sql/postgresql/setup_for_PostgreSQL.bat」および「/sql/postgresql/init_job_control.bat」を
        自端末の環境に合った値に書き換えます。
        詳細は「/sql/postgresql/setup_for_PostgreSQL.bat」を参照してください。

    3.テーブルの作成
       「/sql/postgresql/setup_for_PostgreSQL.bat」を実行します。(eclipseから実行不可)

   ◇Oracleの場合
    1.前提条件(環境により変更可能)
        インスタンス名･･･XE
        DBユーザー名/パスワード･･･sample/sample

    2.「setup_for_Oracle.bat」の編集
        「/sql/oracle/setup_for_Oracle.bat」および「/sql/oracle/init_job_control.bat」を
        自端末の環境に合った値に書き換えます。
        詳細は「/sql/oracle/setup_for_Oracle.bat」を参照してください。

    3.テーブルの作成
      「/sql/oracle/setup_for_Oracle.bat」を実行します。(eclipseから実行不可)
      「SQL> 」が表示されたら exitと入力して終了します。

  ④Eclipseへのインポート
    ・Eclipse画面にて「ファイル＞インポート」を実行し、
      「一般＞既存プロジェクトをワークスペースへ」を選択し「次へ」をクリックします。
    ・「ルート・ディレクトリーの選択」にチェックが入った状態で「参照」をクリックし、
      プロジェクト内容のブラウズから②で展開したディレクトリを指定します。
    ・「プロジェクトをワークスペースへコピー」にチェックが入っていることを確認後、
      「終了」をクリックします。
 
  ⑤入力用ファイルの配置。
    インポートしたプロジェクトに存在する「/input」フォルダの中身を
    C:\temp\に配置します。
    
  ⑥Oracleを使用する場合は、設定ファイルの書き換えを行う(Postgresを使用する場合は不要)
    1.jdbc.propertiesの内容を書き換える。
    「conf\SqlMapAdminConfig\jdbc.properties」および
    「conf\SqlMapConfig\jdbc.properties」の内容を、自環境に合わせ書き換える。
    
    2.SqlMapConfigMain.xmlの内容を書き換える。
      <sqlMap resource="SqlMapAdminConfig/BatchExecutor_Oracle.xml" />の部分の
      コメントアウトを外して、
      <sqlMap resource="SqlMapAdminConfig/BatchExecutor_PostgreSQL.xml" />の部分を
      コメントアウトしておく
    
■ジョブの起動方法

  scriptフォルダ配下のbatファイルから起動します。

■動作確認用サンプルのジョブについて

    本サンプルのジョブ一覧を下記に示します。

    1. jp.terasoluna.batch.functionsample.b001
      ・同期型ジョブ実行機能、ビジネスロジック実行機能、トランザクション管理機能のサンプル
        ・B001001：「scripts/B001001.bat」から起動する
          AbstractTransactionBLogicを継承しフレームワーク側に
          トランザクション管理を任せる(データは全件一括でコミットする)
          処理終了後は、すべてのデータのfamilyNameが「鈴木」に
          firstNameが「太郎」に書き換えられる。

        ・B001002：「scripts/B001002.bat」から起動する
          BLogicインタフェースを継承しビジネスロジック内で
          トランザクションの管理を行うサンプル(データは10件ごとにコミットする)
          処理終了後は、すべてのデータのfamilyNameが「鈴木」に
          firstNameが「太郎」に書き換えられる。

    2. jp.terasoluna.batch.functionsample.b002
      ・非同期型ジョブのサンプル
        ・B002001BLogic：「scripts/B002001_forPostgreSQL.bat」から起動する
          (DBにOracleを使用している場合は「B002001_forOracle.bat」から起動)
          非同期型ジョブの実行サンプル。
          ビジネスロジックではEmployeeテーブルの内容を
          Employee2テーブルにコピーする。

          非同期型ジョブエグゼキューターを終了するには
          「scripts/B002001_TERMINATE.bat」を実行する

    3. jp.terasoluna.batch.functionsample.b003
      ・例外ハンドリング機能のサンプル
        ・B003001BLogic：「scripts/B003001.bat」から起動する
          ジョブ実行時にビジネスロジックで例外が発生した場合に、
          B003001ExceptionHandlerクラスでハンドリングを行うサンプル

    4. jp.terasoluna.batch.functionsample.b004
      ・ファイル操作機能のサンプル
        ・B004001BLogic：「scripts/B004001.bat」から起動する
          「C:\\tmp\\input.csv」ファイルを「C:\\tmp\\outputB004001.csv」に
          コピーするサンプル
    
    5. jp.terasoluna.batch.functionsample.b005
      ・メッセージ管理機能のサンプル
        ・B005001BLogic：「scripts/B005001.bat」から起動する
          「messages.properties」に定義したメッセージを利用した
          ログ出力を行うサンプル

    6. jp.terasoluna.batch.functionsample.b006
      ・バッチ更新最適化機能のサンプル
        ・B006001BLogic：「scripts/B006001.bat」から起動する
          バッチ更新最適化機能を利用して、複数のSQLを一括で発行するサンプル。
          内部ではSQLの発行順をinsertData01:100件,deleteData01:100件,
          updateData01:100件の順に最適化した後、SQLの発行を行っている。
          (実際に発行順を見るためには、デバッグ・ステップインの必要がある)
          
        ・B006002BLogic：「scripts/B006002.bat」から起動する
          バッチ更新最適化機能を利用して、複数のSQLを一括で発行するサンプル。
          こちらでは、Comparatorインタフェース実装クラスを用意し、
          sortメソッドを呼び出すことで、最適化後のSQLの順を変更している。
          内部ではSQLの発行順をupdateData01:100件,deleteData09:100件,
          insertData99:100件の順に最適化した後、SQLの発行を行われる。

    7. jp.terasoluna.batch.functionsample.b007
      ・入力データ取得機能のサンプル
        ・B007001BLogic：「scripts/B007001.bat」から起動する
          ファイル-DB関連ジョブ
          「C:\\tmp\\input.csv」の内容を読み込んで、
          Employeeテーブルにデータを挿入するサンプル。
          
        ・B007002BLogic：「scripts/B007002.bat」から起動する
          DB-ファイル関連ジョブ
          「Employeeテーブル」の内容を読み込んで、
          「C:\\tmp\\outputB007002.csv」に出力するサンプル

    8. jp.terasoluna.batch.functionsample.b008
      ・コントロールブレイク機能のサンプル
        ・B008001BLogic：「scripts/B008001.bat」から起動する
          コントロールブレイク機能を使用したサンプル。
          前データとの比較処理では複数のブレイクキーを使用し、
          コントロールブレイク発生時にログへのヘッダ出力、市町村数のカウントを行い、
          後データとの比較処理では単一のブレイクキーを用いて、
          コントロールブレイクの発生の際にバッチ更新を行う。
          ---「C:\\tmp\\KEN_ALL.CSV」のデータを読み込み、
          ---「ZIP_CODE」テーブルに更新する。

    9. jp.terasoluna.batch.functionsample.b009
      ・入力データ取得機能使用時の入力チェック機能、例外ハンドリングのサンプル
        ・B009001BLogic：「scripts/B009001.bat」から起動する
          入力チェック機能のサンプル。
          拡張入力チェックエラーハンドリングクラスを用意し、Status「SKIP」を返却している。
          ビジネスロジックでは「C:\\tmp\\inputB009001.csv」ファイルを読み込み
          「C:\\tmp\\outputB009001.csv」に出力する。
          この時、2,11,16件目のデータで入力チェックエラーが発生し、
          出力ファイルに出力されない。
          
        ・B009002BLogic：「scripts/B009002.bat」から起動する
          入力データ取得機能使用時の例外ハンドリングサンプル
          拡張例外ハンドリングクラスを用意し、Status「END」を返却している。
          ビジネスロジックではEmployee3テーブルを読み込み、
          Employee2テーブルへの出力を試みるが、
          2,7,12件目のデータで入力チェック例外が投げられ、処理が停止する。
                         
※ファイルを出力するジョブに関して、ファイルの削除処理は特に記述しておらず、
  ファイル出力時には上書きとなるよう設定しています。

-------------------------------------------------------------------------------
Copyright 2007-2011 NTT DATA Corporation.
