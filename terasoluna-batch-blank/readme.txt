*******************************************************************************
      TERASOLUNA Batch Framework for Java
      ブランクプロジェクト 導入手順

      Copyright (c) 2011-2016 NTT DATA Corporation.
*******************************************************************************

■  概要：

  このreadmeは、TERASOLUNA Batch Framework for Javaのブランクプロジェクトを導入する手順書です。
　ブランクプロジェクトを導入することにより、TERASOLUNAフレームワークを使った
　アプリケーション開発環境を構築することが可能になります。 

■  前提条件：

  開発環境には、あらかじめ下記のものが用意されている必要があります。
  また併記されているバージョンを元に動作確認を行なっていますが、
  このバージョン以外の環境で動作を制限することを示すものではありません。
  また、これらのインストール及び設定の手順については、
  別途Web上の利用ガイド等を参照してください。 

  ・Java SE Runtime Environment Standard Edition 1.7.0
  ・Eclipse SDK 3.7.2 + Mavenプラグイン
  ・Apache Maven (3.X以降)
  ・PostgreSQL 9.3
  　または
  ・Oracle 12c

■  プロジェクトのインポート：

  1.ZIPファイルの展開 (Maven archetypeからブランクプロジェクトを生成した場合は本項を読み飛ばしてください。)

    terasoluna-batch-blank-(version_number).zipを任意のフォルダに展開します。 

    展開されたterasoluna-batch-blank-(version_number).zipのフォルダ名が
    「terasoluna-batch-blank」であることを確認します。
    展開ツールや展開のしかたによっては、フォルダ名が
    「terasoluna-batch-blank-(version_number)」となる場合がありますが、
    フォルダ名を手動で「terasoluna-batch-blank」に変更してください。 

  2.Eclipseへのインポート
    ・Eclipse画面にて「ファイル＞インポート」を実行し、
      「Maven＞Existing Maven Projects」を選択し「次へ」をクリックします。
    ・「RootDirectory:」の「Browse...」をクリックし、
    　プロジェクト内容のブラウズから本ディレクトリを指定します。
    ・「/pom.xml xxxxxx.yyyyyy.zzzzzz:terasoluna-batch-blank:(version_number).jar」に
    　チェックが入っていることを確認後、「完了」をクリックします。

■  ジョブの起動(開発環境)：

    ・「(project-root)/scripts/developments」ディレクトリ配下の
    copydependencies.batを実行し、「(project-root)/lib」ディレクトリ配下に
    現在のpom.xmlで依存関係が定義されたjarをすべてコピーします。
    1度実行すれば、pom.xmlに変更を加えない限り、再実行する必要はありません。
    ・開発環境で動作確認を行う場合は、「(project-root)/scripts/developments」ディレクトリ配下の
    compile.batを実行し、「(project-root)/lib」ディレクトリ配下に
    現在のソースコードでコンパイルされた「terasoluna-batch-blank-(version_number).jar」をコピーします。
    ・「(project-root)/scripts」ディレクトリ配下の起動スクリプト
    (ブランクプロジェクトにあらかじめ含まれるサンプルでは、B000001.bat)を実行します。

■  ジョブの起動(試験環境・実運用環境)：

    ・ブランクプロジェクトのルートディレクトリで、「mvn package」コマンドを実行します。
    ・「(project-root)/target」ディレクトリに
    「terasoluna-batch-blank-(version_number)-distribution.zip」が作成されます。
    ・「terasoluna-batch-blank-(version_number)-distribution.zip」を試験環境・実運用環境へ移送し、解凍します。
    ・「(project-root)/scripts」ディレクトリ配下の起動スクリプト
    (ブランクプロジェクトにあらかじめ含まれるサンプルでは、B000001.bat)を実行します。

■  Maven3.2.3以降を使用する際の注意
    ・Maven3.2.3以降、セントラルリポジトリにはHTTPSを使用してアクセスするため、
      「(ユーザーのホームディレクトリ)/.m2/settings.xml」に設定を追加する必要があります。
      追加する設定内容については、以下のURLを参照してください。
      http://central.sonatype.org/pages/consumers.html#apache-maven

-------------------------------------------------------------------------------
Copyright (c) 2011-2016 NTT DATA Corporation.
