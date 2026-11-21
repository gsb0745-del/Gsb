package com.example

import com.example.data.CalendarCellItem
import com.example.data.FestivalFilter
import com.example.data.PanchangRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class PanchangRepositoryTest {

  @Test
  fun `all twelve months of 2027 are loaded`() {
    assertEquals(12, PanchangRepository.allMonths.size)
    PanchangRepository.allMonths.forEachIndexed { index, month ->
      assertEquals(index, month.monthIndex)
      assertEquals(12, month.rashifal.size)
      assertTrue(month.festivals.isNotEmpty())
    }
  }

  @Test
  fun `january 1 2027 is friday with correct vikram samvat and hijri`() {
    val jan1 = PanchangRepository.getDayPanchang(0, 1)
    assertEquals(5, jan1.weekdayIdx)
    assertEquals("शुक्रवार", jan1.weekdayName)
    assertEquals("२०८३", jan1.vsYear)
    assertEquals("१४४८", jan1.hijriYearNum)
    assertEquals(22, jan1.hijriDay)
    assertEquals("रजब", jan1.hijriMonth)
  }

  @Test
  fun `vikram samvat 2084 starts on april 7 2027`() {
    val apr6 = PanchangRepository.getDayPanchang(3, 6)
    val apr7 = PanchangRepository.getDayPanchang(3, 7)
    assertEquals("२०८३", apr6.vsYear)
    assertEquals("२०८४", apr7.vsYear)
    assertTrue(apr7.isGovtHoliday)
  }

  @Test
  fun `hijri 1449 begins on july 6 2027`() {
    val jul5 = PanchangRepository.getDayPanchang(6, 5)
    val jul6 = PanchangRepository.getDayPanchang(6, 6)
    assertEquals("१४४८", jul5.hijriYearNum)
    assertEquals("१४४९", jul6.hijriYearNum)
  }

  @Test
  fun `month grid cells are multiples of seven and contain exact month days`() {
    for (m in 0..11) {
      val cells = PanchangRepository.getMonthGridCells(m)
      assertEquals(0, cells.size % 7)
      val days = cells.filterIsInstance<CalendarCellItem.Day>()
      assertEquals(PanchangRepository.getDaysInMonth(m), days.size)
      assertEquals(1, days.first().panchang.dayNum)
    }
  }

  @Test
  fun `search finds diwali in october 2027`() {
    val results = PanchangRepository.getAllYearFestivals(FestivalFilter.ALL, "Diwali")
    assertTrue(results.any { it.monthIndex == 9 && it.festival.day == 29 })
  }
}
