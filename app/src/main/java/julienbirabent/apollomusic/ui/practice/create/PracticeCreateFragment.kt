package julienbirabent.apollomusic.ui.practice.create

import android.app.Activity
import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.squareup.timessquare.CalendarPickerView
import julienbirabent.apollomusic.BR
import julienbirabent.apollomusic.R
import julienbirabent.apollomusic.databinding.FragmentPracticeCreateBinding
import julienbirabent.apollomusic.databinding.ViewCalendarBinding
import julienbirabent.apollomusic.ui.adapters.DateAdapter
import julienbirabent.apollomusic.ui.base.BaseFragment
import julienbirabent.apollomusic.ui.home.HomeActivity
import java.util.*
import javax.inject.Inject


class PracticeCreateFragment : BaseFragment<FragmentPracticeCreateBinding, PracticeCreateViewModel>(),
    PracticeCreateNavigator {
    private lateinit var dateAdapter: DateAdapter

    @Inject
    lateinit var calendar: Calendar

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        if (activity is HomeActivity) {
            activity.hideBottomNavigation(true)
        }
    }

    private fun showSingleSelectionCalendar(date: Date) {
        val calendarView = DataBindingUtil.inflate<ViewCalendarBinding>(
            layoutInflater,
            R.layout.view_calendar,
            null,
            false
        )
        calendar.add(Calendar.YEAR, 1)
        calendarView.calendar.init(Date(), calendar.time).withSelectedDate(date)
            .inMode(CalendarPickerView.SelectionMode.SINGLE)
        calendar.time = Date()

        val datePickerDialog = AlertDialog.Builder(baseActivity, R.style.AlertDialogTheme)
            .setTitle(R.string.dialog_select_practice_dates_title)
            .setView(calendarView.root)
            .setNegativeButton(R.string.dialog_cancel) { dialogInterface: DialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .setNeutralButton(
                R.string.dialog_done
            ) { dialogInterface, _ ->

                viewModel.replaceDate(date, calendarView.calendar.selectedDate)
                dialogInterface.dismiss()
            }
            .create()
        datePickerDialog.show()
    }

    private fun showMultiSelectionCalendar() {
        val calendarView = DataBindingUtil.inflate<ViewCalendarBinding>(
            layoutInflater,
            R.layout.view_calendar,
            null,
            false
        )
        calendar.add(Calendar.YEAR, 1)
        calendarView.calendar.init(Date(), calendar.time)
            .inMode(CalendarPickerView.SelectionMode.MULTIPLE)
            .withSelectedDates(viewModel.getPracticesDates().value)

        calendar.time = Date()

        val datePickerDialog = AlertDialog.Builder(baseActivity, R.style.AlertDialogTheme)
            .setTitle(R.string.dialog_select_practice_dates_title)
            .setView(calendarView.root)
            .setNegativeButton(R.string.dialog_cancel) { dialogInterface: DialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .setNeutralButton(
                R.string.dialog_done
            ) { dialogInterface, _ ->

                viewModel.addDatesToPractice(calendarView.calendar.selectedDates)
                dialogInterface.dismiss()
            }
            .create()
        datePickerDialog.show()
    }

    override fun onDetach() {
        super.onDetach()
        if (activity is HomeActivity) {
            (activity as HomeActivity).hideBottomNavigation(false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        viewModel.getPracticesDates().observe(viewLifecycleOwner, Observer {
            dateAdapter.setItems(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.create_practice_menu, menu);
        super.onCreateOptionsMenu(menu, inflater)

    }

    override fun goToSimpleSelectionCalendar(date: Date) {
        showSingleSelectionCalendar(date)
    }

    override fun goToMultiSelectionCalendar() {
        showMultiSelectionCalendar()
    }

    override fun setBindingVariables(binding: ViewDataBinding) {
        super.setBindingVariables(binding)
        dateAdapter = DateAdapter(viewModel.dateActionItemCallback)
        binding.setVariable(BR.dateAdapter, dateAdapter)
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_practice_create
    }

    override fun getViewModel(): PracticeCreateViewModel {
        return ViewModelProviders.of(baseActivity).get(PracticeCreateViewModel::class.java)
    }

    companion object {
        @JvmStatic
        fun newInstance() = PracticeCreateFragment()
    }
}
