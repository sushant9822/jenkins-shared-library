def call()
{

    /* 
    Below are the variables required as environment variable for this function
    ENV
    BUCKET_NAME
    DIST_ID
    */
    sh '''
        npm install
	    npm run build:"${ENV}"
	    ls -l ./dist
	    aws s3 sync ./dist/ s3://"${BUCKET_NAME}"
        aws cloudfront create-invalidation --distribution-id "${DIST_ID}" --paths "/*"					
    '''
}