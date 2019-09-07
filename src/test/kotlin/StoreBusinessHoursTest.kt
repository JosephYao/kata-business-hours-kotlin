import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.DayOfWeek
import java.time.LocalDateTime

/*
 * Copyright (c) 2019 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

class StoreBusinessHoursTest {
    private lateinit var storeBusinessHours: StoreBusinessHours

    @Before
    fun setup() {
        storeBusinessHours = StoreBusinessHours()
    }

    @Test
    fun testClose_MoreThanOneHoursBeforeOpen() {
        givenOneBusinessHours()

        shouldBe("Close", LocalDateTime.of(2019, 9, 2, 8, 59))
    }

    @Test
    fun testOpenSoon_OneHourBeforeOpen() {
        givenOneBusinessHours()

        shouldBe("Open Soon", LocalDateTime.of(2019, 9, 2, 9, 0))
    }

    @Test
    fun testOpen_OnOpen() {
        givenOneBusinessHours()

        shouldBe("Open", LocalDateTime.of(2019, 9, 2, 10, 0))
    }

    @Test
    fun testCloseSoon_OneHourBeforeClose() {
        givenOneBusinessHours()

        shouldBe("Close Soon", LocalDateTime.of(2019, 9, 2, 11, 0))
    }

    @Test
    fun testClose_OnClose() {
        givenOneBusinessHours()

        shouldBe("Close", LocalDateTime.of(2019, 9, 2, 12, 0))
    }

    @Test
    fun testOpenSoon_OneHoursBeforeSecondOpen_TwoBusinessHours() {
        givenTwoBusinessHours()

        shouldBe("Open Soon", LocalDateTime.of(2019, 9, 2, 13, 0))
    }

    private fun givenOneBusinessHours() {
        storeBusinessHours.setOpeningHours(
            listOf(
                OpeningHours(
                    LiteralHour(DayOfWeek.MONDAY, 10, 0),
                    LiteralHour(DayOfWeek.MONDAY, 12, 0)
                )
            )
        )
    }

    private fun givenTwoBusinessHours() {
        storeBusinessHours.setOpeningHours(
            listOf(
                OpeningHours(LiteralHour(DayOfWeek.MONDAY, 10, 0), LiteralHour(DayOfWeek.MONDAY, 12, 0)),
                OpeningHours(LiteralHour(DayOfWeek.MONDAY, 14, 0), LiteralHour(DayOfWeek.MONDAY, 16, 0))
            )
        )
    }

    private fun shouldBe(expectedResult: String, localDateTime: LocalDateTime) {
        assertEquals(expectedResult, storeBusinessHours.query(localDateTime))
    }
}
