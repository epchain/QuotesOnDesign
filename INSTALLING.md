# Installation

You can build this project using Android Studio or Gradle.

## Prerequisites

You will need:
  * Java JDK 1.8 installation or higher

## Building with Android Studio

This is the simplest installation process. You need to install *Android Studio 3.0*
or higher and follow these steps:
  * *Import* project in Android Studio
  * Build project with *Build -> Make Project*
  * Generate release APK with *Build -> Generate* Signed APK
  * Follow instructions and get *apk-release.apk* file
    in app/build/outputs/apk/release directory

## Building with Gradle

To make release build with Gradle you first need:
  * *Android SDK* installation, either offline or from Android Studio.
    SDK must have compile platform and build tools installed.
    Check needed versions in project root *build.gradle* file.
    You need to tell Gradle where Android SDK is by:
    * ... setting ANDROID_HOME system variable
    * ... or creating **local.properties** file in project root directory with property
      ```
      sdk.dir=<absolute-path-to-android-sdk>
      ```
  * Java *keystore file*.
    Check [Sign Your App](https://developer.android.com/studio/publish/app-signing.html)
    guide for instructions on generating keystore file with JDK tools.
  * *signing.properties* file.
    Create this file at project root directory and write properties for signing build:
      ```
      STORE_FILE=<absolute-path-to-keystore-file>
      STORE_PASSWORD=<keystore-password>
      KEY_ALIAS=<app-key-alias>
      KEY_PASSWORD=<key-alias-password>
      ```

Then open terminal, navigate to project directory and run:
  ```
  gradlew assembleRelease
  ```

Gradle wrapper will automatically download needed Gradle distribution and perform build.
You will find release APK inside *apk* directory of project.

## Installing on device

You can install APK with [ADB](https://developer.android.com/studio/command-line/adb.html)
or by copying file on device and manually installing it.