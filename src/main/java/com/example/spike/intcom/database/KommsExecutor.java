package com.example.spike.intcom.database;

import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import org.jetbrains.annotations.NotNull;

public class KommsExecutor {

    public static void main(String[] args) throws IOException {
        workWithKomms();
    }

    @DynamoDBTable(tableName = "Komms")
    public static class Komm {
        private String author;
        private String body;
        private String tags;
        private String date;

        // Partition key
        @DynamoDBHashKey(attributeName = "author")
        public String getAuthor() {
            return this.author;
        }

        @DynamoDBRangeKey(attributeName = "date")
        @DynamoDBAttribute(attributeName = "date")
        public String getDate() {
            return this.date;
        }

        @DynamoDBAttribute(attributeName = "body")
        public String getBody() {
            return this.body;
        }

        @DynamoDBAttribute(attributeName = "tags")
        public String getTags() {
            return this.tags;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }

        public void setDate(String date) {
            this.date = date;
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("Komm{");
            sb.append("author='").append(this.author).append('\'');
            sb.append(", body='").append(this.body).append('\'');
            sb.append(", tags='").append(this.tags).append('\'');
            sb.append(", date='").append(this.date).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }

    private static void workWithKomms() {

        final AmazonDynamoDB dynamoDB = LocalClient.client;
        DynamoDBMapper mapper = new DynamoDBMapper(dynamoDB);

        final String randomValue1 = newRandomValue();
        final String hashKey1 = saveItem("alvaro", randomValue1, mapper);
        final String hashKey2 = saveItem("alvaro", newRandomValue(), mapper);

        // Retrieve the item.
        Komm itemRetrieved = mapper.load(Komm.class, hashKey1, randomValue1);
        System.out.println("Item retrieved:");
        System.out.println(itemRetrieved);

        System.out.println("Matching items:");
        HashMap<String, Condition> queryFilters = new HashMap<>();
        queryFilters.put("tags", new Condition().withComparisonOperator(ComparisonOperator.CONTAINS).withAttributeValueList(new AttributeValue("meeting")));
        final Komm hashKey11 = new Komm();
        hashKey11.setAuthor("alvaro");
        PaginatedQueryList<Komm> matchingItems = mapper.query(Komm.class, new DynamoDBQueryExpression<Komm>().withHashKeyValues(hashKey11).withQueryFilter(queryFilters));
        matchingItems.parallelStream().forEach(System.out::println);

        //        // Update the item.
        //        itemRetrieved.setISBN("622-2222222222");
        //        itemRetrieved.setTags(new HashSet<String>(Arrays.asList("Author1", "Author3")));
        //        mapper.save(itemRetrieved);
        //        System.out.println("Item updated:");
        //        System.out.println(itemRetrieved);
        //
        //        // Retrieve the updated item.
        //        DynamoDBMapperConfig config = new DynamoDBMapperConfig(DynamoDBMapperConfig.ConsistentReads.CONSISTENT);
        //        Komm updatedItem = mapper.load(Komm.class, 601, config);
        //        System.out.println("Retrieved the previously updated item:");
        //        System.out.println(updatedItem);
        //
        //        // Delete the item.
        //        mapper.delete(updatedItem);
        //
        //        // Try to retrieve deleted item.
        //        Komm deletedItem = mapper.load(Komm.class, updatedItem.getAuthor(), config);
        //        if (deletedItem == null) {
        //            System.out.println("Done - Sample item is deleted.");
        //        }
    }

    private static String newRandomValue() {
        return "" + Math.abs(new Random().nextLong());
    }

    @NotNull
    private static String saveItem(String hashKey, String date, DynamoDBMapper mapper) {
        Komm item = new Komm();
        item.setAuthor(hashKey + "#" + date);
        item.setDate(date);
        item.setBody("heheh");
        item.setTags("alvaro-meeting");

        mapper.save(item);
        return hashKey;
    }
}
