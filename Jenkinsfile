pipeline {
	agent any
	environment {
		DOCKERHUB_PASS = credentials('Docker')
		BUILD_TIMESTAMP = new Date().format("yyyyMMdd-HHmmss", TimeZone.getTimeZone("UTC"))
	}
	stages {
		stage("Building the Springboot application image") {
			steps {
				script {
					checkout scm
					sh 'echo ${BUILD_TIMESTAMP}'
					sh 'docker login -u gkaramth -p ${DOCKERHUB_PASS_PSW}'
					sh 'mvn clean package'
					def customImage = docker.build("gkaramth/hw3-springboot-app:${BUILD_TIMESTAMP}")
				}
			}
		}
		stage("Pushing image to Dockerhub") {
			steps {
				script {
					sh 'docker push gkaramth/hw3-springboot-app:${BUILD_TIMESTAMP}'
				}
			}
		}
		stage("Deploying to Rancher as single pod") {
			steps {
				sh 'kubectl set image deployment/deployment-hw3 container-hw3=gkaramth/hw3-springboot-app:${BUILD_TIMESTAMP} -n default'
			}
		}
	}
}
