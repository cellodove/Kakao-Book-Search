

object Versions {
    const val KOTLINX_COROUTINES = "1.5.0"
    const val STDLIB ="1.6.21"

    const val CORE_KTX = "1.8.0"
    const val APP_COMPAT = "1.4.2"
    const val ACTIVITY_KTX = "1.4.0"
    const val FRAGMENT_KTX = "1.4.1"

    const val LIFECYCLE_KTX = "2.4.1"

    const val HILT = "2.35.1"
    const val MATERIAL = "1.6.1"

    const val RETROFIT = "2.9.0"
    const val OKHTTP = "4.9.3"

    const val JUNIT = "4.13.2"
    const val ANDROID_JUNIT = "1.1.2"
    const val ESPRESSO_CORE = "3.4.0"

    const val GLIDE_VER = "4.12.0"
    const val PAGING_VERSION = "3.1.1"
}

object Kotlin {
    const val KOTLIN_STDLIB      = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.STDLIB}"
    const val COROUTINES_CORE    = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.KOTLINX_COROUTINES}"
}

object AndroidX {
    const val CORE_KTX                = "androidx.core:core-ktx:${Versions.CORE_KTX}"
    const val APP_COMPAT              = "androidx.appcompat:appcompat:${Versions.APP_COMPAT}"

    const val ACTIVITY_KTX            = "androidx.activity:activity-ktx:${Versions.ACTIVITY_KTX}"
    const val FRAGMENT_KTX            = "androidx.fragment:fragment-ktx:${Versions.FRAGMENT_KTX}"

    const val LIFECYCLE_VIEWMODEL_KTX = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.LIFECYCLE_KTX}"
    const val LIFECYCLE_LIVEDATA_KTX  = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.LIFECYCLE_KTX}"
}

object Google {
    const val HILT_ANDROID          = "com.google.dagger:hilt-android:${Versions.HILT}"
    const val HILT_ANDROID_COMPILER = "com.google.dagger:hilt-android-compiler:${Versions.HILT}"
    const val MATERIAL = "com.google.android.material:material:${Versions.MATERIAL}"
}

object JetpackPack{
    const val NAVIGATION_FRAGMENT  = "androidx.navigation:navigation-fragment-ktx:2.4.2"
    const val NAVIGATION_UI        = "androidx.navigation:navigation-ui-ktx:2.4.2"
    const val NAVIGATION_DYNAMIC   = "androidx.navigation:navigation-dynamic-features-fragment:2.4.2"
    const val NAVIGATION_TEST_IMP = "androidx.navigation:navigation-testing:2.4.2"
}

object Libraries {
    const val RETROFIT                   = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"
    const val RETROFIT_CONVERTER_GSON    = "com.squareup.retrofit2:converter-gson:${Versions.RETROFIT}"
    const val OKHTTP_LOGGING_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor:${Versions.OKHTTP}"
    const val GLIDE = "com.github.bumptech.glide:glide:${Versions.GLIDE_VER}"
    const val GLIDE_ANNOTATION = "com.github.bumptech.glide:compiler:${Versions.GLIDE_VER}"
}

object Test {
    const val JUNIT         = "junit:junit:${Versions.JUNIT}"
    const val ANDROID_JUNIT = "androidx.test.ext:junit:${Versions.ANDROID_JUNIT}"
    const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:${Versions.ESPRESSO_CORE}"
}

object Paging{
    const val PAGING_RUNTIME = "androidx.paging:paging-runtime:${Versions.PAGING_VERSION}"
    const val PAGING_COMMON  = "androidx.paging:paging-common:${Versions.PAGING_VERSION}"
}