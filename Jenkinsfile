@Library('ci-skip') _
pipeline {
    environment {
        JENKINS_NODE_COOKIE = 'dontKillMe'
    }
    agent any
    stages {
        stage('prepare') { steps { ciSkip 'check' } }
        stage('build') {
            steps {
                sh './gradlew clean build'
            }
        }
        stage('deploy-develop') {
            when { branch 'develop' }
            environment {
                PEARX_REPO = credentials('pearx-repo-user')
            }
            steps {
                sh "./gradlew publishDevelop -PpearxRepoUsername=${PEARX_REPO_USR} -PpearxRepoPassword=${PEARX_REPO_PSW} -PdevBuildNumber=${BUILD_NUMBER}"
            }
        }

        stage('deploy-release') {
            when { branch 'master' }
            environment {
                PEARX_REPO = credentials('pearx-repo-user')
            }
            steps {
                sh "./gradlew publishRelease -PpearxRepoUsername=${PEARX_REPO_USR} -PpearxRepoPassword=${PEARX_REPO_PSW}"
            }
        }
    }
    post {
        always {
            ciSkip 'postProcess'
            junit 'modules/*/jvm/build/test-results-prefixed/**/*.xml'
            junit 'modules/*/js/build/test-results-prefixed/**/*.xml'

        }
    }
}
