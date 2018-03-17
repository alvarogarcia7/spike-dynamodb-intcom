package com.example.spike.intcom.database;

import java.io.IOException;
import java.util.Random;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.jetbrains.annotations.NotNull;

public class KommsPumper {

    public static void main(String[] args) throws IOException {
        workWithKomms();
    }

    private static void workWithKomms() {

        final AmazonDynamoDB dynamoDB = LocalClient.client;
        DynamoDBMapper mapper = new DynamoDBMapper(dynamoDB);

        final String dateValue = newDateValue();
        final String hashKeyAlvaro1 = saveItem("alvaro", dateValue, mapper);
        final String hashKeyAlvaro2 = saveItem("alvaro", newDateValue(), mapper);
        final String hashKeyBob1 = saveItem("bob", newDateValue(), mapper);
        final String hashKeyBob2 = saveItem("bob", newDateValue(), mapper);

        // Retrieve the item.
        Komm itemRetrieved = mapper.load(Komm.class, hashKeyAlvaro1, dateValue);
        System.out.println("Item retrieved:");
        System.out.println(itemRetrieved);
    }

    private static String newDateValue() {
        return "" + Math.abs(new Random().nextLong());
    }

    @NotNull
    private static String saveItem(String hashKey, String date, DynamoDBMapper mapper) {
        Komm item = new Komm();
        item.setAuthor(hashKey);
        item.setDate(date);
        item.setBody("heheh");
        item.setTags(hashKey + "-" + "meeting");

        mapper.save(item);
        return hashKey;
    }

    //    ➜  intcom git:(master) ✗ aws dynamodb scan --endpoint http://localhost:8000 --table-name Komms
}
