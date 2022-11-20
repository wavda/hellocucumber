pipeline {
    agent any
    tools {
        maven 'Maven 3.6.3'
        jdk 'JDK_1.8_221'
    }

    options {
        timeout(time: 30, unit: 'MINUTES')
    }

    stages {
        stage('npm install dependencies') {
            steps {
                sh 'npm install'
            }
        }
        stage('Start Docker') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    sh 'docker-compose up -d'
                }
            }
        }
        stage('Tests') {
            steps {
                withMaven(options: [junitPublisher(disabled: true)], maven: 'Maven 3.6.3') {
                    sh ('mvn clean test')
                }
            }
        }
        stage('Generate report') {
            steps {
                withMaven(options: [junitPublisher(disabled: true)], maven: 'Maven 3.6.3') {
                    sh "mvn allure:report"
                }
            }
        }
    }
    post {
        always {
            publishHTML (target : [allowMissing: false,
                            alwaysLinkToLastBuild: true,
                            keepAll: true,
                            reportDir: 'target/',
                            reportFiles: 'site/allure-maven-plugin/index.html',
                            reportName: 'Test Report',
                            reportTitles: 'Test Report'])

            sh 'docker-compose down'

            script {
                summary = junit testResults: '**/target/surefire-reports/TEST-*.xml'
            }
        }
    }
}
