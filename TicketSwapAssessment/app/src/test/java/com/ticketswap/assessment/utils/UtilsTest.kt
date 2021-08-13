package com.ticketswap.assessment.utils

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class UtilsTest {

    @Test
    fun test_count_null() {
        assertThat(prettyCount(null)).isEqualTo("0")
    }

    @Test
    fun test_count_under_k() {
        assertThat(prettyCount(10)).isEqualTo("10")
    }

    @Test
    fun test_count_1500() {
        assertThat(prettyCount(1500)).isEqualTo("1.5k")
    }

    @Test
    fun test_count_15000() {
        assertThat(prettyCount(15000)).isEqualTo("15k")
    }

    @Test
    fun test_count_13000000() {
        assertThat(prettyCount(1500000)).isEqualTo("1.5M")
    }
}