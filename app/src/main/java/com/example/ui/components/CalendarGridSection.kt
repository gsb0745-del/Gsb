package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.CalendarCellItem
import com.example.data.DayPanchang
import com.example.data.Festival
import com.example.data.FestivalFilter
import com.example.data.FestivalType
import com.example.data.MonthCalendarData
import com.example.data.PanchangRepository
import com.example.ui.theme.AmberBorder
import com.example.ui.theme.AmberSoft
import com.example.ui.theme.BlueBg
import com.example.ui.theme.BlueBorder
import com.example.ui.theme.BlueText
import com.example.ui.theme.CrimsonBright
import com.example.ui.theme.CrimsonDarkest
import com.example.ui.theme.CrimsonDeep
import com.example.ui.theme.CrimsonPrimary
import com.example.ui.theme.EmeraldBg
import com.example.ui.theme.EmeraldBorder
import com.example.ui.theme.EmeraldHijri
import com.example.ui.theme.EnglishNumericFontFamily
import com.example.ui.theme.GoldLight
import com.example.ui.theme.HolidayBgEnd
import com.example.ui.theme.HolidayBgStart
import com.example.ui.theme.InkDark
import com.example.ui.theme.InkMuted
import com.example.ui.theme.MarigoldBright
import com.example.ui.theme.OrangeBg
import com.example.ui.theme.OrangeBorder
import com.example.ui.theme.OrangeText
import com.example.ui.theme.PaperCellBg
import com.example.ui.theme.RoyalBlueSat
import com.example.ui.theme.SaffronGold
import com.example.ui.theme.SundayBg
import com.example.ui.theme.WeekdayHeaderBrown

@Composable
fun CalendarGridCard(
  monthData: MonthCalendarData,
  activeFilter: FestivalFilter,
  isDarkTheme: Boolean,
  isExpandedWidth: Boolean,
  onDayClick: (DayPanchang) -> Unit,
  modifier: Modifier = Modifier,
) {
  val cells = PanchangRepository.getMonthGridCells(monthData.monthIndex)
  val weeks = cells.chunked(7)

  val gridLineColor = if (isDarkTheme) {
    SaffronGold.copy(alpha = 0.08f)
  } else {
    SaffronGold.copy(alpha = 0.07f)
  }

  Surface(
    modifier = modifier.fillMaxWidth(),
    shape = RoundedCornerShape(16.dp),
    color = MaterialTheme.colorScheme.surface,
    border = BorderStroke(2.dp, CrimsonPrimary),
    shadowElevation = 4.dp,
  ) {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .padding(3.dp)
        .border(1.5.dp, SaffronGold.copy(alpha = 0.7f), RoundedCornerShape(13.dp))
        .drawBehind {
          // Subtle traditional Kaldarshak graph-paper texture
          val step = 22.dp.toPx()
          var x = step
          while (x < size.width) {
            drawLine(gridLineColor, Offset(x, 0f), Offset(x, size.height), strokeWidth = 1f)
            x += step
          }
          var y = step
          while (y < size.height) {
            drawLine(gridLineColor, Offset(0f, y), Offset(size.width, y), strokeWidth = 1f)
            y += step
          }
        },
    ) {
      // Diagonal GS Watermark behind the grid
      Box(
        modifier = Modifier
          .matchParentSize()
          .graphicsLayer {
            rotationZ = -28f
            alpha = if (isDarkTheme) 0.06f else 0.055f
          },
        contentAlignment = Alignment.Center,
      ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Text(
            text = "GS",
            fontFamily = EnglishNumericFontFamily,
            fontWeight = FontWeight.ExtraBold,
            fontSize = if (isExpandedWidth) 128.sp else 96.sp,
            color = CrimsonPrimary,
          )
          Text(
            text = "GS CALENDAR 2027",
            fontFamily = EnglishNumericFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = if (isExpandedWidth) 22.sp else 16.sp,
            letterSpacing = 4.sp,
            color = CrimsonPrimary,
          )
          Text(
            text = "॥ श्री गणेशाय नमः • जी.एस. कालदर्शक पंचांग ॥",
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            color = SaffronGold,
          )
        }
      }

      Column(modifier = Modifier.fillMaxWidth()) {
        // 7-Day Weekday Header
        WeekdayHeaderRow()

        // Weeks
        weeks.forEachIndexed { rowIdx, weekCells ->
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .height(IntrinsicSize.Min),
          ) {
            weekCells.forEachIndexed { colIdx, cell ->
              val cellMinHeight = if (isExpandedWidth) 96.dp else 78.dp
              when (cell) {
                is CalendarCellItem.Empty -> {
                  Box(
                    modifier = Modifier
                      .weight(1f)
                      .fillMaxHeight()
                      .height(cellMinHeight)
                      .background(
                        if (isDarkTheme) Color.Black.copy(alpha = 0.2f)
                        else SaffronGold.copy(alpha = 0.06f),
                      )
                      .border(0.5.dp, SaffronGold.copy(alpha = 0.25f)),
                    contentAlignment = Alignment.Center,
                  ) {
                    Text(
                      text = "卐",
                      color = SaffronGold.copy(alpha = 0.25f),
                      fontSize = 16.sp,
                    )
                  }
                }

                is CalendarCellItem.Day -> {
                  CalendarDayCell(
                    panchang = cell.panchang,
                    activeFilter = activeFilter,
                    isDarkTheme = isDarkTheme,
                    isExpandedWidth = isExpandedWidth,
                    onClick = { onDayClick(cell.panchang) },
                    modifier = Modifier
                      .weight(1f)
                      .fillMaxHeight()
                      .testTag("day_cell_${cell.panchang.dayNum}"),
                  )
                }
              }
            }
          }
        }
      }
    }
  }
}

