pipeline {
  agent none
  stages {
    stage('build') {
      steps {
        bat 'mvn --version'
        bat 'mvn clean install'
      }
    }
  }
}