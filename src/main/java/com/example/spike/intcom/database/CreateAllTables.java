package com.example.spike.intcom.database;

import java.util.Arrays;
import java.util.List;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.Projection;
import com.amazonaws.services.dynamodbv2.model.ProjectionType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;

public class CreateAllTables {

    public static void main(String[] args) throws Exception {

        DynamoDB dynamoDB = new DynamoDB(X.client);

        String tableName = "Komms";

        try {
            System.out.println("Attempting to create table " + tableName + " ; please wait...");
            final CreateTableRequest createTableRequest = new CreateTableRequest();
            final ProvisionedThroughput provisionedThroughput = new ProvisionedThroughput(1L, 1L);
            final GlobalSecondaryIndex globalSecondaryIndex = new GlobalSecondaryIndex();
            globalSecondaryIndex.setIndexName("tags-index");
            globalSecondaryIndex.setKeySchema(Arrays.asList(new KeySchemaElement("tags", KeyType.HASH)));
            final Projection projection = new Projection();
            projection.setNonKeyAttributes(Arrays.asList("tag"));
            projection.setProjectionType(ProjectionType.INCLUDE);
            globalSecondaryIndex.setProjection(projection);
            globalSecondaryIndex.setProvisionedThroughput(provisionedThroughput);
            final List<KeySchemaElement> keys = Arrays.asList(new KeySchemaElement("author-date", KeyType.HASH), new KeySchemaElement("date", KeyType.RANGE));
            final List<AttributeDefinition> attributes =
                    Arrays.asList(new AttributeDefinition("author-date", ScalarAttributeType.S), new AttributeDefinition("date", ScalarAttributeType.S),
                            new AttributeDefinition("tags", ScalarAttributeType.S));

            createTableRequest.withKeySchema(keys).withTableName(tableName).withAttributeDefinitions(attributes).withGlobalSecondaryIndexes(Arrays.asList(globalSecondaryIndex))
                    .withProvisionedThroughput(provisionedThroughput);
            ////
            Table table = dynamoDB.createTable(createTableRequest);
            table.waitForActive();
            System.out.println("Success.  Table status: " + table.getDescription().getTableStatus());

        } catch (Exception e) {
            System.err.println("Unable to create table: ");
            System.err.println(e.getMessage());
        }

    }
}
