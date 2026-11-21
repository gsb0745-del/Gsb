package com.example.data

import java.util.Calendar
import kotlin.math.ceil
import kotlin.math.floor

object PanchangRepository {

  val allMonths: List<MonthCalendarData> = MONTHS_2027_FIRST_HALF + MONTHS_2027_SECOND_HALF

  val weekdaysHi = listOf("रविवार", "सोमवार", "मंगलवार", "बुधवार", "गुरुवार", "शुक्रवार", "शनिवार")
  val weekdaysShortHi = listOf("रवि", "सोम", "मंगल", "बुध", "गुरु", "शुक्र", "शनि")
  val weekdaysEng = listOf("SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT")

  private val nakshatras = listOf(
    "अश्विनी", "भरणी", "कृत्तिका", "रोहिणी", "मृगशिरा", "आर्द्रा", "पुनर्वसु",
    "पुष्य", "आश्लेषा", "मघा", "पूर्वाफाल्गुनी", "उत्तराफाल्गुनी", "हस्त", "चित्रा",
    "स्वाती", "विशाखा", "अनुराधा", "ज्येष्ठा", "मूल", "पूर्वाषाढ़ा", "उत्तराषाढ़ा",
    "श्रवण", "धनिष्ठा", "शतभिषा", "पूर्वाभाद्रपद", "उत्तराभाद्रपद", "रेवती",
  )

  private val yogas = listOf(
    "विष्कम्भ", "प्रीति", "आयुष्मान", "सौभाग्य", "शोभन", "अतिगण्ड", "सुकर्मा",
    "धृति", "शूल", "गण्ड", "वृद्धि", "ध्रुव", "व्याघात", "हर्षण", "वज्र",
    "सिद्धि", "व्यतीपात", "वरीयान", "परिघ", "शिव", "सिद्ध", "साध्य", "शुभ",
    "शुक्ल", "ब्रह्म", "ऐन्द्र", "वैधृति",
  )

  private val rahukaalByDay = listOf(
    "सायं 04:30 से 06:00",
    "प्रातः 07:30 से 09:00",
    "अपराह्न 03:00 से 04:30",
    "दोपहर 12:00 से 01:30",
    "दोपहर 01:30 से 03:00",
    "प्रातः 10:30 से 12:00",
    "प्रातः 09:00 से 10:30",
  )

  private val tithiCycle = listOf(
    TithiInfo("शुक्ल", "शु. १", "प्रतिपदा", ""),
    TithiInfo("शुक्ल", "शु. २", "द्वितीया", ""),
    TithiInfo("शुक्ल", "शु. ३", "तृतीया", ""),
    TithiInfo("शुक्ल", "शु. ४", "चतुर्थी", "विनायक चतुर्थी"),
    TithiInfo("शुक्ल", "शु. ५", "पंचमी", ""),
    TithiInfo("शुक्ल", "शु. ६", "षष्ठी", ""),
    TithiInfo("शुक्ल", "शु. ७", "सप्तमी", ""),
    TithiInfo("शुक्ल", "शु. ८", "अष्टमी", "दुर्गाष्टमी"),
    TithiInfo("शुक्ल", "शु. ९", "नवमी", ""),
    TithiInfo("शुक्ल", "शु. १०", "दशमी", ""),
    TithiInfo("शुक्ल", "शु. ११", "एकादशी", "एकादशी व्रत"),
    TithiInfo("शुक्ल", "शु. १२", "द्वादशी", ""),
    TithiInfo("शुक्ल", "शु. १३", "त्रयोदशी", "प्रदोष व्रत"),
    TithiInfo("शुक्ल", "शु. १४", "चतुर्दशी", ""),
    TithiInfo("शुक्ल", "पूर्णिमा", "पूर्णिमा", "पूर्णिमा व्रत"),
    TithiInfo("कृष्ण", "कृ. १", "प्रतिपदा", ""),
    TithiInfo("कृष्ण", "कृ. २", "द्वितीया", ""),
    TithiInfo("कृष्ण", "कृ. ३", "तृतीया", ""),
    TithiInfo("कृष्ण", "कृ. ४", "चतुर्थी", "संकष्टी चतुर्थी"),
    TithiInfo("कृष्ण", "कृ. ५", "पंचमी", ""),
    TithiInfo("कृष्ण", "कृ. ६", "षष्ठी", ""),
    TithiInfo("कृष्ण", "कृ. ७", "सप्तमी", ""),
    TithiInfo("कृष्ण", "कृ. ८", "अष्टमी", "कालाष्टमी"),
    TithiInfo("कृष्ण", "कृ. ९", "नवमी", ""),
    TithiInfo("कृष्ण", "कृ. १०", "दशमी", ""),
    TithiInfo("कृष्ण", "कृ. ११", "एकादशी", "एकादशी व्रत"),
    TithiInfo("कृष्ण", "कृ. १२", "द्वादशी", ""),
    TithiInfo("कृष्ण", "कृ. १३", "त्रयोदशी", "प्रदोष व्रत"),
    TithiInfo("कृष्ण", "कृ. १४", "चतुर्दशी", "मासिक शिवरात्रि"),
    TithiInfo("कृष्ण", "अमावस्या", "अमावस्या", "अमावस्या"),
  )

  fun toDevanagariDigits(number: Int): String = toDevanagariDigits(number.toString())

  fun toDevanagariDigits(value: String): String {
    val devDigits = charArrayOf('०', '१', '२', '३', '४', '५', '६', '७', '८', '९')
    return buildString(value.length) {
      for (ch in value) {
        if (ch in '0'..'9') {
          append(devDigits[ch - '0'])
        } else {
          append(ch)
        }
      }
    }
  }

