#!/bin/sh

version="3.6.0-SNAPSHOT"
goal="install"

if [ $1 ]; then
  version=$1
fi

if [ $2 ]; then
  goal=$2
fi

echo "target version: ${version} and goal: ${goal}"

rm -rf ./target
rm -rf ./tmp
mkdir tmp
cp -r scripts tmp
cp -r sql tmp
cp -r src tmp
cp pom.xml tmp
cp readme.txt tmp

pushd tmp

echo "rename resources packages to __packageInPathFormat__"
mkdir -p src/main/resources/__packageInPathFormat__
mv src/main/resources/jp/terasoluna/batch/sample/b000001 src/main/resources/__packageInPathFormat__
rm -rf src/main/resources/jp/terasoluna/batch

mvn versions:set -DnewVersion=${version} -DallowSnapshots=true
mvn versions:update-property -Dproperty=terasoluna-fw-batch.version -DnewVersion=${version} -DallowSnapshots=true
mvn versions:commit

mvn archetype:create-from-project -DpackageName=jp.terasoluna.batch.sample

pushd target/generated-sources/archetype

sed -i -e "s/xxxxxx\.yyyyyy\.zzzzzz/jp.terasoluna.fw.batch/g" pom.xml
sed -i -e "s/xxxxxx.yyyyyy.zzzzzz/\${groupId}/g" -e "s/(version_number)/\${version}/g" -e "s/(project-root)/\${artifactId}/g" -e "s/terasoluna-batch-blank/\${artifactId}/g" src/main/resources/archetype-resources/readme.txt
