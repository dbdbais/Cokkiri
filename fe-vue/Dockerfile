#Serve the application with Nginx
FROM nginx:alpine as production-stage
# 빌드된 파일을 Nginx의 HTML 폴더로 복사
COPY /app/dist /usr/share/nginx/html
# 80번 포트를 외부에 노출
EXPOSE 80
# Nginx를 포그라운드 모드로 실행
CMD ["nginx", "-g", "daemon off;"]

#dockrfile 이름 수정