@Composable
private fun WeekdayHeaderRow() {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
      .border(1.dp, CrimsonPrimary, RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
  ) {
    for (idx in 0 until 7) {
      val bgColor = when (idx) {
        0 -> CrimsonPrimary
        6 -> RoyalBlueSat
        else -> WeekdayHeaderBrown
      }
      Column(
        modifier = Modifier
          .weight(1f)
          .background(bgColor)
          .padding(vertical = 7.dp, horizontal = 2.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        Text(
          text = PanchangRepository.weekdaysShortHi[idx],
          color = Color.White,
          fontWeight = FontWeight.ExtraBold,
          fontSize = 12.sp,
          maxLines = 1,
        )
        Text(
          text = PanchangRepository.weekdaysEng[idx],
          fontFamily = EnglishNumericFontFamily,
          color = AmberSoft,
          fontWeight = FontWeight.SemiBold,
          fontSize = 9.sp,
          maxLines = 1,
        )
      }
    }
  }
}

@Composable
private fun CalendarDayCell(
  panchang: DayPanchang,
  activeFilter: FestivalFilter,
  isDarkTheme: Boolean,
  isExpandedWidth: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val isSunday = panchang.weekdayIdx == 0
  val isRedDay = isSunday || panchang.isGovtHoliday
  val visibleFestivals = PanchangRepository.filterFestivals(panchang.dayFestivals, activeFilter)

  val cellModifier = modifier
    .border(0.5.dp, SaffronGold.copy(alpha = 0.35f))
    .let { base ->
      when {
        panchang.isGovtHoliday && !isDarkTheme -> base.background(
          brush = Brush.linearGradient(
            colors = listOf(HolidayBgStart.copy(alpha = 0.72f), HolidayBgEnd.copy(alpha = 0.72f)),
          ),
        )
        panchang.isGovtHoliday && isDarkTheme -> base.background(CrimsonDeep.copy(alpha = 0.45f))
        isSunday && !isDarkTheme -> base.background(SundayBg.copy(alpha = 0.58f))
        isSunday && isDarkTheme -> base.background(CrimsonPrimary.copy(alpha = 0.24f))
        !isDarkTheme -> base.background(PaperCellBg.copy(alpha = 0.78f))
        else -> base.background(MaterialTheme.colorScheme.surface.copy(alpha = 0.85f))
      }
    }
    .clickable(onClick = onClick)
    .semantics {
      contentDescription = "${panchang.dayNum} ${panchang.monthNameHi} ${panchang.weekdayName}"
    }
    .padding(horizontal = 3.dp, vertical = 4.dp)

  Column(
    modifier = cellModifier.height(if (isExpandedWidth) 98.dp else 80.dp),
    verticalArrangement = Arrangement.SpaceBetween,
  ) {
    // Top Row: Tithi + Moon Phase + Hijri Day
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Text(
        text = panchang.tithiInfo.shortName,
        fontSize = if (isExpandedWidth) 9.5.sp else 8.sp,
        fontWeight = FontWeight.Bold,
        color = if (isDarkTheme) AmberSoft else InkMuted,
        maxLines = 1,
        overflow = TextOverflow.Clip,
      )

      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp),
      ) {
        if (panchang.tithiIdx == 14) {
          Box(
            modifier = Modifier
              .size(7.dp)
              .clip(CircleShape)
              .background(MarigoldBright)
              .border(1.dp, WeekdayHeaderBrown, CircleShape),
          )
        } else if (panchang.tithiIdx == 29) {
          Box(
            modifier = Modifier
              .size(7.dp)
              .clip(CircleShape)
              .background(Color(0xFF1C1917))
              .border(1.dp, Color.Gray, CircleShape),
          )
        }

        Text(
          text = "${PanchangRepository.toDevanagariDigits(panchang.hijriDay)}हि",
          fontSize = if (isExpandedWidth) 9.5.sp else 8.sp,
          fontWeight = FontWeight.Bold,
          color = if (isDarkTheme) Color(0xFF6EE7B7) else EmeraldHijri,
          maxLines = 1,
        )
      }
    }

    // Center: Gregorian Date Number + Govt Holiday pill
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.Bottom,
    ) {
      Text(
        text = panchang.dayNum.toString(),
        fontFamily = EnglishNumericFontFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = if (isExpandedWidth) 24.sp else 19.sp,
        lineHeight = if (isExpandedWidth) 26.sp else 20.sp,
        color = when {
          isRedDay && isDarkTheme -> Color(0xFFFCA5A5)
          isRedDay -> CrimsonBright
          isDarkTheme -> Color.White
          else -> InkDark
        },
      )

      if (panchang.isGovtHoliday && isExpandedWidth) {
        Surface(
          shape = RoundedCornerShape(4.dp),
          color = CrimsonBright,
        ) {
          Text(
            text = stringResource(R.string.govt_holiday_badge),
            color = Color.White,
            fontSize = 8.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 3.dp, vertical = 1.dp),
          )
        }
      }
    }

    // Bottom: Festival label or Special Tithi or Nakshatra
    if (visibleFestivals.isNotEmpty()) {
      val firstFest = visibleFestivals.first()
      val (bgColor, textColor) = festivalPillColors(firstFest, isDarkTheme)
      Surface(
        shape = RoundedCornerShape(4.dp),
        color = bgColor,
        modifier = Modifier.fillMaxWidth(),
      ) {
        Text(
          text = firstFest.title,
          color = textColor,
          fontSize = if (isExpandedWidth) 9.sp else 7.5.sp,
          lineHeight = if (isExpandedWidth) 11.sp else 9.sp,
          fontWeight = FontWeight.Bold,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis,
          modifier = Modifier.padding(horizontal = 3.dp, vertical = 1.dp),
        )
      }
    } else if (panchang.tithiInfo.special.isNotEmpty()) {
      Text(
        text = "• ${panchang.tithiInfo.special}",
        fontSize = if (isExpandedWidth) 9.sp else 7.5.sp,
        fontWeight = FontWeight.SemiBold,
        color = if (isDarkTheme) GoldLight else WeekdayHeaderBrown,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
      )
    } else {
      Text(
        text = panchang.nakshatra,
        fontSize = if (isExpandedWidth) 9.sp else 7.5.sp,
        color = if (isDarkTheme) Color(0xFFA8A29E) else InkMuted.copy(alpha = 0.8f),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
      )
    }
  }
}

