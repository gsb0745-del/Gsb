package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.FestivalFilter
import com.example.data.MonthCalendarData
import com.example.data.PanchangRepository
import com.example.ui.theme.AmberBorder
import com.example.ui.theme.AmberSoft
import com.example.ui.theme.CrimsonDarkest
import com.example.ui.theme.CrimsonDeep
import com.example.ui.theme.CrimsonPrimary
import com.example.ui.theme.DisplayFontFamily
import com.example.ui.theme.EmeraldLight
import com.example.ui.theme.EnglishNumericFontFamily
import com.example.ui.theme.GoldLight
import com.example.ui.theme.MarigoldBright
import com.example.ui.theme.SaffronGold

@Composable
fun MonthControlBar(
  selectedMonthIndex: Int,
  isDarkTheme: Boolean,
  onPrevMonth: () -> Unit,
  onNextMonth: () -> Unit,
  onSelectMonth: (Int) -> Unit,
  onToggleTheme: () -> Unit,
  modifier: Modifier = Modifier,
) {
  var dropdownExpanded by remember { mutableStateOf(false) }
  val monthData = PanchangRepository.getMonthData(selectedMonthIndex)

  Surface(
    modifier = modifier.fillMaxWidth(),
    shape = RoundedCornerShape(16.dp),
    color = CrimsonDarkest,
    border = BorderStroke(1.dp, SaffronGold.copy(alpha = 0.6f)),
    tonalElevation = 4.dp,
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp, vertical = 6.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween,
    ) {
      IconButton(
        onClick = onPrevMonth,
        modifier = Modifier
          .size(48.dp)
          .clip(RoundedCornerShape(12.dp))
          .background(CrimsonPrimary)
          .testTag("prev_month_button"),
      ) {
        Icon(
          imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
          contentDescription = "पिछला माह (Previous Month)",
          tint = Color.White,
        )
      }

      Box(
        modifier = Modifier
          .weight(1f)
          .padding(horizontal = 8.dp),
        contentAlignment = Alignment.Center,
      ) {
        OutlinedButton(
          onClick = { dropdownExpanded = true },
          shape = RoundedCornerShape(12.dp),
          colors = ButtonDefaults.outlinedButtonColors(
            containerColor = AmberSoft,
            contentColor = CrimsonDarkest,
          ),
          border = BorderStroke(1.5.dp, MarigoldBright),
          modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .testTag("month_selector_button"),
        ) {
          Text(
            text = "${monthData.hindiName} 2027 (${monthData.englishName})",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
          )
          Spacer(modifier = Modifier.width(4.dp))
          Icon(
            imageVector = Icons.Default.ArrowDropDown,
            contentDescription = "माह चुनें",
          )
        }

        DropdownMenu(
          expanded = dropdownExpanded,
          onDismissRequest = { dropdownExpanded = false },
          modifier = Modifier.background(MaterialTheme.colorScheme.surface),
        ) {
          PanchangRepository.allMonths.forEach { m ->
            val isSelected = m.monthIndex == selectedMonthIndex
            DropdownMenuItem(
              text = {
                Text(
                  text = "${PanchangRepository.toDevanagariDigits((m.monthIndex + 1).toString().padStart(2, '0'))}. ${m.hindiName} 2027 (${m.englishName})",
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                  color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                )
              },
              onClick = {
                dropdownExpanded = false
                onSelectMonth(m.monthIndex)
              },
            )
          }
        }
      }

      IconButton(
        onClick = onNextMonth,
        modifier = Modifier
          .size(48.dp)
          .clip(RoundedCornerShape(12.dp))
          .background(CrimsonPrimary)
          .testTag("next_month_button"),
      ) {
        Icon(
          imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
          contentDescription = "अगला माह (Next Month)",
          tint = Color.White,
        )
      }

      Spacer(modifier = Modifier.width(6.dp))

      IconButton(
        onClick = onToggleTheme,
        modifier = Modifier
          .size(48.dp)
          .clip(RoundedCornerShape(12.dp))
          .background(CrimsonDeep)
          .border(1.dp, SaffronGold.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
          .testTag("theme_toggle_button"),
      ) {
        Icon(
          imageVector = if (isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
          contentDescription = if (isDarkTheme) "Light Parchment Theme" else "Dark Temple Theme",
          tint = GoldLight,
        )
      }
    }
  }
}

@Composable
fun FestivalFilterRow(
  activeFilter: FestivalFilter,
  onFilterSelect: (FestivalFilter) -> Unit,
  modifier: Modifier = Modifier,
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .horizontalScroll(rememberScrollState()),
    horizontalArrangement = Arrangement.spacedBy(8.dp),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    FestivalFilter.entries.forEach { filter ->
      val selected = filter == activeFilter
      FilterChip(
        selected = selected,
        onClick = { onFilterSelect(filter) },
        label = {
          Text(
            text = "${filter.emoji} ${filter.labelHi}",
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
            fontSize = 12.sp,
          )
        },
        colors = FilterChipDefaults.filterChipColors(
          selectedContainerColor = MarigoldBright,
          selectedLabelColor = CrimsonDarkest,
          containerColor = MaterialTheme.colorScheme.surfaceVariant,
          labelColor = MaterialTheme.colorScheme.onSurfaceVariant,
        ),
        border = FilterChipDefaults.filterChipBorder(
          enabled = true,
          selected = selected,
          borderColor = AmberBorder.copy(alpha = 0.5f),
          selectedBorderColor = CrimsonPrimary,
        ),
        modifier = Modifier.testTag("filter_pill_${filter.name}"),
      )
    }
  }
}

