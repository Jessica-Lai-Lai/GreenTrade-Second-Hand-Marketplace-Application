# GreenTrade - Second-Hand Marketplace Application

[![Android](https://img.shields.io/badge/Android-API%2021+-green.svg)](https://developer.android.com/about/versions)
[![Firebase](https://img.shields.io/badge/Firebase-Authentication%20%7C%20Database-orange.svg)](https://firebase.google.com/)
[![Java](https://img.shields.io/badge/Java-8+-blue.svg)](https://www.oracle.com/java/)

## 📱 Project Overview

GreenTrade is a mobile application platform designed for second-hand item trading, dedicated to promoting environmental protection and sustainable consumption. Users can create personal stores on the platform, publish second-hand goods, and find desired items through powerful search functionality.

### 🌟 Key Features

- **User Authentication System** - Secure registration and login functionality
- **Personal Store Management** - Users can create and manage their own stores
- **Product Publishing** - Support for uploading product information, images, and pricing
- **Smart Search** - Advanced search based on price, distance, and product name
- **Map Integration** - Display locations of nearby purchasable items
- **Red-Black Tree Data Structure** - Efficient data storage and retrieval
- **Firebase Integration** - Cloud data storage and user authentication

## 🏗️ Technical Architecture

### Core Technology Stack
- **Development Language**: Java
- **Platform**: Android (API 21+)
- **Backend Services**: Firebase Authentication & Realtime Database
- **Build Tool**: Gradle (Kotlin DSL)
- **Design Patterns**: Singleton Pattern, Template Pattern, Adapter Pattern

### Data Structures
- **Red-Black Tree** - For efficient storage and search of user and product data
- **ArrayList** - Store search results and frontend display data
- **HashSet** - Store unique IDs of user products and favorites

### Design Patterns
- **Singleton Pattern** - SearchManager class, ensuring globally unique search manager
- **Template Pattern** - Tree abstract class, defining basic operations for tree structures
- **Adapter Pattern** - Connecting Android UI components with backend data

## 📋 Feature Modules

### 1. User Management
- User registration and login
- Personal profile management
- User authentication (Firebase)

### 2. Product Management
- Product information upload
- Product detail display
- Price and location information

### 3. Search System
- Search based on product name
- Price range filtering
- Distance range filtering
- Complex condition search

### 4. Map Functionality
- Display nearby product locations
- Geographic location filtering
- Interactive map browsing

### 5. Store Management
- Personal store creation
- Product list management
- Store information display

## 🚀 Installation and Setup

### Requirements
- Android Studio Arctic Fox or higher
- Android SDK API 21+
- Java 8 or higher
- Google Play Services

### Installation Steps

1. **Clone the Project**
   ```bash
   git clone [repository-url]
   cd GreenTrade-Second-Hand-Marketplace-Application
   ```

2. **Configure Firebase**
   - Create a new project in [Firebase Console](https://console.firebase.google.com/)
   - Download the `google-services.json` file
   - Place the file in the `src/app/` directory

3. **Build the Project**
   ```bash
   ./gradlew build
   ```

4. **Run the Application**
   - Open the project in Android Studio
   - Connect an Android device or start an emulator
   - Click the run button

### Test Accounts
For testing convenience, the application provides the following test accounts:
- **Username**: comp2100@anu.edu.au | **Password**: comp2100
- **Username**: comp6442@anu.edu.au | **Password**: comp6442

## 📁 Project Structure

```
src/app/src/main/java/com/example/myapplication/
├── Java/                    # Core data classes
│   ├── Goods.java          # Product class
│   ├── User.java           # User class
│   ├── RedBlackTree.java   # Red-Black tree implementation
│   ├── RBNode.java         # Red-Black tree node
│   └── Pair.java           # Coordinate pair class
├── Search/                  # Search functionality module
│   ├── SearchManager.java  # Search manager
│   ├── Search.java         # Search class
│   └── FirebaseHandler.java # Firebase data processing
├── TokenizerParser/         # Lexical analyzer and parser
│   ├── Tokenizer.java      # Lexical analyzer
│   ├── Parser.java         # Parser
│   └── Exp.java            # Expression class
├── ui/adapter/             # UI adapters
│   ├── StoreAdapter.java   # Store adapter
│   └── StoreUserAdapter.java # User store adapter
├── MainActivity.java       # Main activity
├── Login.java             # Login activity
├── Register.java          # Registration activity
├── SearchActivity.java    # Search activity
├── MapActivity.java       # Map activity
└── UploadInformation.java # Information upload activity
```

## 🧪 Testing

The project includes a comprehensive unit test suite:

```bash
# Run all tests
./gradlew test

# Run specific tests
./gradlew test --tests GoodsTest
./gradlew test --tests RedBlackTreeTest
./gradlew test --tests SearchManagerTest
```

### Test Coverage
- Product class functionality tests
- User class functionality tests
- Red-Black tree data structure tests
- Search functionality tests
- Lexical analyzer and parser tests

## 🔧 Development Guidelines

### Code Standards
- Follow Java naming conventions
- Use meaningful variable and method names
- Add appropriate comments and documentation

### Contribution Guidelines
1. Fork the project
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📊 Performance Characteristics

- **Time Complexity**: Red-Black tree operations O(log n)
- **Space Complexity**: Efficient memory usage
- **Search Performance**: Support for complex query conditions
- **Data Persistence**: Firebase Realtime Database

## 🤝 Team Contributions

### Team Members
- **Jinqiao Jiang** - Data structures and Red-Black tree implementation
- **Jin Zhang** - Search functionality and map integration
- **Xinyue Huang** - Product and user class design
- **Dong-Jhang Wu** - Lexical analyzer and parser
- **Jessica Lai** - UI design and user authentication

### Contribution Ratio
Each team member has a 20% contribution, demonstrating excellent team collaboration.

## 📄 License

This project is an academic project for learning and research purposes only.

## 📞 Contact

For questions or suggestions, please contact the development team.

---

**GreenTrade** - Making second-hand trading more eco-friendly, making communities more sustainable 🌱


