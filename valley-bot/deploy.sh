#!/bin/sh
echo "Packaging using Gradle"
gradle clean
gradle fatJar
echo "Deploying to mywb.vcs.net"
scp ./build/libs/valley-bot-all-1.0-SNAPSHOT.jar supadmin@mywb.vcs.net:~/valley-bot.jar
echo "Deployment complete"
echo "Restarting server"