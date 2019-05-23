package julienbirabent.apollomusic.app

interface AppConstants {

    fun baseUrl() : String

    fun apiKey(): String

    fun dbName():String

    fun dbVersion(): Int

    fun sharedPrefName(): String
}