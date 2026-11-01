#!/usr/bin/env bash
# 单文件级编译，不依赖 Gradle。
# 用法：改掉下面几个路径，然后 ./build.sh musiccmd  或  ./build.sh musicguard
set -e

MC_CLIENT_JAR="${MC_CLIENT_JAR:-/path/to/26.3-client.jar}"
FABRIC_LOADER_JAR="${FABRIC_LOADER_JAR:-/path/to/fabric-loader.jar}"
FABRIC_API_JARS="${FABRIC_API_JARS:-/path/to/fabric-api/*}"
MUSIC_HUD_JAR="${MUSIC_HUD_JAR:-/path/to/MusicHUD.jar}"
MIXIN_JAR="${MIXIN_JAR:-/path/to/sponge-mixin.jar}"

NAME="${1:?usage: ./build.sh musiccmd|musicguard}"
CP="$MC_CLIENT_JAR:$FABRIC_LOADER_JAR:$FABRIC_API_JARS:$MUSIC_HUD_JAR:$MIXIN_JAR"

rm -rf out && mkdir -p out
javac -encoding UTF-8 --release 21 -cp "$CP" -d out $(find "$NAME/src" -name '*.java')
jar --create --file "$NAME.jar" -C out . -C "$NAME/resources" .
echo "built $NAME.jar"
