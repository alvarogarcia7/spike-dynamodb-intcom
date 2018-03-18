package com.example.spike.intcom.database

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import java.io.IOException

object KommsQuery {

    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val tagsValues = listOf("meeting")
        queryByTagsFilteringOr("bob", tagsValues)
    }

    fun queryByTagsFilteringOr(username: String, tagsValues: List<String>): PaginatedQueryList<Komm> {

        val dynamoDB = LocalClient.client
        val mapper = DynamoDBMapper(dynamoDB)

        println("Matching items:")
        val komm = Komm(username, "", "", "")

        val filterExpression = tagsValues.mapIndexed { i, _ ->
            "contains(#v$i, :value$i)"
        }.joinToString(" or ")
        val attributeNames: Map<String, String> = tagsValues.mapIndexed { i, _ ->
            ("#v$i" to "tags")
        }.toMap()
        val values = tagsValues.mapIndexed { i, tagValue ->
            (":value$i" to AttributeValue(tagValue))
        }.toMap()
        val matchingItems = mapper.query(Komm::class.java,
                DynamoDBQueryExpression<Komm>().withHashKeyValues(komm).withExpressionAttributeNames(attributeNames).withExpressionAttributeValues(values)
                        .withFilterExpression(filterExpression))
        matchingItems.parallelStream().forEach(::println)
        return matchingItems
    }
}
