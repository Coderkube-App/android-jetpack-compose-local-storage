# android jetpack compose local storage

A professional, offline-first task management application built with Jetpack Compose and Room Database, following Clean Architecture principles.

## Overview
**android-jetpack-compose-local-storage** is a native Android application designed for efficient personal task management. It replicates the premium experience of its android counterpart, leveraging a local Room database to ensure that all data is accessible and editable even without an internet connection. The project is built with a focus on modularity, testability, and maintainability using the MVVM architectural pattern and Hilt for dependency injection.

## Key Features
- **Secure Authentication**: Email/password login with auto-registration for new users, managed via SharedPreferences for session persistence.
- **Offline Persistence**: Full CRUD operations powered by Room Database (SQLite abstraction).
- **Task Management**:
    - Dashboard with real-time task statistics.
    - Detailed task creation with Priority and Due Date support.
    - Status toggling (Complete/Pending) with auto-cleanup logic.
- **User Scoping**: Data isolation ensuring users only see their own tasks.
- **Responsive UI**: Modern Jetpack Compose interface with Material 3 design, including a custom Splash Screen and Bottom Navigation.

## Architecture
The project follows **Clean Architecture** principles to ensure separation of concerns:
- **Presentation**: UI layer containing Jetpack Compose screens, components, and ViewModels.
- **Domain**: Business logic and core models (`User`, `TaskItem`).
- **Data**: Room entities, DAOs, Database definition, and Repository implementations.
- **DI**: Hilt modules for providing database and repository dependencies.

## Tech Stack
- **Language**: Kotlin
- **UI Framework**: Jetpack Compose (Material 3)
- **Database**: Room Database
- **Dependency Injection**: Hilt
- **State Management**: StateFlow & Kotlin Coroutines

## Getting Started

### Prerequisites
- Android Studio Ladybug or newer
- JDK 17
- Android SDK 36 (targetSdk)

### Installation
1. Clone the repository:
   ```bash
   https://github.com/Coderkube-App/android-jetpack-compose-local-storage.git
   ```
2. Open the project in Android Studio.

### Running the App
1. Sync project with Gradle files.
2. Select an emulator (API 29+) or physical device.
3. Press `Run` to build and install the application.

## Project Structure
```text
app/src/main/java/com/compose/localstorage/
├── data/
│   ├── local/            # Room Database, DAOs, and PrefManager
│   └── repository/       # Repository implementations
├── domain/
│   └── model/            # Business models (User, TaskItem)
├── presentation/
│   ├── auth/             # Login Screen and AuthManager
│   ├── dashboard/        # Dashboard Screen and TaskViewModel
│   ├── navigation/       # NavGraph and Screen definitions
│   └── splash/           # Splash Screen implementation
└── ui/
    └── theme/            # Theme, Color, and Typography
```

## Contribution

Feel free to fork and improve this project! Pull requests are welcome.

---

## License

This project is licensed under the Apache-2.0 License.