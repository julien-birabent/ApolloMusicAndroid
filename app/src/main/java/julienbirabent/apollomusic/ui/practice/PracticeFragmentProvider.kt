package julienbirabent.apollomusic.ui.practice

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class PracticeFragmentProvider {

    @ContributesAndroidInjector
    abstract fun contributePracticeListFragment(): PracticeListFragment
}