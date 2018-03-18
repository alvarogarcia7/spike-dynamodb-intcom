package com.example.spike.intcom.database

import com.amazonaws.services.dynamodbv2.document.DynamoDB
import com.amazonaws.services.dynamodbv2.model.*

object CreateAllTables {

    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {

        val dynamoDB = DynamoDB(LocalClient.client)

        val tableName = "Komms"

        try {
            println("Attempting to create table $tableName ; please wait...")
            val globalSecondaryIndex = GlobalSecondaryIndex()
            globalSecondaryIndex.indexName = "tags-index"
            globalSecondaryIndex.setKeySchema(listOf(hash("tags")))
            globalSecondaryIndex.projection = include(listOf("tag"))
            val provisionedThroughput = ProvisionedThroughput(1L, 1L)
            globalSecondaryIndex.provisionedThroughput = provisionedThroughput
            val keys = listOf(
                    hash("author"),
                    range("date"))
            val attributes = listOf(
                    string("author"),
                    string("date"),
                    string("tags"))

            val createTableRequest = CreateTableRequest()
            createTableRequest.withKeySchema(keys)
                    .withTableName(tableName)
                    .withAttributeDefinitions(attributes)
                    .withGlobalSecondaryIndexes(listOf(globalSecondaryIndex))
                    .withProvisionedThroughput(provisionedThroughput)
            ////
            val table = dynamoDB.createTable(createTableRequest)
            table.waitForActive()
            println("Success.  Table status: " + table.description.tableStatus)

        } catch (e: Exception) {
            System.err.println("Unable to create table: ")
            System.err.println(e.message)
        }

    }

    private fun range(s: String) = KeySchemaElement(s, KeyType.RANGE)

    private fun include(listOf: List<String>): Projection {
        val projection = Projection()
        projection.setNonKeyAttributes(listOf)
        projection.setProjectionType(ProjectionType.INCLUDE)
        return projection
    }

    private fun string(name: String) = AttributeDefinition(name, ScalarAttributeType.S)

    private fun hash(name: String) = KeySchemaElement(name, KeyType.HASH)
}
