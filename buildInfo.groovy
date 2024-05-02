def call()
{
    /*
    Below are the variables required as environment variable for this function
    ENV
    PROJECT
    APP_NAME
    JOB_TYPE
    PROFILE
    */
    
    sh '''
    	DATE=$(date '+%d-%m-%Y-%H-%M-%S')
        echo """ 
        ####################################################
            cyvx $ENV $APP_NAME
        ####################################################

        JOB_NAME:-
        -> $APP_NAME

        AUTHOR:-
        -> $(git show -s --pretty=%an)

        GIT_COMMIT_MSG:-
        -> $(git show -s --pretty=%B)

        BUILD NUMBER:-
        -> $BUILD_NUMBER

        BUILD URL:-
        -> $BUILD_URL 

        GIT BRANCH:-
        -> $(git branch --show-current) 

        GIT COMMIT:-
        -> $(git show -s --pretty=%H)

        TAG DATE:-
        -> $DATE


        ####################################################
	"""
     '''
}