package julienbirabent.apollomusic.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import julienbirabent.apollomusic.ui.objective.ObjectiveCreateFragment
import julienbirabent.apollomusic.ui.objective.criteria.ObjectiveCriteriaSelectionFragment
import julienbirabent.apollomusic.ui.practice.create.PracticeCreateFragment
import julienbirabent.apollomusic.ui.practice.list.PracticeListFragment

@Module
abstract class FragmentProvider {

    @ContributesAndroidInjector
    abstract fun contributePracticeListFragment(): PracticeListFragment

    @ContributesAndroidInjector
    abstract fun contributePracticeCreateFragment(): PracticeCreateFragment

    @ContributesAndroidInjector
    abstract fun contributeObjectiveCreateFragment(): ObjectiveCreateFragment

    @ContributesAndroidInjector
    abstract fun contributeObjectiveCriteriaSelectionFragment(): ObjectiveCriteriaSelectionFragment
}