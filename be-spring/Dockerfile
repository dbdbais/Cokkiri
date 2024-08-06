FROM compiler_image

COPY be-spring/iscode/iscode/build/libs/*.jar app/app.jar

# JAR 파일 실행
ENTRYPOINT ["java", "-jar", "/app/app.jar"]