package julienbirabent.apollomusic.ui.adapters.practice

import julienbirabent.apollomusic.data.local.entities.PracticeEntity

interface PracticeItemCallback {

    fun openTodayPractice(item: PracticeEntity)
}