FROM compiler_image

COPY compiler/compiler/build/libs/*.jar app/app.jar

EXPOSE 80

# JAR 파일 실행
ENTRYPOINT ["java", "-jar", "/app/app.jar"]