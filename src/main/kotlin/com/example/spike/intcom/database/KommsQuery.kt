package com.example.spike.intcom.database

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import java.io.IOException
import java.util.*

object KommsQuery {

    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val attributeNames = HashMap<String, String>()
        attributeNames["#v1"] = "tags"
        attributeNames["#v2"] = "tags"
        val attributeValues = HashMap<String, AttributeValue>()
        attributeValues[":value1"] = AttributeValue("meeting")
        attributeValues[":value2"] = AttributeValue("headline")
        queryByTagsFilteringOr("bob", attributeNames, attributeValues)
    }

    private fun queryByTagsFilteringOr(username: String, attributeNames: Map<String, String>, values: Map<String, AttributeValue>) {

        val dynamoDB = LocalClient.client
        val mapper = DynamoDBMapper(dynamoDB)

        println("Matching items:")
        val komm = Komm(username, "", "", "")

        val matchingItems = mapper.query(Komm::class.java,
                DynamoDBQueryExpression<Komm>().withHashKeyValues(komm).withExpressionAttributeNames(attributeNames).withExpressionAttributeValues(values)
                        .withFilterExpression("contains(#v1, :value1) or contains(#v2, :value2)"))
        matchingItems.parallelStream().forEach(::println)
    }
}
