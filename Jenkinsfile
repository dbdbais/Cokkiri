pipeline {
    agent any

    environment {
        DOCKER_IMAGE_COM = 'be-compiler'
        PATH = "${tool 'Cokirri-node'}/bin:${env.PATH}"
        DOCKER_IMAGE_BE = 'ohuggy/cokkiri_develop_be'
        DOCKER_IMAGE_FE = 'ohuggy/cokkiri_develop_fe'
        DOCKER_HUB = 'dockerhub' // Jenkins 자격 증명 ID
//         PATH = "/usr/local/bin"

    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'develop',
                    url: 'https://lab.ssafy.com/s11-webmobile1-sub2/S11P12E108.git',
                    credentialsId: 'Be' // Jenkins 자격 증명 ID
            }
        }

        stage('properties copy'){
            steps{
                withCredentials([file(credentialsId: 'Cokkiri-spring-properties', variable: 'properties')]) {
                    script {
                        sh 'pwd'
                        sh 'ls'
                        sh 'chmod +r $properties'
                        sh 'chmod -R 777 be-spring/iscode/iscode/src/main/resources'
                        sh 'cp $properties be-spring/iscode/iscode/src/main/resources/application.properties'
                        sh 'ls'
                    }
                    }
                    
            }
        }

        stage("Install Dependencies"){
            steps{
                dir("fe-vue"){
                    script{
                        // Node.js 설치 확인 및 종속성 설치
                        sh 'node -v'
                        sh 'npm -v'
                        sh 'npm install'
                    }

                }
            }
        }


        stage('Build') {
            steps {
                dir('be-spring/iscode/iscode') {
                    script {
                        def gradleHome = tool name: 'Cokkiri-gradle', type: 'gradle'
                        sh 'chmod 777 gradlew'
                        sh './gradlew bootJar'
                        sh 'ls build/libs'  // 추가하여 파일 존재 확인
                    }
                }

                dir('compiler/compiler') {
                    script {
                        def gradleHome = tool name: 'Cokkiri-gradle', type: 'gradle'
                            sh 'chmod 777 gradlew'
                            sh './gradlew bootJar'
                    }
                }

                dir('fe-vue') {
                    script {
                        // .env 파일을 로드하여 환경 변수를 설정합니다.
                        sh 'npm install @rollup/rollup-linux-x64-gnu --save-optional'
                        sh 'npm run build'
                        sh 'cat .env'
                    }
                }

            }
        }

        stage('Archive') {
            steps {
                dir('be-spring/iscode/iscode') {
                    archiveArtifacts artifacts: '**/build/libs/*.jar', allowEmptyArchive: true
                }
                dir('compiler/compiler') {
                     archiveArtifacts artifacts: '**/build/libs/*.jar', allowEmptyArchive: true
                }
            }
        }

        stage('Build Docker Image') {
            steps {

                dir('be-spring'){
                    script {
                        sh 'docker build -t ${DOCKER_IMAGE_BE} .'
                    }
                }

                dir('compiler'){
                    script {
                        sh 'docker build -t ${DOCKER_IMAGE_COM} .'
                    }
                }

                dir('fe-vue'){
                    script {
                        sh 'docker build -t ${DOCKER_IMAGE_FE} .'
                    }
                }

            }
        }

        stage('Push Docker Image to Docker Hub') {
            steps {
                script {
                    docker.withRegistry('https://index.docker.io/v1/', env.DOCKER_HUB) {
                        // 백엔드 이미지 푸시
                        sh 'docker push ${DOCKER_IMAGE_BE}'
                        // 프론트엔드 이미지 푸시
                        sh 'docker push ${DOCKER_IMAGE_FE}'
                        // 컴파일러 이미지 푸시
                        sh 'docker push ${DOCKER_IMAGE_COM}'
                    }
                    
                }
                // script {
                //     docker.withRegistry('https://index.docker.io/v1/', env.DOCKER_HUB) {

                //     }
                // }

                // script {
                //     docker.withRegistry('https://index.docker.io/v1/', env.DOCKER_HUB) {
                //         // 컴파일러 이미지 푸시
                //         sh 'docker push ${DOCKER_IMAGE_COM}'
                //     }
                // }
            }
        }

        stage('Deploy Docker Container with docker compose'){
            steps{
                script{
                    // Docker Compose 실행 전에 기존 컨테이너를 종료 및 제거
                    sh 'docker rm -f mysql'
                    sh 'docker rm -f vuejs'
                    sh 'docker rm -f Cokkiri-springboot'
                    sh 'docker rm -f Cokkiri-compiler'
                    sh 'docker-compose down'
                    // Docker Compose를 사용하여 서비스 빌드 및 실행
                    sh 'docker-compose up --build -d'
                }
            }
        }
    }

    post {
        always {
            script {
                // Cleanup: Docker Hub에서 로그아웃
                sh 'docker logout'
            }
        }
    }
}
