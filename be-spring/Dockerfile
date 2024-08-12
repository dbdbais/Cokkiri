# 베이스 이미지 설정
FROM openjdk:17-jdk-slim


# JAR 파일을 컨테이너에 복사
COPY iscode/iscode/build/libs/*.jar /app/app.jar

# JAR 파일 실행
ENTRYPOINT ["java", "-jar", "/app/app.jar"]