fun festivalPillColors(festival: Festival, isDarkTheme: Boolean): Pair<Color, Color> {
  if (festival.isGovt && festival.type == FestivalType.GOVT) {
    return CrimsonBright to Color.White
  }
  return when (festival.type) {
    FestivalType.HINDU -> if (isDarkTheme) Color(0xFF7C2D12) to AmberSoft else OrangeBg to OrangeText
    FestivalType.MUSLIM -> if (isDarkTheme) Color(0xFF064E3B) to Color(0xFFA7F3D0) else EmeraldBg to EmeraldHijri
    FestivalType.OTHER -> if (isDarkTheme) Color(0xFF1E3A8A) to Color(0xFFDBEAFE) else BlueBg to BlueText
    FestivalType.GOVT -> CrimsonBright to Color.White
  }
}

@Composable
fun CalendarLegendStrip(
  isDarkTheme: Boolean,
  modifier: Modifier = Modifier,
) {
  Surface(
    modifier = modifier.fillMaxWidth(),
    shape = RoundedCornerShape(12.dp),
    color = if (isDarkTheme) MaterialTheme.colorScheme.surfaceVariant else AmberSoft.copy(alpha = 0.9f),
    border = BorderStroke(1.dp, AmberBorder.copy(alpha = 0.7f)),
  ) {
    Column(
      modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
      verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
      ) {
        LegendDotItem(CrimsonBright, "रविवार / अवकाश")
        LegendDotItem(MarigoldBright, "पूर्णिमा")
        LegendDotItem(Color(0xFF1C1917), "अमावस्या")
        Text(
          text = "हि. इस्लामी तारीख",
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          color = if (isDarkTheme) Color(0xFF6EE7B7) else EmeraldHijri,
        )
      }
      Text(
        text = "* किसी भी तारीख पर टैप कर विस्तृत दैनिक पंचांग देखें",
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        color = if (isDarkTheme) GoldLight else CrimsonPrimary,
      )
    }
  }
}

