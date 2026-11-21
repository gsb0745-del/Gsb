package com.example.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Celebration
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.R
import com.example.data.CalendarDestination
import com.example.data.PanchangRepository
import com.example.ui.components.AllFestivalsDirectorySection
import com.example.ui.components.CalendarGridCard
import com.example.ui.components.CalendarLegendStrip
import com.example.ui.components.DayPanchangDialog
import com.example.ui.components.FestivalFilterRow
import com.example.ui.components.JyotishGananaCard
import com.example.ui.components.MonthControlBar
import com.example.ui.components.MonthlyFestivalsCard
import com.example.ui.components.MonthlyRashifalCard
import com.example.ui.components.WallCalendarHeaderBanner
import com.example.ui.theme.AmberSoft
import com.example.ui.theme.CrimsonDarkest
import com.example.ui.theme.CrimsonDeep
import com.example.ui.theme.CrimsonPrimary
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GsCalendarTheme
import com.example.ui.theme.MarigoldBright
import com.example.ui.theme.SaffronGold
import com.example.viewmodel.CalendarUiState
import com.example.viewmodel.CalendarViewModel
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun GsCalendarApp(
  viewModel: CalendarViewModel = viewModel(),
) {
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()

  GsCalendarTheme(darkTheme = uiState.isDarkTheme) {
    BoxWithConstraints(
      modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background),
    ) {
      val isExpanded = maxWidth >= 840.dp

      Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing,
        bottomBar = {
          if (!isExpanded) {
            GsBottomNavigationBar(
              currentDestination = uiState.currentDestination,
              onSelectDestination = viewModel::selectDestination,
            )
          }
        },
      ) { innerPadding ->
        Row(
          modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        ) {
          if (isExpanded) {
            GsNavigationRail(
              currentDestination = uiState.currentDestination,
              onSelectDestination = viewModel::selectDestination,
            )
          }

          Box(
            modifier = Modifier
              .weight(1f)
              .fillMaxHeight(),
          ) {
            if (isExpanded && uiState.currentDestination != CalendarDestination.FESTIVALS) {
              ExpandedWallCalendarLayout(
                uiState = uiState,
                viewModel = viewModel,
              )
            } else {
              CompactOrDestinationLayout(
                uiState = uiState,
                isExpanded = isExpanded,
                viewModel = viewModel,
              )
            }
          }
        }
      }

      uiState.selectedDayPanchang?.let { panchang ->
        DayPanchangDialog(
          panchang = panchang,
          isDarkTheme = uiState.isDarkTheme,
          onStepDay = viewModel::stepDayDetail,
          onDismiss = viewModel::closeDayDetail,
        )
      }
    }
  }
}

