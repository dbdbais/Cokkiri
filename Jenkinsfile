pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'be-compiler'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'infra-be/compiler-container',
                    url: 'https://lab.ssafy.com/s11-webmobile1-sub2/S11P12E108.git',
                    credentialsId: 'Be' // Jenkins 자격 증명 ID
            }
        }

        stage('Build') {
            steps {
                dir('compiler/compiler') {
                    script {
                        def gradleHome = tool name: 'Cokkiri-gradle', type: 'gradle'
                            sh 'chmod 777 gradlew'
                            sh './gradlew bootJar'
                    }
                }
            }
        }
        stage('Archive') {
            steps {
                dir('compiler/compiler') {
                    archiveArtifacts artifacts: '**/build/libs/*.jar', allowEmptyArchive: true
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    sh 'docker build -t ${DOCKER_IMAGE} .'
                }
            }
        }

        stage('run container'){
            steps{
                sh 'docker rm -f compiler_mysql_test'
                sh 'docker rm -f compiler_server_test'
                sh 'docker-compose down'
                sh 'docker-compose up --build -d'
            }
        }


    }
}
