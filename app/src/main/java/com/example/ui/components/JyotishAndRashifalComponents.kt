package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.FestivalFilter
import com.example.data.FestivalWithMonth
import com.example.data.MonthCalendarData
import com.example.data.PanchangRepository
import com.example.data.RashifalItem
import com.example.ui.theme.AmberBorder
import com.example.ui.theme.AmberSoft
import com.example.ui.theme.CrimsonDarkest
import com.example.ui.theme.CrimsonDeep
import com.example.ui.theme.CrimsonPrimary
import com.example.ui.theme.EnglishNumericFontFamily
import com.example.ui.theme.GoldLight
import com.example.ui.theme.MarigoldBright
import com.example.ui.theme.SaffronGold

@Composable
fun JyotishGananaCard(
  monthData: MonthCalendarData,
  modifier: Modifier = Modifier,
) {
  val jyotish = monthData.jyotish

  Surface(
    modifier = modifier.fillMaxWidth(),
    shape = RoundedCornerShape(16.dp),
    border = BorderStroke(2.dp, MarigoldBright),
    shadowElevation = 4.dp,
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .background(
          brush = Brush.verticalGradient(
            colors = listOf(CrimsonDarkest, CrimsonDeep),
          ),
        )
        .padding(14.dp),
      verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Text(
          text = "🪐 कालदर्शक ज्योतिष गणना",
          color = GoldLight,
          fontWeight = FontWeight.ExtraBold,
          fontSize = 16.sp,
        )
        Surface(
          shape = RoundedCornerShape(6.dp),
          color = MarigoldBright,
        ) {
          Text(
            text = "पंचांग सार",
            color = CrimsonDarkest,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 11.sp,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
          )
        }
      }

      // Sunrise & Sunset Box
      Surface(
        shape = RoundedCornerShape(12.dp),
        color = CrimsonPrimary.copy(alpha = 0.6f),
        border = BorderStroke(1.dp, AmberBorder.copy(alpha = 0.4f)),
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
          horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
              text = "🌅 सूर्योदय (औसत)",
              color = GoldLight,
              fontWeight = FontWeight.Bold,
              fontSize = 11.sp,
            )
            Text(
              text = monthData.sunrise,
              fontFamily = EnglishNumericFontFamily,
              color = Color.White,
              fontWeight = FontWeight.Bold,
              fontSize = 14.sp,
            )
          }

          Box(
            modifier = Modifier
              .size(width = 1.dp, height = 34.dp)
              .background(AmberBorder.copy(alpha = 0.35f)),
          )

          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
              text = "🌇 सूर्यास्त (औसत)",
              color = GoldLight,
              fontWeight = FontWeight.Bold,
              fontSize = 11.sp,
            )
            Text(
              text = monthData.sunset,
              fontFamily = EnglishNumericFontFamily,
              color = Color.White,
              fontWeight = FontWeight.Bold,
              fontSize = 14.sp,
            )
          }
        }
      }

      // Muhurat Section
      Surface(
        shape = RoundedCornerShape(12.dp),
        color = CrimsonDarkest.copy(alpha = 0.75f),
        border = BorderStroke(1.dp, MarigoldBright.copy(alpha = 0.35f)),
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
          verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
          JyotishDetailLine("💍 शुभ विवाह मुहूर्त:", jyotish.vivah)
          JyotishDetailLine("🏠 गृह प्रवेश मुहूर्त:", jyotish.grihaPravesh)
          JyotishDetailLine("🚗 वाहन क्रय मुहूर्त:", jyotish.vahan)
        }
      }

      // Panchak, Grahan & Vishesh
      Surface(
        shape = RoundedCornerShape(12.dp),
        color = MarigoldBright.copy(alpha = 0.16f),
        border = BorderStroke(1.dp, AmberBorder.copy(alpha = 0.45f)),
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
          verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
          JyotishDetailLine("⚠️ पंचक विचार:", jyotish.panchak)
          JyotishDetailLine("🌑 ग्रहण विवरण:", jyotish.grahan)
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .height(1.dp)
              .background(AmberBorder.copy(alpha = 0.25f)),
          )
          Text(
            text = "✨ ${jyotish.vishesh}",
            color = GoldLight,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
            lineHeight = 18.sp,
          )
        }
      }
    }
  }
}

@Composable
private fun JyotishDetailLine(label: String, value: String) {
  Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
    Text(
      text = label,
      color = GoldLight,
      fontWeight = FontWeight.Bold,
      fontSize = 12.sp,
    )
    Text(
      text = value,
      color = AmberSoft,
      fontSize = 12.sp,
      lineHeight = 17.sp,
    )
  }
}

@Composable
fun MonthlyRashifalCard(
  monthData: MonthCalendarData,
  highlightedRashiIndex: Int?,
  isDarkTheme: Boolean,
  onToggleRashi: (Int) -> Unit,
  modifier: Modifier = Modifier,
) {
  Surface(
    modifier = modifier.fillMaxWidth(),
    shape = RoundedCornerShape(16.dp),
    color = MaterialTheme.colorScheme.surface,
    border = BorderStroke(1.5.dp, CrimsonPrimary),
    tonalElevation = 2.dp,
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Column {
          Text(
            text = "♈ मासिक राशिफल (Rashifal)",
            color = if (isDarkTheme) GoldLight else CrimsonPrimary,
            fontWeight = FontWeight.Black,
            fontSize = 16.sp,
          )
          Text(
            text = "${monthData.hindiName} 2027 — सभी १२ राशियों का फल (टैप कर चुनें)",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
          )
        }
        Text(text = "🔮", fontSize = 22.sp)
      }

      Spacer(modifier = Modifier.height(8.dp))
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(2.dp)
          .background(CrimsonPrimary),
      )
      Spacer(modifier = Modifier.height(10.dp))

      Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        monthData.rashifal.forEachIndexed { idx, item ->
          RashiItemCard(
            item = item,
            isHighlighted = idx == highlightedRashiIndex,
            isDarkTheme = isDarkTheme,
            onClick = { onToggleRashi(idx) },
            modifier = Modifier.testTag("rashi_card_$idx"),
          )
        }
      }
    }
  }
}

