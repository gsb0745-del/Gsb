package com.example.data

enum class FestivalType {
  HINDU,
  MUSLIM,
  OTHER,
  GOVT,
}

enum class FestivalFilter(val labelHi: String, val emoji: String) {
  ALL("सभी पर्व", "✨"),
  HINDU("हिन्दू", "🕉️"),
  MUSLIM("मुस्लिम", "☪️"),
  OTHER("सिख/ईसाई/जैन", "☸️"),
  GOVT("राजकीय अवकाश", "🏛️"),
}

enum class CalendarDestination(val labelHi: String) {
  CALENDAR("कैलेंडर"),
  JYOTISH("ज्योतिष व मुहूर्त"),
  RASHIFAL("राशिफल"),
  FESTIVALS("सभी पर्व"),
}

data class Festival(
  val day: Int,
  val title: String,
  val type: FestivalType,
  val isGovt: Boolean,
)

data class FestivalWithMonth(
  val monthIndex: Int,
  val monthNameHi: String,
  val weekdayNameHi: String,
  val festival: Festival,
)

data class JyotishInfo(
  val vivah: String,
  val grihaPravesh: String,
  val vahan: String,
  val panchak: String,
  val grahan: String,
  val vishesh: String,
)

data class RashifalItem(
  val rashi: String,
  val icon: String,
  val text: String,
  val lucky: String,
)

data class MonthCalendarData(
  val monthIndex: Int,
  val hindiName: String,
  val englishName: String,
  val hindiMasa: String,
  val vikramSamvat: String,
  val shakaSamvat: String,
  val ritu: String,
  val hijriYear: String,
  val hijriMonths: String,
  val startTithiIndex: Int,
  val lunarMonth1: String,
  val lunarMonth2: String,
  val monthSwitchDay: Int,
  val hijriStartDay: Int,
  val hijriM1: String,
  val hijriM2: String,
  val hijriDaysInM1: Int,
  val sunrise: String,
  val sunset: String,
  val jyotish: JyotishInfo,
  val festivals: List<Festival>,
  val rashifal: List<RashifalItem>,
)

data class TithiInfo(
  val paksha: String,
  val shortName: String,
  val fullName: String,
  val special: String,
)

data class DayPanchang(
  val dayNum: Int,
  val monthIdx: Int,
  val monthNameHi: String,
  val weekdayIdx: Int,
  val weekdayName: String,
  val tithiIdx: Int,
  val tithiInfo: TithiInfo,
  val lunarMasa: String,
  val vsYear: String,
  val hijriDay: Int,
  val hijriMonth: String,
  val hijriYearNum: String,
  val nakshatra: String,
  val yoga: String,
  val rahukaal: String,
  val sunrise: String,
  val sunset: String,
  val dayFestivals: List<Festival>,
  val isGovtHoliday: Boolean,
)

sealed interface CalendarCellItem {
  data object Empty : CalendarCellItem
  data class Day(val panchang: DayPanchang) : CalendarCellItem
}