@Composable
fun WallCalendarHeaderBanner(
  monthData: MonthCalendarData,
  compactMode: Boolean = false,
  modifier: Modifier = Modifier,
) {
  Column(modifier = modifier.fillMaxWidth()) {
    // Top Hanging Crimson Header
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .background(
          brush = Brush.verticalGradient(
            colors = listOf(CrimsonPrimary, CrimsonDeep),
          ),
        )
        .padding(horizontal = 12.dp, vertical = if (compactMode) 10.dp else 14.dp),
    ) {
      Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        // Decorative Hanging Eyelet
        Box(
          modifier = Modifier
            .size(20.dp)
            .clip(CircleShape)
            .background(Color(0xFF1C1917))
            .border(3.dp, AmberBorder, CircleShape),
          contentAlignment = Alignment.Center,
        ) {
          Box(
            modifier = Modifier
              .size(6.dp)
              .clip(CircleShape)
              .background(AmberSoft),
          )
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Auspicious Tagline Pill
        Surface(
          shape = RoundedCornerShape(50),
          color = MarigoldBright,
          shadowElevation = 2.dp,
        ) {
          Text(
            text = "🪔 ${stringResource(R.string.brand_tagline)} 🪔",
            color = CrimsonDarkest,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 11.sp,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 3.dp),
          )
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Brand Display Title
        Row(
          verticalAlignment = Alignment.Bottom,
          horizontalArrangement = Arrangement.Center,
        ) {
          Text(
            text = "GS Calendar ",
            fontFamily = DisplayFontFamily,
            fontSize = if (compactMode) 28.sp else 36.sp,
            color = GoldLight,
          )
          Text(
            text = "2027",
            fontFamily = EnglishNumericFontFamily,
            fontWeight = FontWeight.ExtraBold,
            fontSize = if (compactMode) 26.sp else 34.sp,
            color = Color.White,
          )
        }

        Text(
          text = stringResource(R.string.brand_subtitle),
          color = AmberSoft,
          fontWeight = FontWeight.SemiBold,
          fontSize = 11.sp,
          textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Vikram Samvat & Hijri Summary Cards
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
          Surface(
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(10.dp),
            color = CrimsonDarkest.copy(alpha = 0.65f),
            border = BorderStroke(1.dp, AmberBorder.copy(alpha = 0.4f)),
          ) {
            Column(modifier = Modifier.padding(8.dp)) {
              Text(
                text = "卐 ${stringResource(R.string.mantra_left)} ॐ",
                color = GoldLight,
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
              )
              Text(
                text = monthData.vikramSamvat,
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 11.sp,
              )
              Text(
                text = monthData.shakaSamvat,
                color = AmberSoft.copy(alpha = 0.85f),
                fontSize = 10.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
              )
            }
          }

          Surface(
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(10.dp),
            color = CrimsonDarkest.copy(alpha = 0.65f),
            border = BorderStroke(1.dp, AmberBorder.copy(alpha = 0.4f)),
          ) {
            Column(
              modifier = Modifier.padding(8.dp),
              horizontalAlignment = Alignment.End,
            ) {
              Text(
                text = "☪️ ${monthData.hijriYear}",
                color = EmeraldLight,
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
              )
              Text(
                text = monthData.hijriMonths,
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 11.sp,
                textAlign = TextAlign.End,
              )
              Text(
                text = "सर्वधर्म पर्व व अवकाश",
                color = AmberSoft.copy(alpha = 0.85f),
                fontSize = 10.sp,
              )
            }
          }
        }
      }
    }

    // Gold Divider
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(3.dp)
        .background(MarigoldBright),
    )

    // Sub-Header Month Banner
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .background(
          brush = Brush.horizontalGradient(
            colors = listOf(MarigoldBright, AmberBorder, MarigoldBright),
          ),
        )
        .padding(horizontal = 12.dp, vertical = 8.dp),
    ) {
      Column(modifier = Modifier.fillMaxWidth()) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween,
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
          ) {
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = CrimsonPrimary,
            ) {
              val monthNumDev = PanchangRepository.toDevanagariDigits(
                (monthData.monthIndex + 1).toString().padStart(2, '0'),
              )
              Text(
                text = "माह $monthNumDev",
                color = AmberSoft,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 12.sp,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
              )
            }
            Text(
              text = "${monthData.hindiName} २०२७",
              color = CrimsonDarkest,
              fontWeight = FontWeight.Black,
              fontSize = 20.sp,
            )
          }

          Text(
            text = "${monthData.englishName} 2027",
            fontFamily = EnglishNumericFontFamily,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 15.sp,
            color = CrimsonPrimary,
          )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          verticalAlignment = Alignment.CenterVertically,
        ) {
          Surface(
            shape = RoundedCornerShape(50),
            color = Color.White.copy(alpha = 0.88f),
            border = BorderStroke(1.dp, CrimsonPrimary.copy(alpha = 0.3f)),
          ) {
            Text(
              text = "${monthData.hindiMasa} (${monthData.vikramSamvat})",
              color = CrimsonDarkest,
              fontWeight = FontWeight.Bold,
              fontSize = 11.sp,
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp),
            )
          }
          Surface(
            shape = RoundedCornerShape(50),
            color = CrimsonDeep,
          ) {
            Text(
              text = "ऋतु: ${monthData.ritu}",
              color = AmberSoft,
              fontWeight = FontWeight.Bold,
              fontSize = 11.sp,
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp),
            )
          }
        }
      }
    }
  }
}
