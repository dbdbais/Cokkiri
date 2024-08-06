# # 베이스 이미지 설정
# FROM node:16-alpine AS builder

# # 작업 디렉토리 설정
# WORKDIR /app

# # 패키지 파일 복사 및 종속성 설치
# COPY package*.json ./
# RUN npm install

# # 애플리케이션 소스 코드 및 .env 파일 복사
# COPY . .

# # 환경 변수를 로드하여 빌드 실행
# RUN export $(cat .env | xargs) && npm run build

# 프로덕션 이미지 설정
#Serve the application with Nginx
FROM nginx:alpine as production-stage
# 빌드된 파일을 Nginx의 HTML 폴더로 복사
COPY dist /usr/share/nginx/html
# 80번 포트를 외부에 노출
EXPOSE 80
# Nginx를 포그라운드 모드로 실행
CMD ["nginx", "-g", "daemon off;"]

#dockrfile 이름 수정
