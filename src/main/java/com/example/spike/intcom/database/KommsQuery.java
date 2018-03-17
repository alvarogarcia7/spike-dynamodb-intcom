package com.example.spike.intcom.database;

import java.io.IOException;
import java.util.HashMap;

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

        {
            System.out.println("Matching items for alvaro:");
            HashMap<String, Condition> queryFilters = new HashMap<>();
            queryFilters.put("tags", new Condition().withComparisonOperator(ComparisonOperator.CONTAINS).withAttributeValueList(new AttributeValue("meeting")));
            Komm komm = new Komm();
            komm.setAuthor("alvaro");
            PaginatedQueryList<Komm> matchingItems = mapper.query(Komm.class, new DynamoDBQueryExpression<Komm>().withHashKeyValues(komm).withQueryFilter(queryFilters));
            matchingItems.parallelStream().forEach(System.out::println);
        }
        //                {
        //                    System.out.println("Matching items for bob:");
        //                    final Condition condition = new Condition();
        //                    condition.withComparisonOperator(ComparisonOperator.EQ).withAttributeValueList(new AttributeValue("bob"));
        //                    PaginatedQueryList<Komm> matchingItems = mapper.query(Komm.class, new DynamoDBQueryExpression<Komm>().wihwithQueryFilterEntry("author", condition));
        //                    matchingItems.parallelStream().forEach(System.out::println);
        //                }

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
}
