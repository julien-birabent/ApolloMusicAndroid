package julienbirabent.apollomusic.app

import julienbirabent.apollomusic.BuildConfig

class DebugAppConstants() : AppConstants {

    override fun baseUrl(): String {
        return BuildConfig.BASE_URL
    }

    override fun apiKey(): String {
        return BuildConfig.API_KEY
    }

    override fun dbName(): String {
        return "apollo_database_debug"
    }

    override fun dbVersion(): Int {
        return 1
    }

}
