package com.example.spike.intcom.database;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "Komms")
public class Komm {
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
