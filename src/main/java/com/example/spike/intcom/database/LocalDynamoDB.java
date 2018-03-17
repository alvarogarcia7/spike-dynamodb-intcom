package com.example.spike.intcom.database;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.local.main.ServerRunner;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;

public class LocalDynamoDB {

    static AmazonDynamoDB dynamoDb;
    static DynamoDBProxyServer server = null;

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("PASSING on the shutdown hook");
                if (server != null) {
                    try {
                        server.stop();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (dynamoDb != null) {
                    dynamoDb.shutdown();
                }
            }
        });

        System.setProperty("sqlite4java.library.path", "target/dependencies");

        //        try {
        //            // Create an in-memory and in-process instance of DynamoDB Local that skips HTTP
        //            dynamoDb = DynamoDBEmbedded.create().amazonDynamoDB();
        //            // use the DynamoDB API with DynamoDBEmbedded
        //            listTables(dynamoDb.listTables(), "DynamoDB Embedded");
        //        } catch (Exception e) {
        //            e.printStackTrace();
        //        }

        // Create an in-memory and in-process instance of DynamoDB Local that runs over HTTP
        AWSCredentialsProvider credentialsProvider = new ProfileCredentialsProvider();

        final String[] localArgs = { "-inMemory", "-sharedDb" };
        try {
            server = ServerRunner.createServerFromCommandLineArgs(localArgs);
            server.start();

            dynamoDb = AmazonDynamoDBClientBuilder.standard().withCredentials(credentialsProvider).withEndpointConfiguration(
                    // we can use any region here
                    new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2")).build();

            // use the DynamoDB API over HTTP
            listTables(dynamoDb.listTables(), "DynamoDB Local over HTTP");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void listTables(ListTablesResult result, String method) {
        System.out.println("found " + Integer.toString(result.getTableNames().size()) + " tables with " + method);
        for (String table : result.getTableNames()) {
            System.out.println(table);
        }
    }
}
