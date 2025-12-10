## 📱 My Spot Finder – AndroidApp3

This project is my custom implementation of the GPSLocation tutorial covered in class.
The app retrieves the user’s last known GPS coordinates using the FusedLocationProviderClient API.
🚀 Features

Requests runtime location permissions

Uses FusedLocationProviderClient to get GPS data

Displays latitude and longitude

Handles cases where the emulator does not provide a location

Clean UI with a custom title and button

Fully commented code

## 🛠 Technologies Used

Kotlin

Android Studio

Google Play Services Location API

View-based XML layout

## 📂 Project Structure 
 AndroidApp3/
│
├── app/
│   ├── src/main/
│   │   ├── java/com/example/androidapp3/
│   │   │   ├── MainActivity.kt
│   │   │   ├── FirstFragment.kt
│   │   │   └── SecondFragment.kt
│   │   ├── res/layout/activity_main.xml
│   │   ├── AndroidManifest.xml
│   │   └── ...
│
├── build.gradle.kts
├── settings.gradle.kts
└── README.md

## 🧪 Emulator Note

Android emulators sometimes return null for the last known location.
To test correctly:

Open Extended Controls → Location

Choose a point on the map

Click Set Location

The app will then attempt to retrieve that location.


** 📌 Notes **

The Android emulator sometimes returns null for the last known location.
The app handles this gracefully and displays:
“Unable to get location. Try again.”
(Tested using the Emulator Location tools.)

## 📂 GitHub Repository

https://github.com/samreenbaig-code/AndroidApp3

** 🎓 Assignment Ready**

My project includes:
✔ All GPSLocation features
✔ Error handling
✔ Clean UI
✔ Commented code
✔ GitHub setup with commits

💛 Thank you for reviewing my work!
