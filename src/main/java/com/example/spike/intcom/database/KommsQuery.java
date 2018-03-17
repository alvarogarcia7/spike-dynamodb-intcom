package com.example.spike.intcom.database;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

public class KommsQuery {

    public static void main(String[] args) throws IOException {
        final HashMap<String, String> attributeNames = new HashMap<>();
        attributeNames.put("#v1", "tags");
        attributeNames.put("#v2", "tags");
        final HashMap<String, AttributeValue> attributeValues = new HashMap<>();
        attributeValues.put(":value1", new AttributeValue("meeting"));
        attributeValues.put(":value2", new AttributeValue("headline"));
        queryByTagsFilteringOr("bob", attributeNames, attributeValues);
    }

    private static void queryByTagsFilteringOr(String username, Map<String, String> attributeNames, Map<String, AttributeValue> values) {

        final AmazonDynamoDB dynamoDB = LocalClient.client;
        DynamoDBMapper mapper = new DynamoDBMapper(dynamoDB);

        System.out.println("Matching items:");
        Komm komm = new Komm(username, "", "", "");

        PaginatedQueryList<Komm> matchingItems = mapper.query(Komm.class,
                new DynamoDBQueryExpression<Komm>().withHashKeyValues(komm).withExpressionAttributeNames(attributeNames).withExpressionAttributeValues(values)
                        .withFilterExpression("contains(#v1, :value1) or contains(#v2, :value2)"));
        matchingItems.parallelStream().forEach(System.out::println);
    }
}
