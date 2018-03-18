package com.example.spike.intcom.database.test

import com.example.spike.intcom.database.KommsPumper
import com.example.spike.intcom.database.KommsQuery
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.util.*

class FullCycleTest {

    @Test
    fun `filter by meeting`() {
        val pumper = KommsPumper
        val username = random("bob-test")
        val queryBob = { x: List<String> -> KommsQuery.queryByTagsFilteringOr(username, x) }

        pumper.save(username, arrayOf("meeting"))
        pumper.save(username, arrayOf("policy"))
        pumper.save(username, arrayOf("policy", "meeting"))
        pumper.save(username, arrayOf("headline", "meeting"))
        pumper.save(username, arrayOf("headline"))

        assertThat(queryBob(listOf("meeting"))).hasSize(3)
        assertThat(queryBob(listOf("policy"))).hasSize(2)
        assertThat(queryBob(listOf("headline"))).hasSize(2)
    }

    private fun random(username: String): String {
        return "$username-${Random().nextLong()}"
    }

}
