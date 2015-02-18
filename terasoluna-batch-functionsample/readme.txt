*******************************************************************************
      TERASOLUNA Batch Framework for Java
              機能サンプル 導入手順について

      Copyright 2007-2015 NTT DATA Corporation.
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

  ・Java 2 Runtime Environment Standard Edition 1.7.0
  ・Eclipse SDK 3.7.2 + Mavenプラグイン
  ・PostgreSQL 9.3
    または
  ・Oracle 12c
        
■  プロジェクトの実行：
     
  ①ZIPファイルの展開
    terasoluna-batch4j-funcsample_(バージョン番号).zipを「C:\」直下に展開します。
      ・例「C:\terasoluna-batch4j-funcsample_(バージョン番号)\」
       ※指定されたディレクトリは固定ではないため、適宜読み替えて実行してください。
       ここでは、（Windows 7の）C:\に展開すると仮定し、手順を進めます。

  ②データベース環境の設定、初期化(ジョブ実行前に必ず実行する)
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

  ③Eclipseへのインポート
    1.Eclipse画面にて「ファイル＞インポート」を実行し、
      「Maven＞Existing Maven Projects」を選択し「次へ」をクリックします。
    2.「RootDirectory:」の「Browse...」をクリックし、
    　プロジェクト内容のブラウズから①で展開したディレクトリを指定します。
    3.「/pom.xml jp.terasoluna.fw:terasoluna-batch-functionsample:(バージョン番号).jar」に
    　チェックが入っていることを確認後、「完了」をクリックします。

  ④JDBCドライバの設定
  利用するDBMSにより設定手順が異なります。
   ◇PostgreSQLの場合
    1.「/pom.xml」の編集
       MavenのセントラルリポジトリからJDBCドライバを取得します。
       pom.xmlに以下のような記述が必要になります(あらかじめ設定されています)。

       <!-- JDBC Driver(PostgreSQL) -->
       <dependency>
           <groupId>org.postgresql</groupId>
           <artifactId>postgresql</artifactId>
           <version>9.3-1102-jdbc41</version>
           <scope>runtime</scope>
       </dependency>

       ※<version>タグに記載するバージョンは、利用するPostgreSQLのバージョンに合わせて選択してください。
         Mavenのセントラルリポジトリに公開されているバージョンは、以下のURLから検索することができます。
         http://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22org.postgresql%22%20AND%20a%3A%22postgresql%22

   ◇Oracleの場合
    1.JDBCドライバの取得
       以下のURLからJDBCドライバを取得し、「/scripts/developments」フォルダに配置してください。
          http://www.oracle.com/technetwork/database/features/jdbc/jdbc-drivers-12c-download-1958347.html

    2.「/scripts/developments/installojdbc.bat」の編集
       「/scripts/developments/installojdbc.bat」のFILE_NAME、GROUP_ID、ARTIFACT_ID、VERSIONの値を、
        以下のように自環境で使用するJDBCドライバの値に書き換えてください。

        REM インストールするjarファイルの名前
        SET FILE_NAME=ojdbc7.jar
        REM インストールするjarのgroupId (変更不要)
        SET GROUP_ID=com.oracle
        REM インストールするjarのartifactId (ファイル名と揃える)
        SET ARTIFACT_ID=ojdbc7
        REM インストールするjarのバージョン
        SET VERSION=12.1.0.1

        ※batファイルにはOracle Database 12c Release 1のojdbc7.jarを使用する場合の
          設定があらかじめ記載されています。

    3.「/scripts/developments/installojdbc.bat」の実行
       2.で編集した「/scripts/developments/installojdbc.bat」を実行します。
       コマンドプロンプトが立ち上がり、「BUILD SUCCESS」がログに出力されていることを確認します。

    4.「/pom.xml」の編集
       2.でインストールしたJDBCドライバを取得するために、
       pom.xmlに以下のような記述が必要になります(あらかじめ設定がコメントアウトされています)。
       2.で指定したGROUP_ID、ARTIFACT_ID、VERSIONの各値を使用します。

       <!-- JDBC Driver(Oracle) -->
       <dependency>
           <groupId>com.oracle</groupId> <!-- 2.で指定したGROUP_IDの値 -->
           <artifactId>ojdbc7</artifactId> <!-- 2.で指定したARTIFACT_IDの値 -->
           <version>12.1.0.1</version> <!-- 2.で指定したVERSIONの値 -->
           <scope>runtime</scope>
       </dependency>

  ⑤入力用ファイルの配置
    インポートしたプロジェクトに存在する「/input」フォルダの中身をC:\tmp\に配置します。
    
  ⑥Oracleを使用する場合は、設定ファイルの書き換えを行います。(Postgresを使用する場合は不要)
    1.データベース接続設定の書き換え
    「/src/main/resources/mybatisAdmin/jdbc.properties」および
    「/src/main/resources/mybatis/jdbc.properties」の内容を、自環境に合わせ書き換えます。
    
    2.システムが利用するDAOの書き換え
     「/src/main/resources/beansAdminDef/AdminDataSource.xml」の
      システム利用DAO定義(PostgreSQL)をコメントアウトし、システム利用DAO定義(Oracle)のコメントアウトを外します。

