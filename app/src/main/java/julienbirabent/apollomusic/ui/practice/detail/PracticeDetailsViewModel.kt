package julienbirabent.apollomusic.ui.practice.detail

import julienbirabent.apollomusic.data.repository.PracticeRepository
import julienbirabent.apollomusic.thread.AppSchedulerProvider
import julienbirabent.apollomusic.ui.base.BaseViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PracticeDetailsViewModel @Inject constructor(
    private val practiceRepo: PracticeRepository,
    private val scheduler: AppSchedulerProvider
) :
    BaseViewModel<PracticeDetailsNavigator>() {

    var practiceId: Int = -1

}