@Composable
private fun CompactOrDestinationLayout(
  uiState: CalendarUiState,
  isExpanded: Boolean,
  viewModel: CalendarViewModel,
) {
  if (uiState.currentDestination != CalendarDestination.CALENDAR) {
    BackHandler {
      viewModel.selectDestination(CalendarDestination.CALENDAR)
    }
  }

  Column(modifier = Modifier.fillMaxSize()) {
    MonthControlBar(
      selectedMonthIndex = uiState.selectedMonthIndex,
      isDarkTheme = uiState.isDarkTheme,
      onPrevMonth = viewModel::previousMonth,
      onNextMonth = viewModel::nextMonth,
      onSelectMonth = viewModel::selectMonth,
      onToggleTheme = viewModel::toggleTheme,
      modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
    )

    when (uiState.currentDestination) {
      CalendarDestination.CALENDAR -> {
        val pagerState = rememberPagerState(
          initialPage = uiState.selectedMonthIndex,
          pageCount = { 12 },
        )

        LaunchedEffect(uiState.selectedMonthIndex) {
          if (pagerState.settledPage != uiState.selectedMonthIndex) {
            pagerState.animateScrollToPage(uiState.selectedMonthIndex)
          }
        }

        LaunchedEffect(pagerState) {
          snapshotFlow { pagerState.settledPage }
            .distinctUntilChanged()
            .collect { settled ->
              if (settled != uiState.selectedMonthIndex) {
                viewModel.selectMonth(settled)
              }
            }
        }

        HorizontalPager(
          state = pagerState,
          modifier = Modifier.fillMaxSize(),
          contentPadding = PaddingValues(0.dp),
        ) { pageMonthIdx ->
          val monthData = PanchangRepository.getMonthData(pageMonthIdx)
          Column(
            modifier = Modifier
              .fillMaxSize()
              .verticalScroll(rememberScrollState())
              .padding(horizontal = 10.dp, vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
          ) {
            FestivalFilterRow(
              activeFilter = uiState.activeFilter,
              onFilterSelect = viewModel::setFilter,
            )

            Surface(
              shape = RoundedCornerShape(16.dp),
              border = BorderStroke(1.5.dp, CrimsonPrimary),
              shadowElevation = 4.dp,
            ) {
              WallCalendarHeaderBanner(
                monthData = monthData,
                compactMode = true,
              )
            }

            CalendarGridCard(
              monthData = monthData,
              activeFilter = uiState.activeFilter,
              isDarkTheme = uiState.isDarkTheme,
              isExpandedWidth = isExpanded,
              onDayClick = viewModel::openDayDetail,
            )

            CalendarLegendStrip(isDarkTheme = uiState.isDarkTheme)

            MonthlyFestivalsCard(
              monthData = monthData,
              activeFilter = uiState.activeFilter,
              isDarkTheme = uiState.isDarkTheme,
              onFestivalClick = { fest ->
                viewModel.openFestivalDay(monthData.monthIndex, fest.day)
              },
            )

            CalendarFooterNote()
            Spacer(modifier = Modifier.height(8.dp))
          }
        }
      }

      CalendarDestination.JYOTISH -> {
        Column(
          modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 10.dp, vertical = 6.dp),
          verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
          WallCalendarHeaderBanner(
            monthData = uiState.currentMonthData,
            compactMode = true,
          )
          JyotishGananaCard(monthData = uiState.currentMonthData)
          CalendarFooterNote()
          Spacer(modifier = Modifier.height(8.dp))
        }
      }

      CalendarDestination.RASHIFAL -> {
        Column(
          modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 10.dp, vertical = 6.dp),
          verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
          MonthlyRashifalCard(
            monthData = uiState.currentMonthData,
            highlightedRashiIndex = uiState.highlightedRashiIndex,
            isDarkTheme = uiState.isDarkTheme,
            onToggleRashi = viewModel::toggleRashiHighlight,
          )
          CalendarFooterNote()
          Spacer(modifier = Modifier.height(8.dp))
        }
      }

      CalendarDestination.FESTIVALS -> {
        Column(
          modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 10.dp, vertical = 6.dp),
          verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
          AllFestivalsDirectorySection(
            searchQuery = uiState.festivalSearchQuery,
            activeFilter = uiState.activeFilter,
            festivals = uiState.directoryFestivals,
            isDarkTheme = uiState.isDarkTheme,
            onSearchQueryChange = viewModel::updateSearchQuery,
            onFilterSelect = viewModel::setFilter,
            onFestivalSelect = { item ->
              viewModel.openFestivalDay(item.monthIndex, item.festival.day)
            },
          )
          CalendarFooterNote()
          Spacer(modifier = Modifier.height(8.dp))
        }
      }
    }
  }
}

@Composable
private fun ExpandedWallCalendarLayout(
  uiState: CalendarUiState,
  viewModel: CalendarViewModel,
) {
  val monthData = uiState.currentMonthData

  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState())
      .padding(14.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp),
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(12.dp),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      MonthControlBar(
        selectedMonthIndex = uiState.selectedMonthIndex,
        isDarkTheme = uiState.isDarkTheme,
        onPrevMonth = viewModel::previousMonth,
        onNextMonth = viewModel::nextMonth,
        onSelectMonth = viewModel::selectMonth,
        onToggleTheme = viewModel::toggleTheme,
        modifier = Modifier.weight(1f),
      )
      FestivalFilterRow(
        activeFilter = uiState.activeFilter,
        onFilterSelect = viewModel::setFilter,
        modifier = Modifier.weight(1f),
      )
    }

    // Full Wall Calendar Sheet
    Surface(
      shape = RoundedCornerShape(20.dp),
      border = BorderStroke(2.dp, CrimsonPrimary),
      shadowElevation = 6.dp,
    ) {
      Column {
        WallCalendarHeaderBanner(
          monthData = monthData,
          compactMode = false,
        )

        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp),
          horizontalArrangement = Arrangement.spacedBy(14.dp),
          verticalAlignment = Alignment.Top,
        ) {
          Column(
            modifier = Modifier.weight(0.64f),
            verticalArrangement = Arrangement.spacedBy(12.dp),
          ) {
            CalendarGridCard(
              monthData = monthData,
              activeFilter = uiState.activeFilter,
              isDarkTheme = uiState.isDarkTheme,
              isExpandedWidth = true,
              onDayClick = viewModel::openDayDetail,
            )
            CalendarLegendStrip(isDarkTheme = uiState.isDarkTheme)
            MonthlyFestivalsCard(
              monthData = monthData,
              activeFilter = uiState.activeFilter,
              isDarkTheme = uiState.isDarkTheme,
              onFestivalClick = { fest ->
                viewModel.openFestivalDay(monthData.monthIndex, fest.day)
              },
            )
          }

          Column(
            modifier = Modifier.weight(0.36f),
            verticalArrangement = Arrangement.spacedBy(12.dp),
          ) {
            JyotishGananaCard(monthData = monthData)
            MonthlyRashifalCard(
              monthData = monthData,
              highlightedRashiIndex = uiState.highlightedRashiIndex,
              isDarkTheme = uiState.isDarkTheme,
              onToggleRashi = viewModel::toggleRashiHighlight,
            )
          }
        }

        CalendarFooterNote()
      }
    }
  }
}

