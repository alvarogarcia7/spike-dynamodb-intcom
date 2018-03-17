package com.example.spike.intcom.database

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable

@DynamoDBTable(tableName = "Komms")
data class Komm(
        @get:DynamoDBHashKey(attributeName = "author")
        var author: String? = "",
        @get:DynamoDBAttribute(attributeName = "body")
        var body: String? = "",
        @get:DynamoDBAttribute(attributeName = "tags")
        var tags: String? = "",
        @get:DynamoDBRangeKey(attributeName = "date")
        @get:DynamoDBAttribute(attributeName = "date")
        var date: String? = "")
