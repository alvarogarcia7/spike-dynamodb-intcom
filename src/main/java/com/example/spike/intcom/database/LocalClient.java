package com.example.spike.intcom.database;

import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

public class LocalClient {
    public static AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(new EndpointConfiguration("http://localhost:8000", "any")).build();
}
