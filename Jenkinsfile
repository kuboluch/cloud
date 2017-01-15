pipeline {
  agent none
  stages {
    stage('build') {
        sh 'mvn --version'
        sh 'mvn clean install'
    }
  }
}