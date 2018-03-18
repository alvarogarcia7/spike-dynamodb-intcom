package com.example.spike.intcom.database

import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder

class LocalClient {

    val remote: AmazonDynamoDB
        get(): AmazonDynamoDB {
            return AmazonDynamoDBClientBuilder.standard()
                    .withCredentials(ProfileCredentialsProvider())
                    .withRegion("us-west-1")
                    .build()
        }

    companion object {
        var client = AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(EndpointConfiguration("http://localhost:8000", "any")).build()
    }


}
