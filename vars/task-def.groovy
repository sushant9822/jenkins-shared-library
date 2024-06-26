def call (){

    /* ENV 
    
    FAMILY = task-deination-name
    PORT = 
    LOG_NAME = 
    REGION =
    S3_URL = 
    TASK_ROLE = 
    CPU = 
    MEMORY= 
    CLUSTER=
    SERVICE =
    CONTAINER_NAME = 

       

    */

    
    sh '''
        
        REPO_URI=$(aws ecr describe-repositories --repository-names ${REPO_NAME} --region ${REGION} | grep -i uri | cut -d '"' -f4)

        json='{
            "containerDefinitions": [
                {
                    "name": "%CONTAINER_NAME%",
                    "image": "%REPO_URI%:latest",
                    "logConfiguration": {
                                                "logDriver": "awslogs",
                                                "options": {
                                                    "awslogs-group": "/ecs/%LOG_NAME%",
                                                    "awslogs-region": "%REGION%",
                                        "awslogs-create-group": "true",
                                                    "awslogs-stream-prefix": "ecs"
                                                }
                    },
                    "essential": true,
                    "portMappings": [
                        {
                        "hostPort": %PORT%,
                        "protocol": "tcp",
                        "containerPort": %PORT%
                        }
                    ],
                   "environmentFiles": [
                        {   "type": "s3",
                            "value": "%S3_ARN%"
                        }
                    ]
  
                }
            ],
            "family": "%FAMILY%"
        }'

        echo $json > td.json

        sed -i -e "s;%FAMILY%;${FAMILY};g" td.json
        sed -i -e "s;%REPO_URI%;${REPO_URI};g" td.json
        sed -i -e "s;%REGION%;${REGION};g" td.json
        sed -i -e "s;%PORT%;${PORT};g" td.json
        sed -i -e "s;%LOG_NAME%;${LOG_NAME};g" td.json
        sed -i -e "s;%TASK_EXEC_ROLE%;${TASK_EXEC_ROLE};g" td.json
        sed -i -e "s;%CONTAINER_NAME%;${CONTAINER_NAME};g" td.json
        sed -i -e "s;%S3_ARN%;${S3_URI};g" td.json
  
        
        aws ecs register-task-definition --family ${FAMILY} --region  ${REGION} --execution-role-arn ${TASK_EXEC_ROLE} --task-role-arn ${TASK_EXEC_ROLE} --requires-compatibilities FARGATE  --cpu $CPU --memory $MEMORY --network-mode awsvpc --cli-input-json file://td.json

    '''
}