#!/bin/sh
rm -rf ./target
rm -rf ./tmp
mkdir tmp
cp -r scripts tmp
cp -r sql tmp
cp -r src tmp
cp pom.xml tmp
pushd tmp

if [ -d src/main/resources/jp/terasoluna/batch/sample/b000001 ];then
  echo "rename to __packageInPathFormat__"
  mkdir -p src/main/resources/__packageInPathFormat__
  mv src/main/resources/jp/terasoluna/batch/sample/b000001 src/main/resources/__packageInPathFormat__
  rm -rf src/main/resources/jp/terasoluna/batch
fi

mvn archetype:create-from-project -DpackageName=jp.terasoluna.batch.sample

pushd target/generated-sources/archetype
sed -i -e "s/xxxxxx\.yyyyyy\.zzzzzz/jp.terasoluna.fw.batch/g" pom.xml
sed -i -e "s/projectName/terasoluna-batch-blank/g" pom.xml

mvn install