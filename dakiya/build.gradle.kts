import groovy.xml.XmlParser

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    `maven-publish`
}

group = "com.github.Riyaz002"


android {
    namespace = "com.riyaz.dakiya"
    compileSdk = 34

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.firebase.messaging.ktx)
    implementation(libs.androidx.runner)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.kotlin.reflect)
    testImplementation(libs.kotlin.reflect)
}

afterEvaluate {
    publishing{
        publications {
            create<MavenPublication>("release"){
                from(components["release"])
                groupId = "com.github.Riyaz002"
                artifactId = "dakiya"
                version = "0.1.5-alpha"
            }
        }
    }
}

tasks.register("checkMetaData") {
    doLast {
        val manifestFile = file("src/main/AndroidManifest.xml")
        if (!manifestFile.exists()) {
            return@doLast
        }

        // Parse the AndroidManifest.xml with namespace awareness disabled
        val parser = XmlParser()
        parser.setFeature("http://xml.org/sax/features/namespaces", false)
        val manifest = parser.parse(manifestFile)

        // Access the <application> tag as a Node
        val applicationTag = ((manifest as groovy.util.Node).get("application") as List<*>?)?.firstOrNull() ?: return@doLast

        // Cast applicationTag to Node for safe XML traversal
        val applicationNode = applicationTag as groovy.util.Node

        // Check for the <meta-data> tag by name
        val metaDataName = "com.riyaz.dakiya.Notification_Small_Icon"
        val metaDataTag = applicationNode.children()
            .filterIsInstance<groovy.util.Node>()
            .find { it.attribute("android:name") == metaDataName }

        if (metaDataTag == null) error("<meta-data> tag for $metaDataName not found read Dakiya documentation")
    }
}

tasks.named("build") {
    dependsOn("checkMetaData")
}