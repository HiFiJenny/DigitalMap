#!/bin/sh

# 复制项目的文件到对应docker路径，便于一键生成镜像。
usage() {
	echo "Usage: sh copy.sh"
	exit 1
}


# copy sql
echo "begin copy sql "
cp ../sql/ry_20220814.sql ./mysql/db
cp ../sql/ry_config_20220510.sql ./mysql/db

# copy html
echo "begin copy html "
cp -r ../measure-ui/dist/** ./nginx/html/dist


# copy jar
echo "begin copy measure-gateway "
cp ../measure-gateway/target/measure-gateway.jar ./ruoyi/gateway/jar

echo "begin copy measure-auth "
cp ../measure-auth/target/measure-auth.jar ./ruoyi/auth/jar

echo "begin copy measure-visual "
cp ../measure-visual/measure-monitor/target/measure-visual-monitor.jar  ./ruoyi/visual/monitor/jar

echo "begin copy measure-modules-system "
cp ../measure-modules/measure-system/target/measure-modules-system.jar ./ruoyi/modules/system/jar

echo "begin copy measure-modules-file "
cp ../measure-modules/measure-file/target/measure-modules-file.jar ./ruoyi/modules/file/jar

echo "begin copy measure-modules-job "
cp ../measure-modules/measure-job/target/measure-modules-job.jar ./ruoyi/modules/job/jar

echo "begin copy measure-modules-gen "
cp ../measure-modules/measure-gen/target/measure-modules-gen.jar ./ruoyi/modules/gen/jar

