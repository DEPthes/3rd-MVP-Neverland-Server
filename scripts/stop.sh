#!/usr/bin/env bash

PROJECT_ROOT="/home/ubuntu/app"
JAR_FILE="spring-webapp.jar"
DEPLOY_LOG="$PROJECT_ROOT/deploy.log"

TIME_NOW=$(date +%c)

# 현재 구동 중인 애플리케이션 PID 확인
CURRENT_PID=$(pgrep -f "java -jar $PROJECT_ROOT/$JAR_FILE")

# 프로세스가 켜져 있으면 종료
if [ -z "$CURRENT_PID" ]; then
  echo "$TIME_NOW > 현재 실행중인 애플리케이션이 없습니다" >> $DEPLOY_LOG
else
  echo "$TIME_NOW > 실행중인 $CURRENT_PID 애플리케이션 종료" >> $DEPLOY_LOG
  kill -15 $CURRENT_PID
  
  # 종료 후 잠시 기다리기
  sleep 10
  
  # 프로세스가 아직 종료되지 않았을 경우 강제 종료
  CURRENT_PID=$(pgrep -f "java -jar $PROJECT_ROOT/$JAR_FILE")
  if [ ! -z "$CURRENT_PID" ]; then
    echo "$TIME_NOW > 강제 종료 프로세스 $CURRENT_PID" >> $DEPLOY_LOG
    kill -9 $CURRENT_PID
  fi
fi

# 포트가 해제될 때까지 확인
for i in {1..10}; do
  if ! lsof -i :8080 > /dev/null 2>&1; then
    echo "$TIME_NOW > 포트 8080이 해제되었습니다." >> $DEPLOY_LOG
    break
  fi
  echo "$TIME_NOW > 포트 8080이 아직 사용 중입니다. $i 번째 대기..." >> $DEPLOY_LOG
  sleep 1
done
