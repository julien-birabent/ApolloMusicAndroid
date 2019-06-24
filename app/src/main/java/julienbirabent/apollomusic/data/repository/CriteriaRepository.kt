package julienbirabent.apollomusic.data.repository

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CriteriaRepository @Inject constructor(
    private val userRepo: UserRepository
) : BaseRepository() {

}