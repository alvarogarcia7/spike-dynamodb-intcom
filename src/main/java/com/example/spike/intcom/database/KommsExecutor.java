package com.example.spike.intcom.database;

import java.io.IOException;
import java.util.Random;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import org.jetbrains.annotations.NotNull;

public class KommsExecutor {

    public static void main(String[] args) throws IOException {
        workWithKomms();
    }

    @DynamoDBTable(tableName = "Komms")
    public static class Komm {
        private String authorDate;
        private String body;
        private String tags;
        private String date;

        // Partition key
        @DynamoDBHashKey(attributeName = "author-date")
        public String getAuthorDate() {
            return this.authorDate;
        }

        public void setAuthorDate(String authorDate) {
            this.authorDate = authorDate;
        }

        @DynamoDBAttribute(attributeName = "body")
        public String getBody() {
            return this.body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        @DynamoDBAttribute(attributeName = "tags")
        public String getTags() {
            return this.tags;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }

        @DynamoDBRangeKey(attributeName = "date")
        @DynamoDBAttribute(attributeName = "date")
        public String getDate() {
            return this.date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("Komm{");
            sb.append("authorDate='").append(this.authorDate).append('\'');
            sb.append(", body='").append(this.body).append('\'');
            sb.append(", tags='").append(this.tags).append('\'');
            sb.append(", date='").append(this.date).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }

    private static void workWithKomms() {

        DynamoDBMapper mapper = new DynamoDBMapper(LocalClient.client);

        final long randomValue1 = newRandomValue();
        final String hashKey1 = saveItem(randomValue1, mapper);
        final String hashKey2 = saveItem(newRandomValue(), mapper);

        // Retrieve the item.
        Komm itemRetrieved = mapper.load(Komm.class, hashKey1, "" + randomValue1);
        System.out.println("Item retrieved:");
        System.out.println(itemRetrieved);

        //        mapper.query(Komm.class, new DynamoDBQueryExpression<Komm>().)

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
        //        Komm deletedItem = mapper.load(Komm.class, updatedItem.getAuthorDate(), config);
        //        if (deletedItem == null) {
        //            System.out.println("Done - Sample item is deleted.");
        //        }
    }

    private static long newRandomValue() {
        return Math.abs(new Random().nextLong());
    }

    @NotNull
    private static String saveItem(long date, DynamoDBMapper mapper) {
        Komm item = new Komm();
        final String hashKey = "alvaro-12" + date;
        item.setAuthorDate(hashKey);
        item.setDate("" + date);
        item.setBody("heheh");
        item.setTags("alvaro-meeting");

        mapper.save(item);
        return hashKey;
    }
}
