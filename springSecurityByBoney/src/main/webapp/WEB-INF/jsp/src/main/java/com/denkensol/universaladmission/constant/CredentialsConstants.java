package com.denkensol.universaladmission.constant;

import org.springframework.stereotype.Component;

@Component
public class CredentialsConstants {

	public static final String AWS_ACCESS_KEY_ID = System.getenv("AWS_ACCESS_KEY_ID");
	// @Value("${aws.s3.credentials.accessKey}")
	public static final String AWS_SECRETE_ACCESS_KEY = System.getenv("AWS_SECRET_ACCESS_KEY");
	
	public static final String AWS_S3_BUCKET_NAME = "s3-us-west-2-dev";

}
