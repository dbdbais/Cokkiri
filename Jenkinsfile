pipeline {
    agent any

    environment {
        PATH = "${tool 'Cokirri-node'}/bin:${env.PATH}"
        DOCKER_IMAGE_BE = 'ohuggy/Cokkiri_develop_BE'
        DOCKER_IMAGE_FE = 'ohuggy/Cokkiri_develop_FE'
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
            }
        }

        stage('Build Docker Image') {
            steps {
                dir('be-spring'){
                    script {
                        sh 'docker build -t ${DOCKER_IMAGE} .'
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

        stage('Push Docker Image to Docker Hub') {
            steps {
                script {
                    docker.withRegistry('https://index.docker.io/v1/', env.DOCKER_HUB) {
                        // Docker 이미지 푸시
                        sh 'docker push ${DOCKER_IMAGE_BE}'
                    }
                    
                }
                script {
                    docker.withRegistry('https://index.docker.io/v1/', env.DOCKER_HUB) {
                        // Docker 이미지 푸시
                        sh 'docker push ${DOCKER_IMAGE_FE}'
                    }
                }

            }
        }

        stage('Deploy Docker Container with docker compose'){
            steps{
                script{
                    // Docker Compose 실행 전에 기존 컨테이너를 종료 및 제거
                    sh 'docker rm -f mysql'
                    // sh 'docker rm -f Cokkiri-springboot'
                    
                    sh 'docker-compose down'
                    // Docker Compose를 사용하여 서비스 빌드 및 실행
                    sh 'docker-compose up --build -d'
                }
            }
        }
//         stage('Deploy Docker Container') {
//             steps {
//                 script {
//                     // 기존 컨테이너가 존재하면 삭제
//                     sh 'docker rm -f be_test_container || true'
//
//                     // 새로운 컨테이너 생성 및 실행
//                     sh 'docker run -d --name be_test_container ${DOCKER_IMAGE}'
//                 }
//             }
//         }
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
