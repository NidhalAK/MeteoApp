
import org.gradle.api.artifacts.dsl.DependencyHandler

object AppDependencies {
    //std lib
    private const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"

    //android ui
    private const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    private const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    private const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    private const val materialDesign = "com.google.android.material:material:${Versions.materialDesign}"
    private const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    private const val lifecycle = "android.arch.lifecycle:extensions:${Versions.lifecycle}"
    private const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"
    private const val activityKtx = "androidx.activity:activity-ktx:${Versions.activityVersion}"
    private const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.fragmentKtxVersion}"

    //Dagger
    private const val daggerHilt = "com.google.dagger:hilt-android:${Versions.dagger}"
    private const val daggerHiltAndCompiler = "com.google.dagger:hilt-android-compiler:${Versions.dagger}"
    private const val hiltLifecycle = "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.hiltLifecycle}"
    private const val hiltCompiler = "androidx.hilt:hilt-compiler:${Versions.hilt}"

    //Networking
    private const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    private const val retrofitMoshiConverter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    private const val retrofitAdapter = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
    private const val retrofitGsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    private const val retrofitScalarConverter = "com.squareup.retrofit2:converter-scalars:${Versions.retrofit}"
    private const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    private const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"

    // Coroutine
    private const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlinxCoroutines}"
    private const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinxCoroutines}"

    //test libs
    private const val junit = "junit:junit:${Versions.junit}"
    private const val extJUnit = "androidx.test.ext:junit:${Versions.extJunit}"
    private const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"

    val appLibraries = arrayListOf<String>().apply {
        add(kotlinStdLib)
        add(coreKtx)
        add(appcompat)
        add(constraintLayout)
        add(materialDesign)
        add(glide)
        add(lifecycle)
        add(recyclerView)
        add(activityKtx)
        add(fragmentKtx)
        add(daggerHilt)
        add(retrofit)
        add(retrofitMoshiConverter)
        add(okhttp)
        add(loggingInterceptor)
        add(coroutines)
        add(coroutinesCore)
        add(retrofitAdapter)
        add(retrofitGsonConverter)
        add(retrofitScalarConverter)
        add(hiltLifecycle)
    }

    val appKapt = arrayListOf<String>().apply {
        add(daggerHiltAndCompiler)
        add(hiltCompiler)
    }

    val androidTestLibraries = arrayListOf<String>().apply {
        add(extJUnit)
        add(espressoCore)
    }

    val testLibraries = arrayListOf<String>().apply {
        add(junit)
    }
}

//util functions for adding the different type dependencies from build.gradle.kts.kts file
fun DependencyHandler.kapt(list: List<String>) {
    list.forEach { dependency ->
        add("kapt", dependency)
    }
}

fun DependencyHandler.implementation(list: List<String>) {
    list.forEach { dependency ->
        add("implementation", dependency)
    }
}

fun DependencyHandler.androidTestImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("androidTestImplementation", dependency)
    }
}

fun DependencyHandler.testImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("testImplementation", dependency)
    }
}