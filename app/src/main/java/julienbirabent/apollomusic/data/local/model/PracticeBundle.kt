package julienbirabent.apollomusic.data.local.model

import julienbirabent.apollomusic.data.local.entities.PracticeEntity

data class PracticeBundle(val practice: PracticeEntity, val ObjBundleList: List<ObjectiveBundle>)