@Composable
private fun CalendarFooterNote() {
  Surface(
    modifier = Modifier.fillMaxWidth(),
    shape = RoundedCornerShape(12.dp),
    color = CrimsonDarkest,
    border = BorderStroke(1.dp, SaffronGold.copy(alpha = 0.6f)),
  ) {
    Column(
      modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.spacedBy(2.dp),
    ) {
      Text(
        text = "© 2027 GS Calendar (जी.एस. कैलेंडर) — सर्वाधिकार सुरक्षित",
        color = GoldLight,
        fontWeight = FontWeight.Bold,
        fontSize = 11.sp,
        textAlign = TextAlign.Center,
      )
      Text(
        text = stringResource(R.string.disclaimer_note),
        color = AmberSoft.copy(alpha = 0.9f),
        fontSize = 10.sp,
        textAlign = TextAlign.Center,
      )
    }
  }
}

private fun destinationIcon(destination: CalendarDestination, selected: Boolean): ImageVector =
  when (destination) {
    CalendarDestination.CALENDAR ->
      if (selected) Icons.Filled.CalendarMonth else Icons.Outlined.CalendarMonth
    CalendarDestination.JYOTISH ->
      if (selected) Icons.Filled.WbSunny else Icons.Outlined.WbSunny
    CalendarDestination.RASHIFAL ->
      if (selected) Icons.Filled.AutoAwesome else Icons.Outlined.AutoAwesome
    CalendarDestination.FESTIVALS ->
      if (selected) Icons.Filled.Celebration else Icons.Outlined.Celebration
  }

@Composable
private fun GsBottomNavigationBar(
  currentDestination: CalendarDestination,
  onSelectDestination: (CalendarDestination) -> Unit,
) {
  NavigationBar(
    containerColor = CrimsonDarkest,
    contentColor = AmberSoft,
    tonalElevation = 6.dp,
  ) {
    CalendarDestination.entries.forEach { destination ->
      val selected = currentDestination == destination
      NavigationBarItem(
        selected = selected,
        onClick = { onSelectDestination(destination) },
        icon = {
          Icon(
            imageVector = destinationIcon(destination, selected),
            contentDescription = destination.labelHi,
          )
        },
        label = {
          Text(
            text = destination.labelHi,
            fontSize = 11.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
            maxLines = 1,
          )
        },
        colors = NavigationBarItemDefaults.colors(
          selectedIconColor = CrimsonDarkest,
          selectedTextColor = GoldLight,
          indicatorColor = MarigoldBright,
          unselectedIconColor = AmberSoft.copy(alpha = 0.78f),
          unselectedTextColor = AmberSoft.copy(alpha = 0.78f),
        ),
        modifier = Modifier.testTag("nav_item_${destination.name}"),
      )
    }
  }
}

@Composable
private fun GsNavigationRail(
  currentDestination: CalendarDestination,
  onSelectDestination: (CalendarDestination) -> Unit,
) {
  NavigationRail(
    containerColor = CrimsonDarkest,
    contentColor = AmberSoft,
    modifier = Modifier.width(88.dp),
  ) {
    Spacer(modifier = Modifier.height(16.dp))
    CalendarDestination.entries.forEach { destination ->
      val selected = currentDestination == destination
      NavigationRailItem(
        selected = selected,
        onClick = { onSelectDestination(destination) },
        icon = {
          Icon(
            imageVector = destinationIcon(destination, selected),
            contentDescription = destination.labelHi,
          )
        },
        label = {
          Text(
            text = destination.labelHi,
            fontSize = 11.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
            textAlign = TextAlign.Center,
          )
        },
        colors = NavigationRailItemDefaults.colors(
          selectedIconColor = CrimsonDarkest,
          selectedTextColor = GoldLight,
          indicatorColor = MarigoldBright,
          unselectedIconColor = AmberSoft.copy(alpha = 0.78f),
          unselectedTextColor = AmberSoft.copy(alpha = 0.78f),
        ),
        modifier = Modifier.testTag("rail_item_${destination.name}"),
      )
      Spacer(modifier = Modifier.height(8.dp))
    }
  }
}
