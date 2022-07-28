cd /home/ubuntu/app

echo $ Copy Jar File >> /home/ubuntu/app/deploy.log
cp /home/ubuntu/app/pinata-server/deploy/*-SNAPSHOT.jar .

BUILD_JAR=$(ls /home/ubuntu/app/*-SNAPSHOT.jar)
DEPLOY_PATH=/home/ubuntu/app/
JAR_NAME=$(basename $BUILD_JAR)
HOME=/home/ubuntu

echo "> build 파일명: $JAR_NAME" >> /home/ubuntu/app/deploy.log

echo "> 현재 실행중인 애플리케이션 pid 확인" >> /home/ubuntu/app/deploy.log
CURRENT_PID=$(pgrep -f $JAR_NAME)

if [ -z $CURRENT_PID ]
then
echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다." >> /home/ubuntu/app/deploy.log
else
echo "> kill -15 $CURRENT_PID"
kill -15 $CURRENT_PID
sleep 5
fi

DEPLOY_JAR=$DEPLOY_PATH$JAR_NAME
echo "> DEPLOY_JAR에 실행 권한 추가 (DEPLOY_JAR 이름: $DEPLOY_JAR)"    >> /home/ubuntu/app/deploy.log
chmod 755 $DEPLOY_JAR

echo "> DEPLOY_JAR 배포 및 실행"    >> /home/ubuntu/app/deploy.log
nohup java -jar -Dspring.profiles.active=prod $DEPLOY_JAR >> /home/ubuntu/app/deploy.log 2>/home/ubuntu/app/deploy_err.log &
