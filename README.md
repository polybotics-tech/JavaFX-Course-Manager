# Course Manager

A JavaFX-based Course Management application built with Maven, following a modular architecture with FXML for UI definition. This project serves as a submission for the COS201 (Computer Programming I) Lab Project from Miva Open University.

## Features

- User authentication (login/register)
- Dashboard for course overview
- Course management (add/edit courses)
- Modern UI with custom styling
- Form validation
- Icons from [Lucide Icons](http://lucide.dev/icons)
- Advanced UI components with ControlsFX and FormsFX
- JSON handling with [Gson](https://github.com/google/gson)

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/cos201/coursemanager/
│   │       ├── controllers/      # UI controllers (FXML controllers)
│   │       ├── models/           # Data models
│   │       ├── services/         # Business logic services
│   │       ├── session/          # Session management
│   │       ├── utils/            # Utility classes (Dialogue, FontLoader, etc.)
│   │       ├── constants/        # Constant values (Views, Styles, Titles, AppInfo)
│   │       ├── MainApplication.java   # Main JavaFX Application class
│   │       └── Launcher.java     # Application launcher
│   │
│   ├── resources/
│   │   └── com/cos201/coursemanager/
│   │       ├── css/              # Stylesheets (auth.css, dashboard.css, etc.)
│   │       ├── fxml/             # FXML UI amrkups and definitions
│   │       ├── data/             # Data storage (JSON files, etc.)
│   │       └── fonts/            # Custom fonts
│   │
│   └── module-info.java          # Java Platform Module System configuration
└
```

## Technologies

- **JavaFX 21** - GUI framework
- **Maven** - Build and dependency management
- **ControlsFX** - Advanced UI controls
- **FormsFX** - Form handling and validation
- **Gson** - JSON processing
- **JUnit 5** - Testing framework

## Module Configuration

The application uses the Java Platform Module System (JPAS). See `src/main/java/module-info.java`:

```java
module com.cos201.coursemanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens com.cos201.coursemanager to javafx.fxml;
    exports com.cos201.coursemanager;
}
```

## Getting Started

### Prerequisites

- JDK 21 or higher
- Maven 3.6+

### Running the Application

```bash
# Using Maven Wrapper (recommended)
./mvnw javafx:run

# Or with Maven directly
mvn javafx:run
```

### Building the Application

```bash
# Compile the project
./mvnw clean compile

# Package the application
./mvnw clean package

# Create a native installer/package
./mvnw jlink:jlink
```

## Development

### Working with FXML

When modifying FXML files:
1. Ensure the `fx:controller` attribute points to the correct controller class
2. Make sure any UI element `fx:id` values match `@FXML` annotated fields in the controller
3. Event handler methods in controllers must match `onAction#methodName` attributes in FXML

### Adding New Features

1. Create new FXML files in `src/main/resources/com/cos201/coursemanager/fxml/`
2. Create corresponding controller classes in `src/main/java/com/cos201/coursemanager/controllers/`
3. Add any new library dependencies to `pom.xml` and `module-info.java`
4. Follow the existing MVC pattern for separation of concerns

## License

This project is for educational purposes as part of a software engineering course offered by Miva Open University.

## Acknowledgments

- JavaFX team for the excellent UI framework
- All open-source library authors whose work makes this project possible