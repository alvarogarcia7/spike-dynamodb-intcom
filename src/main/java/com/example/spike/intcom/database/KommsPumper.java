package com.example.spike.intcom.database;

import java.io.IOException;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        final String hashKeyAlvaro1 = saveItem(mapper, "alvaro", dateValue, "meeting");
        saveItem(mapper, "alvaro", newDateValue(), "meeting");
        saveItem(mapper, "alvaro", newDateValue(), "meeting");
        saveItem(mapper, "alvaro", newDateValue(), "headline");
        saveItem(mapper, "alvaro", newDateValue(), "headline");

        saveItem(mapper, "bob", newDateValue(), "meeting");
        saveItem(mapper, "bob", newDateValue(), "meeting", "headline");
        saveItem(mapper, "bob", newDateValue(), "headline");

        // Retrieve the item.
        Komm itemRetrieved = mapper.load(Komm.class, hashKeyAlvaro1, dateValue);
        System.out.println("Item retrieved:");
        System.out.println(itemRetrieved);
    }

    private static String newDateValue() {
        return "" + Math.abs(new Random().nextLong());
    }

    @NotNull
    private static String saveItem(DynamoDBMapper mapper, String hashKey, String date, String... otherTags) {
        final String tags = Stream.of(otherTags).collect(Collectors.joining("-"));
        Komm item = new Komm(hashKey, "heheh", tags, date);
        mapper.save(item);
        return hashKey;
    }

    //    ➜  intcom git:(master) ✗ aws dynamodb scan --endpoint http://localhost:8000 --table-name Komms
}