■  ジョブの起動(開発環境)：

    1.「/scripts/developments/copydependencies.bat」を実行し、「/lib」ディレクトリ配下に
      現在のpom.xmlで依存関係が定義されたjarをすべてコピーします。
      1度実行すれば、pom.xmlに変更を加えない限り、再実行する必要はありません。
    2.開発環境で動作確認を行う場合は、「/scripts/developments/compile.bat」を実行し、
      「/lib」ディレクトリ配下に 現在のソースコードでコンパイルされた
      「terasoluna-batch-functionsample-(バージョン番号).jar」をコピーします。
    3.「/scripts」ディレクトリ配下の起動スクリプト(B00X00X.bat)を実行します。

■  ジョブの起動(試験環境・実運用環境)：

    1.機能サンプルプロジェクトのルートディレクトリで、「mvn package」コマンドを実行します。
    2.「/target」ディレクトリに「terasoluna-batch-functionsample-(バージョン番号)-distribution.zip」が作成されます。
    3.「terasoluna-batch-functionsample-(バージョン番号)-distribution.zip」を試験環境・実運用環境へ移送し、解凍します。
    4.「/scripts」ディレクトリ配下の起動スクリプト(B00X00X.bat)を実行します。

■  動作確認用サンプルのジョブについて

    本サンプルのジョブ一覧を下記に示します。

    1. jp.terasoluna.batch.functionsample.b001
      ・同期型ジョブ実行機能、ビジネスロジック実行機能、トランザクション管理機能、データベースアクセス機能のサンプル

        すべてのデータのfamilyNameを「鈴木」に、firstNameを「太郎」に書き換える処理を行います。
        トランザクション管理機能とデータベースアクセス機能のバリエーションで4つに分けられています。
       
        ・jp.terasoluna.batch.functionsample.b001.b001001
            B001001：「scripts/B001001.bat」から起動する
              トランザクション管理:
                AbstractTransactionBLogicを継承しフレームワーク側に
                トランザクション管理を任せるサンプルです。
                データは全件一括でコミットされます。
              データベースアクセス機能:
                バッチ更新を用いないサンプルです。

        ・jp.terasoluna.batch.functionsample.b001.b001002
            B001002：「scripts/B001002.bat」から起動する
              トランザクション管理:
                BLogicインタフェースを継承しビジネスロジック内で
                トランザクションの管理を行うサンプルです。
                データは10件ごとにコミットされます。
              データベースアクセス機能:
                バッチ更新を用いないサンプルです。

        ・jp.terasoluna.batch.functionsample.b001.b001003
             B001003：「scripts/B001003.bat」から起動する
              トランザクション管理:
                AbstractTransactionBLogicを継承しフレームワーク側に
                トランザクション管理を任せるサンプルです。
                データは全件一括でコミットされます。
              データベースアクセス機能:
                バッチ更新を用いるサンプルです。
                更新系のSQLをまとめて実行する場合、バッチ更新を用いると性能の向上が見込めます。
                メモリの枯渇を避けるため、コミット時以外に、
                SqlSessionインタフェースのflushStatementsメソッドを使用し、
                10件ごとにバッチ更新(コミットは行わない)を実行しています。

        ・jp.terasoluna.batch.functionsample.b001.b001004
            B001004：「scripts/B001004.bat」から起動する
              トランザクション管理:
                BLogicインタフェースを継承しビジネスロジック内で
                トランザクションの管理を行うサンプルです。
                データは10件ごとにコミットされます。
              データベースアクセス機能:
                バッチ更新を用いるサンプルです。
                更新系のSQLをまとめて実行する場合、バッチ更新を用いると性能の向上が見込めます。
                10件ごとにコミットしており、コミットのタイミング(コミット直前)でバッチ更新が実行されるため、
                SqlSessionインタフェースのflushStatementsメソッドを使用したバッチ更新は実行していません。

    2. jp.terasoluna.batch.functionsample.b002
      ・非同期型ジョブのサンプル

          Employeeテーブルの内容をEmployee2テーブルにコピーする処理を行います。

        ・jp.terasoluna.batch.functionsample.b002.b002001
            B002001BLogic：「scripts/B002001_forPostgreSQL.bat」から起動する
              非同期型ジョブエグゼキュータ―を起動してジョブを非同期に実行するサンプルです。

              ※DBにOracleを使用している場合は「B002001_forOracle.bat」から起動してください。

              ※非同期型ジョブエグゼキューターを終了するには
              「scripts/B002001_TERMINATE.bat」を実行してください。

    3. jp.terasoluna.batch.functionsample.b003
      ・例外ハンドリング機能のサンプル

        ・jp.terasoluna.batch.functionsample.b003.b003001
            B003001BLogic：「scripts/B003001.bat」から起動する
              ジョブ実行時にビジネスロジックで例外が発生した場合に、
              B003001ExceptionHandlerクラスでハンドリングを行うサンプルです。

    4. jp.terasoluna.batch.functionsample.b004
      ・ファイル操作機能のサンプル

        ・jp.terasoluna.batch.functionsample.b004.b004001
            B004001BLogic：「scripts/B004001.bat」から起動する
             「C:/tmp/input.csv」ファイルを「C:/tmp/outputB004001.csv」に
              コピーするサンプルです。
    
    5. jp.terasoluna.batch.functionsample.b005
      ・メッセージ管理機能のサンプル

        ・jp.terasoluna.batch.functionsample.b005.b005001
            B005001BLogic：「scripts/B005001.bat」から起動する
             「application-messages.properties」に定義したメッセージを利用したログ出力を行うサンプルです。

    6. jp.terasoluna.batch.functionsample.b006
      ・本バージョンでは、「バッチ更新最適化機能」は提供していないため、サンプルはありません。
        データベースアクセス機能のバッチ更新を用いるサンプル(B001003、B001004)を利用してください。

    7. jp.terasoluna.batch.functionsample.b007
      ・入力データ取得機能、データベースアクセス機能、ファイルアクセス機能のサンプル

        ・jp.terasoluna.batch.functionsample.b007.b007001
            B007001BLogic：「scripts/B007001.bat」から起動する
              入力データ取得機能を利用してファイル「C:/tmp/input.csv」の内容を読み込み、
              データベースアクセス機能を利用して「Employeeテーブル」にデータを挿入するサンプルです。

        ・jp.terasoluna.batch.functionsample.b007.b007002
            B007002BLogic：「scripts/B007002.bat」から起動する
              入力データ取得機能を利用してデータベース「Employeeテーブル」の内容を読み込み、
              ファイルアクセス機能を利用して「C:/tmp/outputB007002.csv」にデータを挿入するサンプルです。

    8. jp.terasoluna.batch.functionsample.b008
      ・コントロールブレイク機能のサンプル

        ・jp.terasoluna.batch.functionsample.b008.b008001
            B008001BLogic：「scripts/B008001.bat」から起動する
              都道府県ごとの市区町村、町域が記載されている「C:/tmp/KEN_ALL.csv」を読み込み、
              都道府県単位で「ZIP_CODE」テーブルにデータを挿入するサンプルです。
              その際に、都道府県ごとの市区町村数、町域数をログに出力します。

    9. jp.terasoluna.batch.functionsample.b009
      ・入力データ取得機能使用時の入力チェック機能、例外ハンドリングのサンプル

        ・jp.terasoluna.batch.functionsample.b009.b009001
            B009001BLogic：「scripts/B009001.bat」から起動する
              「C:/tmp/inputB009001.csv」を読み込み、「C:/tmp/outputB009001.csv」に出力します。
              この時、BeanValidationを利用した入力チェックが実行され、
              2,11,16件目のデータで入力チェックエラーが発生します。
              このサンプルでは、拡張入力チェックエラーハンドリングクラスを用意し、
              Status「SKIP」を返却しているため、2,11,16件目のデータはファイルに出力されません。
          
        ・jp.terasoluna.batch.functionsample.b009.b009002
            B009002BLogic：「scripts/B009002.bat」から起動する
              Employee3テーブルを読み込み、Employee2テーブルにコピーします。
              この時、BeanValidationを利用した入力チェックが実行され、
              2,7,12件目のデータで入力チェックエラーが発生します。
              このサンプルでは、入力データ取得機能使用時の拡張例外ハンドリングクラスを用意し、
              Status「END」を返却しているため、2件目のデータで入力データの取得が停止します。
              そのため、Employeeテーブルには1件のみデータがコピーされます。

※ファイルを出力するジョブに関して、ファイルの削除処理は特に記述しておらず、
  ファイル出力時には上書きとなるよう設定しています。

-------------------------------------------------------------------------------
Copyright 2007-2015 NTT DATA Corporation.
