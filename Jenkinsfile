pipeline {
  agent none
  stages {
    stage('checkout') {
      steps {
        checkout scm
      }
    }
    stage('build') {
      steps {
        node('windows') {
          bat 'mvn --version'
          bat 'mvn clean install'
        }
      }
    }
  }
  post {
    always {
      archive "*/target/**/*"
      junit '*/target/surefire-reports/*.xml'
    }
  }
}