package julienbirabent.apollomusic.binding

import androidx.databinding.BindingAdapter
import com.google.android.gms.common.SignInButton

@BindingAdapter("android:onClick")
fun bindSignInGoogleClick(button: SignInButton, method: () -> Unit) {
    button.setOnClickListener { method.invoke() }
}

/*
@BindingAdapter("android:onClick")
fun bindSignInFacebookClick(button: com.facebook.login.widget.LoginButton, method: () -> Unit) {
    button.setOnClickListener { method.invoke() }
}*/
