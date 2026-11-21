package com.example.viewmodel

import androidx.lifecycle.ViewModel
import com.example.data.CalendarDestination
import com.example.data.DayPanchang
import com.example.data.FestivalFilter
import com.example.data.FestivalWithMonth
import com.example.data.MonthCalendarData
import com.example.data.PanchangRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class CalendarUiState(
  val selectedMonthIndex: Int = 0,
  val activeFilter: FestivalFilter = FestivalFilter.ALL,
  val selectedDayPanchang: DayPanchang? = null,
  val highlightedRashiIndex: Int? = null,
  val festivalSearchQuery: String = "",
  val currentDestination: CalendarDestination = CalendarDestination.CALENDAR,
  val isDarkTheme: Boolean = false,
) {
  val currentMonthData: MonthCalendarData
    get() = PanchangRepository.getMonthData(selectedMonthIndex)

  val directoryFestivals: List<FestivalWithMonth>
    get() = PanchangRepository.getAllYearFestivals(activeFilter, festivalSearchQuery)
}

class CalendarViewModel : ViewModel() {

  private val _uiState = MutableStateFlow(CalendarUiState())
  val uiState: StateFlow<CalendarUiState> = _uiState.asStateFlow()

  fun selectMonth(monthIndex: Int) {
    val clamped = ((monthIndex % 12) + 12) % 12
    _uiState.update { it.copy(selectedMonthIndex = clamped) }
  }

  fun nextMonth() {
    _uiState.update { it.copy(selectedMonthIndex = (it.selectedMonthIndex + 1) % 12) }
  }

  fun previousMonth() {
    _uiState.update { it.copy(selectedMonthIndex = (it.selectedMonthIndex + 11) % 12) }
  }

  fun setFilter(filter: FestivalFilter) {
    _uiState.update { it.copy(activeFilter = filter) }
  }

  fun openDayDetail(dayPanchang: DayPanchang) {
    _uiState.update { it.copy(selectedDayPanchang = dayPanchang) }
  }

  fun openFestivalDay(monthIndex: Int, dayNum: Int) {
    val panchang = PanchangRepository.getDayPanchang(monthIndex, dayNum)
    _uiState.update {
      it.copy(
        selectedMonthIndex = monthIndex,
        selectedDayPanchang = panchang,
      )
    }
  }

  fun stepDayDetail(deltaDays: Int) {
    val current = _uiState.value.selectedDayPanchang ?: return
    val nextDay = PanchangRepository.stepDay(current, deltaDays)
    _uiState.update {
      it.copy(
        selectedMonthIndex = nextDay.monthIdx,
        selectedDayPanchang = nextDay,
      )
    }
  }

  fun closeDayDetail() {
    _uiState.update { it.copy(selectedDayPanchang = null) }
  }

  fun toggleRashiHighlight(index: Int) {
    _uiState.update {
      it.copy(highlightedRashiIndex = if (it.highlightedRashiIndex == index) null else index)
    }
  }

  fun updateSearchQuery(query: String) {
    _uiState.update { it.copy(festivalSearchQuery = query) }
  }

  fun selectDestination(destination: CalendarDestination) {
    _uiState.update { it.copy(currentDestination = destination) }
  }

  fun toggleTheme() {
    _uiState.update { it.copy(isDarkTheme = !it.isDarkTheme) }
  }
}
