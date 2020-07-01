#!/bin/bash

REPOSITORY=/home/ec2-user/project
REPOSITORY_NAME=Messenger_Backend
PROJECT_NAME=messenger

cd $REPOSITORY/$REPOSITORY_NAME

echo "Git Pull ..."

git pull

echo "Start Build ..."

./gradlew build

echo "Copy Build Result ..."

cp ./build/libs/*.jar $REPOSITORY/deploy/

echo "Check running application pid"

CURRENT_PID = $(pgrep -fl $PROJECT_NAME)

if [ -z $CURRENT_PID ]; then
	echo "There is no running application"
else
    echo "kill running application $CURRENT_PID"
	kill -15 $CURRENT_PID
	sleep 5
fi

echo "Deploy application"

JAR_NAME=$(ls $REPOSITORY/deploy/ | grep $PROJECT_NAME | tail -n 1)

echo "Jar file $JAR_NAME"

nohup java -jar $REPOSITORY/deploy/$JAR_NAME &