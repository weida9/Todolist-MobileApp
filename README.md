# 📝 TodoList Android App

A modern, user-friendly Android Todo List application built with Java and SQLite database. This app allows users to create, manage, and track their daily tasks with a clean and intuitive interface.

## ✨ Features

- **📋 Task Management**: Create, edit, and delete tasks
- **✅ Task Completion**: Mark tasks as completed with visual feedback
- **🎨 Modern UI**: Clean and intuitive Material Design interface
- **💾 Persistent Storage**: Data persists between app sessions using SQLite
- **📱 Responsive Design**: Optimized for various screen sizes
- **🔄 Real-time Updates**: Instant UI updates when tasks are modified
- **📊 Empty State**: Friendly empty state when no tasks exist

## 🛠️ Technology Stack

- **Language**: Java
- **Database**: SQLite (via SQLiteOpenHelper)
- **UI Components**: 
  - RecyclerView for task list
  - ConstraintLayout for responsive layouts
  - CardView for task items
  - Custom TextView-based checkboxes
- **Architecture**: MVC (Model-View-Controller) pattern
- **Minimum SDK**: Android API 24 (Android 7.0)
- **Target SDK**: Latest Android version

## 📱 Screenshots

*[Screenshots would be added here]*

## 🚀 Installation

### Prerequisites

- Android Studio Arctic Fox or later
- Android SDK API 24 or higher
- Java Development Kit (JDK) 8 or higher

### Setup Instructions

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/TodoList.git
   cd TodoList
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Select "Open an existing Android Studio project"
   - Navigate to the cloned TodoList folder and select it

3. **Sync Project**
   - Wait for Gradle sync to complete
   - If prompted, update Gradle version

4. **Run the App**
   - Connect an Android device or start an emulator
   - Click the "Run" button (green play icon) in Android Studio
   - Select your target device and click "OK"

## 📖 Usage

### Adding a Task
1. Open the app
2. Type your task in the input field at the top
3. Tap the "+" button or press Enter
4. Your task will appear in the list below

### Managing Tasks
- **Mark as Complete**: Tap the checkbox next to a task
- **Edit Task**: Tap the edit (pencil) icon
- **Delete Task**: Tap the delete (trash) icon and confirm

### Task Status
- **Incomplete**: Normal text, empty checkbox
- **Complete**: Strikethrough text, checked checkbox, reduced opacity

## 🏗️ Project Architecture

### File Structure
```
TodoList/
├── app/
│   ├── src/main/
│   │   ├── java/com/example/todolist/
│   │   │   ├── MainActivity.java          # Main activity controller
│   │   │   ├── DatabaseHelper.java        # SQLite database operations
│   │   │   ├── Todo.java                  # Data model class
│   │   │   └── TodoAdapter.java           # RecyclerView adapter
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   │   ├── activity_main.xml      # Main activity layout
│   │   │   │   ├── item_todo.xml          # Individual task item layout
│   │   │   │   └── dialog_edit_task.xml   # Edit task dialog layout
│   │   │   ├── drawable/                  # Custom drawables and icons
│   │   │   ├── values/
│   │   │   │   ├── colors.xml             # Color definitions
│   │   │   │   ├── strings.xml            # String resources
│   │   │   │   └── themes.xml             # App themes
│   │   │   └── mipmap/                    # App icons
│   │   └── AndroidManifest.xml            # App manifest
│   └── build.gradle.kts                   # App-level build configuration
├── build.gradle.kts                       # Project-level build configuration
└── README.md                              # This file
```

### Key Components

#### 1. **MainActivity.java**
- Main controller for the application
- Handles user interactions and UI updates
- Implements `TodoAdapter.OnTodoClickListener` for task operations
- Manages database operations through `DatabaseHelper`

#### 2. **DatabaseHelper.java**
- Extends `SQLiteOpenHelper` for database management
- Handles CRUD operations (Create, Read, Update, Delete)
- Manages database schema and versioning
- Provides methods for task persistence

#### 3. **Todo.java**
- Data model class representing a single task
- Contains properties: id, title, completed status, creation timestamp
- Provides getter and setter methods

#### 4. **TodoAdapter.java**
- RecyclerView adapter for displaying task list
- Implements custom checkbox using TextView
- Handles task item interactions
- Manages visual states (completed/incomplete)

#### 5. **Layout Files**
- **activity_main.xml**: Main screen with input field and task list
- **item_todo.xml**: Individual task item with checkbox, title, and action buttons
- **dialog_edit_task.xml**: Dialog for editing existing tasks

## 🗄️ Database Schema

### Table: `todos`
| Column | Type | Description |
|--------|------|-------------|
| `id` | INTEGER PRIMARY KEY AUTOINCREMENT | Unique task identifier |
| `title` | TEXT NOT NULL | Task description |
| `completed` | INTEGER DEFAULT 0 | Completion status (0=incomplete, 1=complete) |
| `created_at` | INTEGER DEFAULT 0 | Timestamp when task was created |

## 🎨 UI/UX Features

### Design Principles
- **Material Design**: Follows Google's Material Design guidelines
- **Accessibility**: Proper content descriptions and touch targets
- **Responsive**: Adapts to different screen sizes and orientations
- **Intuitive**: Clear visual feedback for user actions

### Custom Components
- **TextView-based Checkbox**: Custom implementation for better control and styling
- **Card-based Layout**: Tasks displayed in elevated cards for better visual hierarchy
- **Color-coded Actions**: Different colors for edit (warning) and delete (error) actions

## 🔧 Configuration

### Build Configuration
The project uses Gradle with Kotlin DSL for build configuration:

- **Minimum SDK**: API 24 (Android 7.0)
- **Target SDK**: Latest Android version
- **Compile SDK**: Latest Android version
- **Build Tools**: Latest version

### Dependencies
- **AndroidX**: Core Android libraries
- **RecyclerView**: For displaying task list
- **ConstraintLayout**: For responsive layouts
- **CardView**: For task item styling

## 🧪 Testing

### Unit Tests
- Located in `app/src/test/java/`
- Tests for data model and business logic

### Instrumented Tests
- Located in `app/src/androidTest/java/`
- Tests for UI interactions and database operations

## 🚀 Deployment

### Building Release APK
1. In Android Studio, go to `Build` → `Generate Signed Bundle/APK`
2. Choose `APK` option
3. Create or select a keystore
4. Choose release build variant
5. Build the APK

### Google Play Store
1. Create a Google Play Console account
2. Prepare app metadata (description, screenshots, etc.)
3. Upload the signed APK
4. Complete store listing and release

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 Author

**Your Name**
- GitHub: [@yourusername](https://github.com/yourusername)
- Email: your.email@example.com

## 🙏 Acknowledgments

- Android Developer Documentation
- Material Design Guidelines
- SQLite Documentation
- Android Studio Team

## 📞 Support

If you encounter any issues or have questions:

1. Check the [Issues](../../issues) page for existing problems
2. Create a new issue with detailed description
3. Contact the author directly

---

**Made with ❤️ for Android Developers** 
