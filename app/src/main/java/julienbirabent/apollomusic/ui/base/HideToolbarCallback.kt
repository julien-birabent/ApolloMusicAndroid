package julienbirabent.apollomusic.ui.base

interface HideToolbarCallback {

    @JvmDefault
    fun onHideToolbar(toolbarIsShown: Boolean) {}
}