@Composable
private fun RashiItemCard(
  item: RashifalItem,
  isHighlighted: Boolean,
  isDarkTheme: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Surface(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(12.dp))
      .clickable(onClick = onClick),
    shape = RoundedCornerShape(12.dp),
    color = when {
      isHighlighted && isDarkTheme -> CrimsonDeep.copy(alpha = 0.5f)
      isHighlighted -> AmberSoft
      isDarkTheme -> MaterialTheme.colorScheme.surfaceVariant
      else -> Color.White
    },
    border = BorderStroke(
      width = if (isHighlighted) 2.dp else 1.dp,
      color = if (isHighlighted) CrimsonPrimary else AmberBorder.copy(alpha = 0.65f),
    ),
  ) {
    Column(modifier = Modifier.padding(11.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
          Box(
            modifier = Modifier
              .size(28.dp)
              .clip(CircleShape)
              .background(CrimsonPrimary),
            contentAlignment = Alignment.Center,
          ) {
            Text(
              text = item.icon,
              color = GoldLight,
              fontWeight = FontWeight.Bold,
              fontSize = 14.sp,
            )
          }
          Text(
            text = item.rashi,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 14.sp,
            color = if (isDarkTheme) GoldLight else CrimsonDarkest,
          )
        }

        Surface(
          shape = RoundedCornerShape(50),
          color = if (isDarkTheme) CrimsonDarkest else AmberSoft,
          border = BorderStroke(1.dp, SaffronGold.copy(alpha = 0.6f)),
        ) {
          Text(
            text = "शुभ: ${item.lucky}",
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = if (isDarkTheme) GoldLight else CrimsonDarkest,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
          )
        }
      }

      Spacer(modifier = Modifier.height(6.dp))

      Text(
        text = item.text,
        fontSize = 12.5.sp,
        lineHeight = 18.sp,
        color = MaterialTheme.colorScheme.onSurface,
      )
    }
  }
}

@Composable
fun AllFestivalsDirectorySection(
  searchQuery: String,
  activeFilter: FestivalFilter,
  festivals: List<FestivalWithMonth>,
  isDarkTheme: Boolean,
  onSearchQueryChange: (String) -> Unit,
  onFilterSelect: (FestivalFilter) -> Unit,
  onFestivalSelect: (FestivalWithMonth) -> Unit,
  modifier: Modifier = Modifier,
) {
  Column(
    modifier = modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(10.dp),
  ) {
    Surface(
      shape = RoundedCornerShape(16.dp),
      color = MaterialTheme.colorScheme.surface,
      border = BorderStroke(1.5.dp, CrimsonPrimary),
    ) {
      Column(
        modifier = Modifier.padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically,
        ) {
          Text(
            text = "🗓️ 2027 सम्पूर्ण पर्व एवं अवकाश सूची",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 16.sp,
            color = if (isDarkTheme) GoldLight else CrimsonPrimary,
          )
          Surface(
            shape = RoundedCornerShape(50),
            color = CrimsonPrimary,
          ) {
            Text(
              text = "${PanchangRepository.toDevanagariDigits(festivals.size)} पर्व",
              color = Color.White,
              fontWeight = FontWeight.Bold,
              fontSize = 11.sp,
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp),
            )
          }
        }

        OutlinedTextField(
          value = searchQuery,
          onValueChange = onSearchQueryChange,
          placeholder = {
            Text(
              text = "त्योहार खोजें (जैसे: दिवाली, होली, ईद, एकादशी...)",
              fontSize = 13.sp,
            )
          },
          leadingIcon = {
            Icon(
              imageVector = Icons.Default.Search,
              contentDescription = "खोजें",
              tint = CrimsonPrimary,
            )
          },
          trailingIcon = {
            if (searchQuery.isNotEmpty()) {
              IconButton(onClick = { onSearchQueryChange("") }) {
                Icon(
                  imageVector = Icons.Default.Clear,
                  contentDescription = "साफ़ करें",
                )
              }
            }
          },
          singleLine = true,
          shape = RoundedCornerShape(12.dp),
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = CrimsonPrimary,
            unfocusedBorderColor = AmberBorder,
          ),
          modifier = Modifier
            .fillMaxWidth()
            .testTag("festival_search_input"),
        )

        FestivalFilterRow(
          activeFilter = activeFilter,
          onFilterSelect = onFilterSelect,
        )
      }
    }

    if (festivals.isEmpty()) {
      Surface(
        shape = RoundedCornerShape(14.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, AmberBorder),
        modifier = Modifier.fillMaxWidth(),
      ) {
        Text(
          text = "कोई पर्व नहीं मिला। कृपया खोज शब्द या फ़िल्टर बदलें।",
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          fontWeight = FontWeight.SemiBold,
          textAlign = TextAlign.Center,
          modifier = Modifier.padding(24.dp),
        )
      }
    } else {
      festivals.forEach { item ->
        FestivalListItemRow(
          day = item.festival.day,
          weekdayShort = item.monthNameHi.take(4),
          subtitle = "${item.weekdayNameHi} • ${item.monthNameHi} 2027",
          festival = item.festival,
          isDarkTheme = isDarkTheme,
          onClick = { onFestivalSelect(item) },
        )
      }
    }
  }
}
