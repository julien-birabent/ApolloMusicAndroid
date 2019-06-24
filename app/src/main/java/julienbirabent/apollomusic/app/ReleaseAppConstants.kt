package julienbirabent.apollomusic.app

import julienbirabent.apollomusic.BuildConfig

class ReleaseAppConstants : AppConstants {

    override fun baseUrl(): String {
        return BuildConfig.BASE_URL
    }

    override fun dbName(): String {
        return "apollo_database_release"
    }

    override fun dbVersion(): Int {
        return 1
    }

    override fun sharedPrefName(): String {
        return "shared_prefs_release"
    }

    override fun adminProfileI(): Int {
        return 0
    }
}