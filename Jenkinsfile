pipeline {
  agent { label 'windows' }
  stages {
    stage('checkout') {
      steps {
        checkout scm
      }
    }
    stage('build') {
      steps {
        bat 'mvn --version'
        bat 'mvn clean install'
      }
    }
  }
  post {
    always {
      archive "*/target/*.jar"
      junit '*/target/surefire-reports/*.xml'
    }
  }
}