@Composable
private fun LegendDotItem(color: Color, label: String) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(4.dp),
  ) {
    Box(
      modifier = Modifier
        .size(10.dp)
        .clip(CircleShape)
        .background(color)
        .border(0.5.dp, Color.Gray.copy(alpha = 0.5f), CircleShape),
    )
    Text(
      text = label,
      fontSize = 11.sp,
      fontWeight = FontWeight.SemiBold,
      color = MaterialTheme.colorScheme.onSurface,
    )
  }
}

@Composable
fun MonthlyFestivalsCard(
  monthData: MonthCalendarData,
  activeFilter: FestivalFilter,
  isDarkTheme: Boolean,
  onFestivalClick: (Festival) -> Unit,
  modifier: Modifier = Modifier,
) {
  val filtered = PanchangRepository.filterFestivals(monthData.festivals, activeFilter)

  Surface(
    modifier = modifier.fillMaxWidth(),
    shape = RoundedCornerShape(16.dp),
    color = MaterialTheme.colorScheme.surface,
    border = BorderStroke(1.5.dp, CrimsonPrimary.copy(alpha = 0.7f)),
    tonalElevation = 2.dp,
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(bottom = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Text(
          text = "🎊 इस माह के व्रत, त्योहार व अवकाश",
          fontWeight = FontWeight.ExtraBold,
          fontSize = 15.sp,
          color = if (isDarkTheme) GoldLight else CrimsonPrimary,
          modifier = Modifier.weight(1f),
        )
        Surface(
          shape = RoundedCornerShape(50),
          color = CrimsonPrimary,
        ) {
          Text(
            text = "${PanchangRepository.toDevanagariDigits(filtered.size)} पर्व",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 11.sp,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp),
          )
        }
      }

      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(2.dp)
          .background(AmberBorder),
      )

      Spacer(modifier = Modifier.height(10.dp))

      if (filtered.isEmpty()) {
        Text(
          text = "इस श्रेणी में इस माह कोई विशेष पर्व नहीं है।",
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          fontWeight = FontWeight.SemiBold,
          fontSize = 13.sp,
          textAlign = TextAlign.Center,
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        )
      } else {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          filtered.forEach { festival ->
            val panchang = PanchangRepository.getDayPanchang(monthData.monthIndex, festival.day)
            FestivalListItemRow(
              day = festival.day,
              weekdayShort = panchang.weekdayName.take(3),
              subtitle = "${panchang.weekdayName} • ${monthData.hindiName} 2027",
              festival = festival,
              isDarkTheme = isDarkTheme,
              onClick = { onFestivalClick(festival) },
            )
          }
        }
      }
    }
  }
}

