package julienbirabent.apollomusic.di.scope

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import javax.inject.Scope

@Scope
@MustBeDocumented
@Retention(value = RetentionPolicy.RUNTIME)
annotation class UserScope