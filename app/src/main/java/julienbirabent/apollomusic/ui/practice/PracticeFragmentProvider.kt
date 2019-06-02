package julienbirabent.apollomusic.ui.practice

import dagger.Module
import dagger.android.ContributesAndroidInjector
import julienbirabent.apollomusic.ui.practice.create.PracticeCreateFragment
import julienbirabent.apollomusic.ui.practice.list.PracticeListFragment

@Module
abstract class PracticeFragmentProvider {

    @ContributesAndroidInjector
    abstract fun contributePracticeListFragment(): PracticeListFragment

    @ContributesAndroidInjector
    abstract fun contributePracticeCreateFragment(): PracticeCreateFragment
}