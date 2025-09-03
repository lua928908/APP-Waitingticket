plugins {
    id("com.android.application")
    id("kotlin-android")
    // The Flutter Gradle Plugin must be applied after the Android and Kotlin Gradle plugins.
    id("dev.flutter.flutter-gradle-plugin")
}

android {
    namespace = "com.example.app_waitingticket"
    compileSdk = flutter.compileSdkVersion
    ndkVersion = flutter.ndkVersion
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
        isCoreLibraryDesugaringEnabled = true
    }
    
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_21.toString()
    }
    
    defaultConfig {
        applicationId = "com.example.app_waitingticket"
        minSdk = flutter.minSdkVersion
        targetSdk = flutter.targetSdkVersion
        versionCode = 1
        versionName = "1.0"

        // 앱이 사용할 CPU 라이브러리 종류를 32비트용으로 강제합니다.
//        ndk {
//            abiFilters.addAll(listOf("armeabi-v7a", "x86"))
//        }
    }
    
    packaging {
        jniLibs {
            useLegacyPackaging = true
        }
    }
    
    // sourceSets 블록을 올바른 Kotlin 문법으로 수정합니다.
    sourceSets {
        getByName("main") {
            java.srcDirs("src/main/java")
            kotlin.srcDirs("src/main/kotlin")
            jniLibs.srcDir("src/main/jniLibs")
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    
    // 빌드 과정에서 더 자세한 로그 출력
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.4")
}

flutter {
    source = "../.."
}

