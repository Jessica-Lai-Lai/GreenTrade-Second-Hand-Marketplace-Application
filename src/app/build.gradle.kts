import com.android.build.gradle.internal.dsl.decorator.SupportedPropertyType.Collection.List.type

plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}


android {
    namespace = "com.example.myapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }




    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }



    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    signingConfigs {
        create("release") {
            storeFile = file("key.jks")
            storePassword = "123456"
            keyAlias = "key0"
            keyPassword = "123456"
        }
    }


    android {
        buildTypes {
            getByName("release") {
                isMinifyEnabled = false
                signingConfig = signingConfigs.getByName("release")
                proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            }
        }
    }


    buildFeatures{
        dataBinding = true
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-auth:22.3.1")
    implementation("junit:junit:4.13.2")
    implementation("com.google.firebase:firebase-database:20.3.1")
    implementation("com.google.firebase:firebase-firestore:24.11.1")
    implementation("org.testng:testng:6.9.6")
    implementation("org.testng:testng:6.9.6")

    implementation("androidx.recyclerview:recyclerview-selection:1.1.0")
    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation("com.firebaseui:firebase-ui-database:8.0.0")
    implementation("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0")


    testImplementation("junit:junit:4.13.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.amap.api:navi-3dmap:7.4.0_3dmap7.4.0")
}

// For dataBase


