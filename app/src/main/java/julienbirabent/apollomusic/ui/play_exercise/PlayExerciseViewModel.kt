package julienbirabent.apollomusic.ui.play_exercise

import julienbirabent.apollomusic.data.repository.ObjectiveRepository
import julienbirabent.apollomusic.data.repository.PracticeRepository
import julienbirabent.apollomusic.thread.AppSchedulerProvider
import julienbirabent.apollomusic.ui.base.BaseViewModel
import javax.inject.Inject

class PlayExerciseViewModel @Inject constructor(
    private val practiceRepo: PracticeRepository,
    private val objectiveRepository: ObjectiveRepository,
    private val scheduler: AppSchedulerProvider
) : BaseViewModel<PlayExerciseNavigator>() {

}
