def call(){
        /* ENV

        REGION = "ap-south-1"
        REPO_NAME = "node"
        APP_NAME = "auth-api"
        DOCKER_FILE_PATH = "Deployment/Dockerfile"


        */
    
        sh'''
        sudo chmod 777 /var/run/docker.sock
        aws ecr describe-repositories --repository-names ${REPO_NAME} --region ${REGION}
        REPO_URI=$(aws ecr describe-repositories --repository-names ${REPO_NAME} --region ${REGION} | grep -i uri | cut -d '"' -f4)
        aws ecr get-login-password --region ${REGION} | docker login --username AWS --password-stdin $(echo $REPO_URI | cut -d '/' -f1)
        
        docker build -t ${APP_NAME} -f ${DOCKER_FILE_PATH} --no-cache .
        docker tag ${APP_NAME} ${REPO_URI}:latest
        docker push ${REPO_URI}:latest
        '''
}
