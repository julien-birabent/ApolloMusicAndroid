package julienbirabent.apollomusic.app

import julienbirabent.apollomusic.BuildConfig

class ReleaseAppConstants() : AppConstants {

    override fun baseUrl(): String {
        return BuildConfig.BASE_URL
    }

    override fun apiKey(): String {
        return BuildConfig.API_KEY
    }

    override fun dbName(): String {
        return "apollo_database_release"
    }

    override fun dbVersion(): Int {
        return 1
    }

}