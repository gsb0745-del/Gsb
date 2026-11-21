package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.R
import com.example.data.DayPanchang
import com.example.data.PanchangRepository
import com.example.ui.theme.AmberBorder
import com.example.ui.theme.AmberSoft
import com.example.ui.theme.CrimsonBright
import com.example.ui.theme.CrimsonDarkest
import com.example.ui.theme.CrimsonPrimary
import com.example.ui.theme.EmeraldBg
import com.example.ui.theme.EmeraldBorder
import com.example.ui.theme.EmeraldHijri
import com.example.ui.theme.EmeraldLight
import com.example.ui.theme.EnglishNumericFontFamily
import com.example.ui.theme.GoldLight
import com.example.ui.theme.ParchmentCream
import com.example.ui.theme.SaffronGold
import com.example.ui.theme.SundayBg

@Composable
fun DayPanchangDialog(
  panchang: DayPanchang,
  isDarkTheme: Boolean,
  onStepDay: (Int) -> Unit,
  onDismiss: () -> Unit,
) {
  Dialog(
    onDismissRequest = onDismiss,
    properties = DialogProperties(usePlatformDefaultWidth = false),
  ) {
    Surface(
      modifier = Modifier
        .fillMaxWidth(0.94f)
        .widthIn(max = 520.dp),
      shape = RoundedCornerShape(22.dp),
      color = if (isDarkTheme) MaterialTheme.colorScheme.surface else ParchmentCream,
      border = BorderStroke(3.dp, CrimsonPrimary),
      shadowElevation = 12.dp,
    ) {
      Column(
        modifier = Modifier
          .padding(4.dp)
          .border(1.5.dp, SaffronGold, RoundedCornerShape(18.dp))
          .padding(16.dp),
      ) {
        // Header Row with Close Button
        Box(modifier = Modifier.fillMaxWidth()) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 36.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
          ) {
            Text(
              text = "GS CALENDAR 2027 • दैनिक कालदर्शक पंचांग",
              fontSize = 10.sp,
              fontWeight = FontWeight.ExtraBold,
              color = if (isDarkTheme) GoldLight else CrimsonPrimary,
              textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "${panchang.dayNum} ${panchang.monthNameHi} 2027, ${panchang.weekdayName}",
              fontSize = 20.sp,
              fontWeight = FontWeight.Black,
              color = MaterialTheme.colorScheme.onSurface,
              textAlign = TextAlign.Center,
            )
            Text(
              text = "विक्रम संवत ${panchang.vsYear} (${panchang.lunarMasa} मास) • हिजरी सन् ${panchang.hijriYearNum}",
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold,
              color = if (isDarkTheme) AmberSoft else CrimsonPrimary,
              textAlign = TextAlign.Center,
            )
          }

          IconButton(
            onClick = onDismiss,
            modifier = Modifier
              .align(Alignment.TopEnd)
              .size(40.dp)
              .clip(CircleShape)
              .background(CrimsonPrimary)
              .testTag("close_panchang_dialog"),
          ) {
            Icon(
              imageVector = Icons.Default.Close,
              contentDescription = stringResource(R.string.close_label),
              tint = Color.White,
              modifier = Modifier.size(18.dp),
            )
          }
        }

        Spacer(modifier = Modifier.height(10.dp))
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(2.dp)
            .background(AmberBorder),
        )
        Spacer(modifier = Modifier.height(12.dp))

        // Festival Banner if present
        if (panchang.dayFestivals.isNotEmpty()) {
          Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, CrimsonBright.copy(alpha = 0.6f)),
          ) {
            Column(
              modifier = Modifier
                .background(
                  brush = Brush.horizontalGradient(
                    colors = if (isDarkTheme) {
                      listOf(CrimsonPrimary.copy(alpha = 0.4f), SaffronGold.copy(alpha = 0.3f))
                    } else {
                      listOf(SundayBg, AmberSoft)
                    },
                  ),
                )
                .padding(10.dp),
              horizontalAlignment = Alignment.CenterHorizontally,
            ) {
              panchang.dayFestivals.forEach { fest ->
                Text(
                  text = "🎊 ${fest.title} ${if (fest.isGovt) "(राजकीय अवकाश)" else ""}",
                  fontWeight = FontWeight.ExtraBold,
                  fontSize = 13.sp,
                  color = if (isDarkTheme) GoldLight else CrimsonDarkest,
                  textAlign = TextAlign.Center,
                )
              }
            }
          }
          Spacer(modifier = Modifier.height(12.dp))
        }

        // 6 Panchang Tiles (2x3)
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
          ) {
            PanchangInfoTile(
              label = "हिन्दू तिथि व पक्ष",
              value = "${panchang.lunarMasa} ${panchang.tithiInfo.paksha} ${panchang.tithiInfo.fullName}",
              borderColor = AmberBorder,
              isDarkTheme = isDarkTheme,
              modifier = Modifier.weight(1f),
            )
            PanchangInfoTile(
              label = "इस्लामी हिजरी तारीख",
              value = "${PanchangRepository.toDevanagariDigits(panchang.hijriDay)} ${panchang.hijriMonth} ${panchang.hijriYearNum} हि.",
              borderColor = EmeraldBorder,
              bgColor = if (isDarkTheme) Color(0xFF064E3B).copy(alpha = 0.35f) else EmeraldBg,
              valueColor = if (isDarkTheme) EmeraldLight else EmeraldHijri,
              isDarkTheme = isDarkTheme,
              modifier = Modifier.weight(1f),
            )
          }

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
          ) {
            PanchangInfoTile(
              label = "नक्षत्र (Nakshatra)",
              value = panchang.nakshatra,
              borderColor = AmberBorder,
              isDarkTheme = isDarkTheme,
              modifier = Modifier.weight(1f),
            )
            PanchangInfoTile(
              label = "योग (Yoga)",
              value = panchang.yoga,
              borderColor = AmberBorder,
              isDarkTheme = isDarkTheme,
              modifier = Modifier.weight(1f),
            )
          }

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
          ) {
            PanchangInfoTile(
              label = "सूर्योदय / सूर्यास्त",
              value = "${panchang.sunrise} / ${panchang.sunset}",
              borderColor = AmberBorder,
              isEnglishNum = true,
              isDarkTheme = isDarkTheme,
              modifier = Modifier.weight(1f),
            )
            PanchangInfoTile(
              label = "राहुकाल (अशुभ समय)",
              value = panchang.rahukaal,
              borderColor = CrimsonBright.copy(alpha = 0.6f),
              bgColor = if (isDarkTheme) CrimsonPrimary.copy(alpha = 0.3f) else SundayBg.copy(alpha = 0.6f),
              labelColor = if (isDarkTheme) GoldLight else CrimsonBright,
              valueColor = if (isDarkTheme) Color.White else CrimsonDarkest,
              isDarkTheme = isDarkTheme,
              modifier = Modifier.weight(1f),
            )
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Day Stepper & Close Actions
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically,
        ) {
          OutlinedButton(
            onClick = { onStepDay(-1) },
            enabled = !(panchang.monthIdx == 0 && panchang.dayNum == 1),
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(1.dp, SaffronGold),
            modifier = Modifier.testTag("prev_day_button"),
          ) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
              contentDescription = "पिछला दिन",
              modifier = Modifier.size(18.dp),
            )
            Text("पिछला दिन", fontSize = 11.sp, fontWeight = FontWeight.Bold)
          }

          OutlinedButton(
            onClick = { onStepDay(1) },
            enabled = !(panchang.monthIdx == 11 && panchang.dayNum == 31),
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(1.dp, SaffronGold),
            modifier = Modifier.testTag("next_day_button"),
          ) {
            Text("अगला दिन", fontSize = 11.sp, fontWeight = FontWeight.Bold)
            Icon(
              imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
              contentDescription = "अगला दिन",
              modifier = Modifier.size(18.dp),
            )
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
          onClick = onDismiss,
          shape = RoundedCornerShape(10.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = CrimsonPrimary,
            contentColor = Color.White,
          ),
          modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .testTag("dismiss_panchang_button"),
        ) {
          Text(
            text = stringResource(R.string.close_label),
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
          )
        }
      }
    }
  }
}

@Composable
private fun PanchangInfoTile(
  label: String,
  value: String,
  borderColor: Color,
  isDarkTheme: Boolean,
  modifier: Modifier = Modifier,
  bgColor: Color? = null,
  labelColor: Color? = null,
  valueColor: Color? = null,
  isEnglishNum: Boolean = false,
) {
  val containerColor = bgColor ?: if (isDarkTheme) {
    MaterialTheme.colorScheme.surfaceVariant
  } else {
    Color.White
  }

  Surface(
    modifier = modifier,
    shape = RoundedCornerShape(12.dp),
    color = containerColor,
    border = BorderStroke(1.dp, borderColor),
  ) {
    Column(modifier = Modifier.padding(10.dp)) {
      Text(
        text = label,
        fontSize = 10.sp,
        fontWeight = FontWeight.Bold,
        color = labelColor ?: MaterialTheme.colorScheme.onSurfaceVariant,
      )
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = value,
        fontFamily = if (isEnglishNum) EnglishNumericFontFamily else MaterialTheme.typography.bodyMedium.fontFamily,
        fontSize = 12.5.sp,
        fontWeight = FontWeight.ExtraBold,
        color = valueColor ?: MaterialTheme.colorScheme.onSurface,
      )
    }
  }
}
