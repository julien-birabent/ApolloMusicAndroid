package julienbirabent.apollomusic.ui.practice.create

import android.app.Activity
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.squareup.timessquare.CalendarPickerView
import julienbirabent.apollomusic.BR
import julienbirabent.apollomusic.R
import julienbirabent.apollomusic.databinding.FragmentPracticeCreateBinding
import julienbirabent.apollomusic.databinding.ViewCalendarBinding
import julienbirabent.apollomusic.ui.adapters.DateAdapter
import julienbirabent.apollomusic.ui.adapters.objective.ObjectiveAdapter
import julienbirabent.apollomusic.ui.base.BaseFragment
import julienbirabent.apollomusic.ui.home.HomeActivity
import java.util.*
import javax.inject.Inject


class PracticeCreateFragment : BaseFragment<FragmentPracticeCreateBinding, PracticeCreateViewModel>(),
    PracticeCreateNavigator {
    private lateinit var dateAdapter: DateAdapter

    private lateinit var objAdapter: ObjectiveAdapter

    @Inject
    lateinit var calendar: Calendar

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        if (activity is HomeActivity) {
            activity.hideBottomNavigation(true)
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.getPracticesDates().observe(viewLifecycleOwner, Observer {
            dateAdapter.updateList(it)
        })

        viewModel.objList.observe(viewLifecycleOwner, Observer {
            objAdapter.updateList(it)
        })

        view?.let {
            it.setFocusableInTouchMode(true)
            it.requestFocus()
            it.setOnKeyListener { view, keyCode, keyEvent ->
                if (keyEvent.action == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // No choice but to clear manually the viewModel from the fragment because viewModel needs to be a singleton
                    // but we also need to refresh the vm's data when the user return to the home activity
                    viewModel.manualClear()
                }
                false
            }
        }


    }

    private fun showSingleSelectionCalendar(date: Date) {
        val calendarView = DataBindingUtil.inflate<ViewCalendarBinding>(
            layoutInflater,
            R.layout.view_calendar,
            null,
            false
        )
        val title = layoutInflater.inflate(R.layout.view_dialog_title, null) as TextView
        title.text = resources.getText(R.string.dialog_select_practice_dates_title)

        calendar.add(Calendar.YEAR, 1)
        calendarView.calendar.init(Date(), calendar.time).withSelectedDate(date)
            .inMode(CalendarPickerView.SelectionMode.SINGLE)
        calendar.time = Date()

        val datePickerDialog = AlertDialog.Builder(baseActivity, R.style.AlertDialogTheme_Blue)
            .setCustomTitle(title)
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
        val title = layoutInflater.inflate(R.layout.view_dialog_title, null) as TextView
        title.text = resources.getText(R.string.dialog_select_practice_dates_title)

        calendar.add(Calendar.YEAR, 1)
        calendarView.calendar.init(Date(), calendar.time)
            .inMode(CalendarPickerView.SelectionMode.MULTIPLE)
            .withSelectedDates(viewModel.getPracticesDates().value)

        calendar.time = Date()

        val datePickerDialog = AlertDialog.Builder(baseActivity, R.style.AlertDialogTheme_Blue)
            .setTitle(R.string.dialog_select_practice_dates_title)
            .setCustomTitle(title)
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

    override fun returnToPracticeList() {
        activity?.supportFragmentManager?.popBackStack()
        Toast.makeText(baseActivity, "Practice successfuly added.", Toast.LENGTH_LONG).show()
    }

    override fun onDetach() {
        super.onDetach()
        if (activity is HomeActivity) {
            (activity as HomeActivity).hideBottomNavigation(false)
        }
    }

    override fun goToCreateObjectivePage() {
        findNavController().navigate(R.id.action_practiceCreateFragment_to_objectiveCreateFragment)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        viewDataBinding.addObjectiveButton.setOnClickListener {
            viewModel.goToCreateObjectivePage()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.create_practice_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.save_practice -> viewModel.createPractice()
            else -> viewModel.manualClear()
        }

        return super.onOptionsItemSelected(item)
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
        objAdapter = ObjectiveAdapter(viewModel.objActionCallback)
        binding.setVariable(BR.objAdapter, objAdapter)
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_practice_create
    }

    override fun getViewModel(): PracticeCreateViewModel {
        return ViewModelProviders.of(this, viewModelFactory).get(PracticeCreateViewModel::class.java)
    }

    override fun createPracticeError() {
        Toast.makeText(baseActivity, "An error occured. Practice has not been created.", Toast.LENGTH_LONG).show()
    }

    override fun practiceContentMissingError() {
        baseActivity.showErrorDialog(
            "Cannot create practice",
            "In order to create a practice, you must have at least one date and one objective defined."
        )
    }
}
