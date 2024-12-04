pipeline {
    agent any
    environment {
        DOCKERHUB_PASS = credentials('Docker') // DockerHub credentials
        BUILD_TIMESTAMP = new Date().format("yyyyMMdd-HHmmss", TimeZone.getTimeZone("UTC")) // Use a timestamp for image tagging
        PATH = "${env.PATH}:/usr/local/bin" // Fix PATH issue
    }
    stages {
        stage('Check Maven and Docker') {
            steps {
                // Check if Maven and Docker are available
                sh 'mvn -v'
                sh 'docker --version'
            }
        }
        stage("Building the Spring Boot Application Image") {
            steps {
                script {
                    // Fetch the current SCM and print the timestamp
                    checkout scm
                    echo "Build timestamp: ${BUILD_TIMESTAMP}"

                    // Login to DockerHub
                    sh '''
                        echo "${DOCKERHUB_PASS_PSW}" | docker login -u gkaramth --password-stdin
                    '''

                    // Build the application using Maven
                    sh 'mvn clean package'

                    // Build the Docker image
                    sh "docker build -t gkaramth/hw3:01-${BUILD_TIMESTAMP} ."
                }
            }
        }
        stage("Pushing Image to DockerHub") {
            steps {
                script {
                    // Push the image to DockerHub
                    sh "docker push gkaramth/hw3:01-${BUILD_TIMESTAMP}"
                }
            }
        }
        stage("Deploy to Kubernetes") {
            steps {
                // Inject kubeconfig from Jenkins credentials store
                withCredentials([file(credentialsId: 'kubeconfig', variable: 'KUBECONFIG')]) {

                    // Update the Kubernetes deployment with the new image
                    sh "kubectl set image deployment/deployment-hw3 container-hw3=gkaramth/hw3:01-${BUILD_TIMESTAMP} -n default"
                }
            }
        }
    }
}
