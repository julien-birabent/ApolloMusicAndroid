package julienbirabent.apollomusic.data.local.model

import julienbirabent.apollomusic.data.local.entities.CriteriaEntity
import julienbirabent.apollomusic.data.local.entities.ExerciseEntity
import julienbirabent.apollomusic.data.local.entities.ObjectiveEntity

data class ObjectiveBundle(val obj: ObjectiveEntity, val ex: ExerciseEntity?, val crit: CriteriaEntity?)