  fun getMonthData(monthIdx: Int): MonthCalendarData =
    allMonths[monthIdx.coerceIn(0, 11)]

  fun getDaysInMonth(monthIdx: Int): Int {
    val cal = Calendar.getInstance()
    cal.set(2027, monthIdx, 1)
    return cal.getActualMaximum(Calendar.DAY_OF_MONTH)
  }

  fun getFirstWeekdayIndex(monthIdx: Int): Int {
    val cal = Calendar.getInstance()
    cal.set(2027, monthIdx, 1)
    return cal.get(Calendar.DAY_OF_WEEK) - 1 // 0 = Sunday .. 6 = Saturday
  }

  fun getDayPanchang(monthIdx: Int, dayNum: Int): DayPanchang {
    val mData = getMonthData(monthIdx)
    val cal = Calendar.getInstance()
    cal.set(2027, monthIdx, dayNum, 0, 0, 0)
    cal.set(Calendar.MILLISECOND, 0)
    val weekdayIdx = cal.get(Calendar.DAY_OF_WEEK) - 1

    val tithiOffset = floor((dayNum - 1) * 1.015).toInt()
    val tithiIdx = (mData.startTithiIndex + tithiOffset) % 30
    val tithiInfo = tithiCycle[tithiIdx]
    val lunarMasa = if (dayNum <= mData.monthSwitchDay) mData.lunarMonth1 else mData.lunarMonth2

    val vsYear = if (monthIdx > 3 || (monthIdx == 3 && dayNum >= 7)) "२०८४" else "२०८३"

    var hijriDay = mData.hijriStartDay + (dayNum - 1)
    var hijriMonth = mData.hijriM1
    if (hijriDay > mData.hijriDaysInM1) {
      hijriDay -= mData.hijriDaysInM1
      hijriMonth = mData.hijriM2
    }

    val hijriYearNum = if (monthIdx > 6 || (monthIdx == 6 && dayNum >= 6)) "१४४९" else "१४४८"

    val dayOfYear = cal.get(Calendar.DAY_OF_YEAR) - 1
    val nakshatra = nakshatras[(dayOfYear + 8) % nakshatras.size]
    val yoga = yogas[(dayOfYear + 14) % yogas.size]

    val dayFestivals = mData.festivals.filter { it.day == dayNum }
    val isGovtHoliday = dayFestivals.any { it.isGovt }

    return DayPanchang(
      dayNum = dayNum,
      monthIdx = monthIdx,
      monthNameHi = mData.hindiName,
      weekdayIdx = weekdayIdx,
      weekdayName = weekdaysHi[weekdayIdx],
      tithiIdx = tithiIdx,
      tithiInfo = tithiInfo,
      lunarMasa = lunarMasa,
      vsYear = vsYear,
      hijriDay = hijriDay,
      hijriMonth = hijriMonth,
      hijriYearNum = hijriYearNum,
      nakshatra = nakshatra,
      yoga = yoga,
      rahukaal = rahukaalByDay[weekdayIdx],
      sunrise = mData.sunrise,
      sunset = mData.sunset,
      dayFestivals = dayFestivals,
      isGovtHoliday = isGovtHoliday,
    )
  }

  fun getMonthGridCells(monthIdx: Int): List<CalendarCellItem> {
    val firstDayOfWeek = getFirstWeekdayIndex(monthIdx)
    val totalDays = getDaysInMonth(monthIdx)
    val totalCells = ceil((firstDayOfWeek + totalDays) / 7.0).toInt() * 7

    return List(totalCells) { cellIdx ->
      val dayNum = cellIdx - firstDayOfWeek + 1
      if (dayNum in 1..totalDays) {
        CalendarCellItem.Day(getDayPanchang(monthIdx, dayNum))
      } else {
        CalendarCellItem.Empty
      }
    }
  }

  fun stepDay(current: DayPanchang, deltaDays: Int): DayPanchang {
    val cal = Calendar.getInstance()
    cal.set(2027, current.monthIdx, current.dayNum)
    cal.add(Calendar.DAY_OF_MONTH, deltaDays)
    val targetYear = cal.get(Calendar.YEAR)
    return if (targetYear < 2027) {
      getDayPanchang(0, 1)
    } else if (targetYear > 2027) {
      getDayPanchang(11, 31)
    } else {
      getDayPanchang(cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH))
    }
  }

  fun filterFestivals(festivals: List<Festival>, filter: FestivalFilter): List<Festival> =
    festivals.filter { f ->
      when (filter) {
        FestivalFilter.ALL -> true
        FestivalFilter.HINDU -> f.type == FestivalType.HINDU
        FestivalFilter.MUSLIM -> f.type == FestivalType.MUSLIM
        FestivalFilter.OTHER -> f.type == FestivalType.OTHER
        FestivalFilter.GOVT -> f.isGovt
      }
    }

  fun getAllYearFestivals(filter: FestivalFilter, query: String): List<FestivalWithMonth> {
    val q = query.trim()
    return allMonths.flatMap { month ->
      filterFestivals(month.festivals, filter)
        .filter { f ->
          q.isEmpty() ||
            f.title.contains(q, ignoreCase = true) ||
            month.hindiName.contains(q, ignoreCase = true) ||
            month.englishName.contains(q, ignoreCase = true)
        }
        .map { f ->
          val cal = Calendar.getInstance()
          cal.set(2027, month.monthIndex, f.day)
          val weekdayIdx = cal.get(Calendar.DAY_OF_WEEK) - 1
          FestivalWithMonth(
            monthIndex = month.monthIndex,
            monthNameHi = month.hindiName,
            weekdayNameHi = weekdaysHi[weekdayIdx],
            festival = f,
          )
        }
    }
  }
}