@Composable
fun FestivalListItemRow(
  day: Int,
  weekdayShort: String,
  subtitle: String,
  festival: Festival,
  isDarkTheme: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val (pillBg, pillText) = festivalPillColors(festival, isDarkTheme)
  val borderColor = when {
    festival.isGovt -> CrimsonBright.copy(alpha = 0.5f)
    festival.type == FestivalType.MUSLIM -> EmeraldBorder
    festival.type == FestivalType.OTHER -> BlueBorder
    else -> AmberBorder.copy(alpha = 0.6f)
  }

  Surface(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(12.dp))
      .clickable(onClick = onClick),
    shape = RoundedCornerShape(12.dp),
    color = if (festival.isGovt) {
      if (isDarkTheme) CrimsonDeep.copy(alpha = 0.35f) else SundayBg.copy(alpha = 0.55f)
    } else {
      if (isDarkTheme) MaterialTheme.colorScheme.surfaceVariant else AmberSoft.copy(alpha = 0.45f)
    },
    border = BorderStroke(1.dp, borderColor),
  ) {
    Row(
      modifier = Modifier.padding(10.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
      Surface(
        shape = RoundedCornerShape(10.dp),
        color = if (festival.isGovt) CrimsonBright else MarigoldBright,
        modifier = Modifier.size(44.dp),
      ) {
        Column(
          modifier = Modifier.fillMaxSize(),
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.Center,
        ) {
          Text(
            text = day.toString(),
            fontFamily = EnglishNumericFontFamily,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 15.sp,
            color = if (festival.isGovt) Color.White else CrimsonDarkest,
            lineHeight = 16.sp,
          )
          Text(
            text = weekdayShort,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            color = if (festival.isGovt) AmberSoft else CrimsonDeep,
          )
        }
      }

      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = festival.title,
          fontWeight = FontWeight.Bold,
          fontSize = 13.sp,
          color = MaterialTheme.colorScheme.onSurface,
        )
        Text(
          text = subtitle,
          fontSize = 11.sp,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
      }

      val tagLabel = when {
        festival.isGovt -> "🏛️ अवकाश"
        festival.type == FestivalType.HINDU -> "🕉️ हिन्दू"
        festival.type == FestivalType.MUSLIM -> "☪️ मुस्लिम"
        festival.type == FestivalType.OTHER -> "✨ सर्वधर्म"
        else -> "🏛️ अवकाश"
      }

      Surface(
        shape = RoundedCornerShape(50),
        color = pillBg,
        border = BorderStroke(
          0.5.dp,
          when (festival.type) {
            FestivalType.HINDU -> OrangeBorder
            FestivalType.MUSLIM -> EmeraldBorder
            FestivalType.OTHER -> BlueBorder
            FestivalType.GOVT -> CrimsonBright
          },
        ),
      ) {
        Text(
          text = tagLabel,
          color = pillText,
          fontWeight = FontWeight.Bold,
          fontSize = 10.sp,
          modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
        )
      }
    }
  }
}
