# TERASOLUNA Batch Framework for Java

TERASOLUNA Batch Framework for Javaのブランク、機能網羅サンプル、
チュートリアルのプロジェクトを提供します。

### terasoluna-batch-blank
TERASOLUNA Batch Framework for Javaのブランクプロジェクトです。

### terasoluna-batch-functionsample
TERASOLUNA Batch Framework for Javaが提供する各機能の実装サンプルのプロジェクトです。

### terasoluna-batch-tutorial
TERASOLUNA Batch Framework for Javaの基本的な処理パターンと実装方法を学習するためのプロジェクトです。

## Download

* [Download from here](https://github.com/terasoluna-batch/terasoluna-sample/releases)

## Create blank project by archetype
#### for CommandPrompt

``` console
mvn org.apache.maven.plugins:maven-archetype-plugin:2.4:generate^
 -DarchetypeCatalog=http://repo.terasoluna.org/nexus/content/repositories/terasoluna-batch-releases/^
 -DarchetypeGroupId=jp.terasoluna.fw.batch^
 -DarchetypeArtifactId=terasoluna-batch-blank-archetype^
 -DarchetypeVersion=3.6.5.RELEASE
```

#### for Bash

``` console
mvn org.apache.maven.plugins:maven-archetype-plugin:2.4:generate\
 -DarchetypeCatalog=http://repo.terasoluna.org/nexus/content/repositories/terasoluna-batch-releases/\
 -DarchetypeGroupId=jp.terasoluna.fw.batch\
 -DarchetypeArtifactId=terasoluna-batch-blank-archetype\
 -DarchetypeVersion=3.6.5.RELEASE
```
