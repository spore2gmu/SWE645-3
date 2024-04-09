pipeline {
   environment {
        registry = "spore2/swe"
        registryCredential = 'DockerHubCredentials'
        TIMESTAMP = new Date().format("yyyy-MM-dd_HH-mm-ss")
        KUBECONFIG_CREDENTIALS_ID = 'myKubeconfigID'
        DOCKER_IMAGE = "spore2/swe:${env.TIMESTAMP}"
    }
    agent any
   tools {
    maven 'Maven'
	}

   stages {
       stage('Maven Clean') {
            steps {
               script{
                sh 'mvn clean'
               }
            }
        }
        stage('Maven Install') {
            steps {
               script{
                sh 'mvn install'
            }
            }
        }
      stage('Build') {
         steps {
            script{
               docker.withRegistry('',registryCredential){
                  def customImage = docker.build("$DOCKER_IMAGE")
               }
            }
         }
      }

      stage('Push Image to Dockerhub') {
         steps {
            script{
               docker.withRegistry('',registryCredential){
                  sh "docker push $DOCKER_IMAGE"
               }
            }
         }
      }

      stage('Deploying to Rancher as Load Balancer') {
            steps {
                withCredentials([file(credentialsId: "$KUBECONFIG_CREDENTIALS_ID", variable: 'KUBECONFIG')]) {
                    sh 'kubectl set image deployment/survey-app-deployment swe=$DOCKER_IMAGE'
                }
            }
        }

   }
}