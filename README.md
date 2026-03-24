<div align="center">

# 🏠 LiviSync
### Smart Roommate Matching Android App

*Your Ideal Roommate, Simplified.*

![Android](https://img.shields.io/badge/Platform-Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Java](https://img.shields.io/badge/Language-Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Firebase](https://img.shields.io/badge/Backend-Firebase-FFCA28?style=for-the-badge&logo=firebase&logoColor=black)
![Android Studio](https://img.shields.io/badge/IDE-Android%20Studio-3DDC84?style=for-the-badge&logo=androidstudio&logoColor=white)

</div>

---

## 📖 About

**LiviSync** is a native Android application built for university students to find compatible roommates based on lifestyle habits and preferences. Students set up their profile, define their lifestyle preferences, and browse potential roommates — with the app surfacing the most compatible matches first.

---

## 📁 Project Structure

```
com.livisync.app/
│
├── 🚀 Activities
│   ├── SplashActivity.java          # Animated launch screen
│   ├── LoginActivity.java           # Hosts Login + SignUp fragments via ViewPager
│   ├── MainActivity.java            # Main shell with Bottom Navigation
│   ├── ProfileSetupActivity.java    # First-time profile creation flow
│   ├── EditProfileActivity.java     # Edit existing profile details
│   └── PreferencesActivity.java     # Lifestyle preference setup
│
├── 📄 Fragments (Bottom Nav)
│   ├── HomeFragment.java            # Dashboard / matched users feed
│   ├── SearchFragment.java          # Browse & filter potential roommates
│   ├── ChatFragment.java            # Messaging with matched users
│   ├── RequestsFragment.java        # Incoming & outgoing match requests
│   └── ProfileFragment.java         # Own profile view
│
├── 🔐 Auth Fragments
│   ├── LoginFragment.java           # Login form
│   └── SignUpFragment.java          # Registration form
│
└── 🔧 Adapters
    └── LoginPageAdapter.java        # ViewPager2 adapter for Login/SignUp tabs
```

---

## 🖼️ Screens & Layouts

| Layout File | Screen |
|---|---|
| `activity_splash.xml` | Splash / launch screen with animation |
| `activity_login.xml` | Login & Sign Up (tabbed via ViewPager) |
| `fragment_login.xml` | Login form fragment |
| `fragment_sign_up.xml` | Registration form fragment |
| `activity_profile_setup.xml` | First-time profile creation |
| `activity_edit_profile.xml` | Edit profile screen |
| `activity_preferences.xml` | Lifestyle preferences setup |
| `activity_main.xml` | Main activity with bottom navigation |
| `fragment_home.xml` | Home / discover feed |
| `fragment_search.xml` | Search & filter roommates |
| `fragment_chat.xml` | Chat / messaging screen |
| `fragment_requests.xml` | Match requests screen |
| `fragment_profile.xml` | Own profile view |

---

## 🧭 Navigation

Bottom navigation bar with 5 tabs using custom XML drawables:

```
🏠 Home   🔍 Search   💬 Chat   🔔 Notifications   👤 Profile
```

Defined in `res/menu/bottom_nav_menu.xml` with custom icons in `res/drawable/`.

---

## 🚀 Getting Started

### Prerequisites

- Android Studio **Hedgehog (2023.1.1)** or later
- JDK 17+
- A Firebase project with **Authentication** and **Firestore** enabled

### Setup

**1. Clone the repository**
```bash
git clone https://github.com/bissamiftikhar/LiviSync-App.git
cd LiviSync-App
```

**2. Connect Firebase**
- Go to [Firebase Console](https://console.firebase.google.com/) and create a project
- Add an Android app with package name `com.livisync.app`
- Download `google-services.json` and place it inside the `/app` directory

**3. Enable Firebase Services**

In the Firebase Console enable:
- ✅ Authentication → Email / Password
- ✅ Firestore Database

**4. Open in Android Studio**
```
File → Open → Select the LiviSync-App folder
```

**5. Build & Run**
```
Build → Make Project   (Ctrl + F9)
Run   → Run 'app'      (Shift + F10)
```

---
 
## 👥 Team
 
| Member | Role |
|---|---|
| **Awais** | Project Lead & System Architect |
| **Bissam** | Frontend Developer & Requirements Engineer |
| **Muaz** | QA Tester & Android Developer |
