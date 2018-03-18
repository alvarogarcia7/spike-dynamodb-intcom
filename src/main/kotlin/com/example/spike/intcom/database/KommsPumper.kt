package com.example.spike.intcom.database

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import java.io.IOException
import java.util.*

object KommsPumper {

    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        workWithKomms()
    }
        val dynamoDB = LocalClient.client
        val mapper = DynamoDBMapper(dynamoDB)

    private fun workWithKomms() {


        val dateValue = newDateValue()
        val hashKeyAlvaro1 = saveItem(mapper, "alvaro", dateValue, "meeting")
        save("alvaro", arrayOf("meeting"))
        saveItem(mapper, "alvaro", newDateValue(), "meeting")
        saveItem(mapper, "alvaro", newDateValue(), "headline")
        saveItem(mapper, "alvaro", newDateValue(), "headline")

        saveItem(mapper, "bob", newDateValue(), "meeting")
        saveItem(mapper, "bob", newDateValue(), "meeting", "headline")
        saveItem(mapper, "bob", newDateValue(), "headline")

        // Retrieve the item.
        val itemRetrieved = mapper.load(Komm::class.java, hashKeyAlvaro1, dateValue)
        println("Item retrieved:")
        println(itemRetrieved)
    }

    fun save(hashKey: String, otherTags: Array<String>) {
        saveItem(mapper, hashKey, newDateValue(), *otherTags)
    }

    private fun newDateValue(): String {
        return "" + Math.abs(Random().nextLong())
    }

    private fun saveItem(mapper: DynamoDBMapper, hashKey: String, date: String, vararg otherTags: String): String {
        val tags = otherTags.joinToString("-")
        val item = Komm(hashKey, "heheh", tags, date)
        mapper.save(item)
        return hashKey
    }

    //    ➜  intcom git:(master) ✗ aws dynamodb scan --endpoint http://localhost:8000 --table-name Komms
}
