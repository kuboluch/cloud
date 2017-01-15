pipeline {
  agent none
  stages {
    stage('build') {
      steps {
        sh 'mvn --version'
        sh 'mvn clean install'
      }
    }
  }
}