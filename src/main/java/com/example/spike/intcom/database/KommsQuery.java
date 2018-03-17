package com.example.spike.intcom.database;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;

public class KommsQuery {

    public static void main(String[] args) throws IOException {
        workWithKomms();
    }

    private static void workWithKomms() {

        final AmazonDynamoDB dynamoDB = LocalClient.client;
        DynamoDBMapper mapper = new DynamoDBMapper(dynamoDB);

        System.out.println("Matching items for alvaro:");
        HashMap<String, Condition> queryFilters = new HashMap<>();
        queryFilters.put("tags", new Condition().withComparisonOperator(ComparisonOperator.CONTAINS).withAttributeValueList(new AttributeValue("meeting")));
        //            queryFilters.put("tags", new Condition().withComparisonOperator(ComparisonOperator.NOT_CONTAINS).withAttributeValueList(new AttributeValue("meeting")));
        Komm komm = new Komm();
        komm.setAuthor("alvaro");
        Map<String, String> aN = new HashMap<>();
        aN.put("#v1", "tags");
        aN.put("#v2", "tags");
        Map<String, AttributeValue> vs = new HashMap<>();
        vs.put(":value1", new AttributeValue("meeting"));
        vs.put(":value2", new AttributeValue("headline"));

        PaginatedQueryList<Komm> matchingItems = mapper.query(Komm.class,
                new DynamoDBQueryExpression<Komm>().withHashKeyValues(komm).withExpressionAttributeNames(aN).withExpressionAttributeValues(vs)
                        .withFilterExpression("contains(#v1, :value1) or contains(#v2, :value2)"));
        matchingItems.parallelStream().forEach(System.out::println);
    }
}
