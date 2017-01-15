pipeline {
  agent none
  stages {
    stage('build') {
      steps {
        node('windows') {
          bat 'mvn --version'
          bat 'mvn clean install'
        }
      }
    }
  }
}