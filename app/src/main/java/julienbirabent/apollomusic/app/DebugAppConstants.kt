package julienbirabent.apollomusic.app

import julienbirabent.apollomusic.BuildConfig

class DebugAppConstants : AppConstants {

    override fun baseUrl(): String {
        return BuildConfig.BASE_URL
    }

    override fun dbName(): String {
        return "apollo_database_debug"
    }

    override fun dbVersion(): Int {
        return 1
    }

    override fun sharedPrefName(): String {
        return "shared_prefs_debug"
    }

    override fun adminProfileId(): Int {
        return 1
    }
}

