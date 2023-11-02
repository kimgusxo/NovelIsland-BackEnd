# 베이스 이미지를 지정 (여기서는 OpenJDK 11을 사용)
FROM openjdk:11

# 작업 디렉토리를 지정
WORKDIR /app

# 프로젝트의 jar 파일을 Docker 이미지로 복사
COPY ./build/libs/NovelIsland-0.0.1-SNAPSHOT.jar /app/app.jar

# 컨테이너가 시작될 때 실행될 명령어를 지정
ENTRYPOINT ["java","-jar","/app/app.jar"]
