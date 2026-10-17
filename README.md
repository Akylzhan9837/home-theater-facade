# Home Theater Facade Pattern

**Course:** Software Design Patterns  
**Assignment:** #4 — Facade Pattern  
**Language:** Java 17  
**IDE:** IntelliJ IDEA  
**Testing:** JUnit 5

## 1. Project Overview

This project demonstrates the Facade Design Pattern through a home theater system.

A home theater consists of several independent devices. Starting a movie requires multiple operations performed in the correct order.

The `HomeTheaterFacade` class simplifies this process by providing two main methods:

- `watchMovie(String movie)` — prepares the devices and starts the movie.
- `endMovie()` — stops the movie and shuts down the devices.

The client does not need to manage each device individually.

## 2. Facade Pattern Implementation

The project contains five subsystem classes:

| Class | Responsibility |
|---|---|
| `Amplifier` | Controls sound and volume |
| `DvdPlayer` | Starts and stops movie playback |
| `Lights` | Controls room brightness |
| `Projector` | Controls projection and widescreen mode |
| `Screen` | Raises and lowers the screen |

The `HomeTheaterFacade` coordinates these components.

### Without Facade

```java
lights.dim(20);
screen.down();
projector.turnOn();
projector.setWideScreenMode();
amplifier.turnOn();
amplifier.setVolume(50);
dvdPlayer.turnOn();
dvdPlayer.play("Interstellar");
```

### With Facade

```java
homeTheater.watchMovie("Interstellar");
```

The Facade reduces complexity for the client and centralizes the device coordination logic.

## 3. UML Class Diagram

The UML class diagram is available in `home-theater.puml`.

It illustrates the relationship between the client, Facade, subsystem classes, and custom exception.

## 4. Clean Code Principles

The implementation applies principles discussed in Robert C. Martin's *Clean Code*, Chapters 7, 8, and 9.

### Principle 1 — Use Exceptions Rather Than Return Codes (Chapter 7)

Instead of returning error codes, the application uses exceptions to communicate invalid operations.

```java
if (moviePlaying) {
    throw new IllegalStateException(
        "A movie is already playing"
    );
}
```

This keeps error handling separate from the normal execution flow.

### Principle 2 — Provide Context with Exceptions (Chapter 7)

The project defines a custom `HomeTheaterException` that provides information about the failed operation and preserves its original cause.

```java
throw new HomeTheaterException(
    "Failed to start movie: " + movie,
    exception
);
```

This makes errors easier to understand and debug.

### Principle 3 — Don't Return Null / Validate Inputs (Chapter 7)

The Facade validates movie titles before attempting to start any device.

```java
if (movie == null || movie.isBlank()) {
    throw new IllegalArgumentException(
        "Movie title cannot be empty"
    );
}
```

This prevents invalid input from reaching the subsystem.

### Principle 4 — Clean Boundaries (Chapter 8)

The client interacts with the home theater through the Facade rather than coordinating subsystem operations directly.

```java
homeTheater.watchMovie("Interstellar");
homeTheater.endMovie();
```

This creates a clear boundary between high-level client operations and low-level device coordination.

The subsystem classes remain independent and can also be used directly.

### Principle 5 — Single Concept per Test (Chapter 9)

Each unit test focuses on one scenario, such as starting a movie, ending a movie, or handling invalid input.

```java
@Test
void shouldRejectEmptyMovieTitle() {
    HomeTheaterFacade homeTheater = createHomeTheater();

    assertThrows(
        IllegalArgumentException.class,
        () -> homeTheater.watchMovie("")
    );
}
```

Tests use meaningful names and verify observable behavior.

### Principle 6 — F.I.R.S.T. (Chapter 9)

The JUnit tests are designed to be:

- **Fast:** No real hardware or external services are required.
- **Independent:** Each test creates its own theater components.
- **Repeatable:** Tests use deterministic inputs.
- **Self-Validating:** JUnit assertions determine success or failure.
- **Timely:** Tests were introduced during development to verify the implementation.

## 5. Unit Testing

The project includes six JUnit 5 tests:

| Test | Purpose |
|---|---|
| `shouldStartMovieSuccessfully()` | Verifies successful movie startup |
| `shouldEndMovieSuccessfully()` | Verifies correct device shutdown |
| `shouldRejectEmptyMovieTitle()` | Rejects an empty movie title |
| `shouldRejectStartingSecondMovie()` | Prevents starting a second movie |
| `shouldRejectEndingMovieWhenNotPlaying()` | Prevents shutdown when no movie is playing |
| `shouldShutdownDevicesWhenDvdPlayerFails()` | Verifies recovery after DVD player failure |

## 6. How to Run

**Requirements:** Java Development Kit 17 and IntelliJ IDEA.

1. Clone the repository.
2. Open the project in IntelliJ IDEA.
3. Configure the Project SDK to Java 17.
4. Run `com.hometheater.Main`.

The project does not require Maven or Gradle for the main application.

### Running Tests

JUnit 5 must be available on the test module classpath.

Open `HomeTheaterFacadeTest.java` in IntelliJ IDEA and run the test class.

## 7. Conclusion

The Facade Pattern provides a simplified interface to a complex subsystem.

In this project, the pattern improves usability, centralizes coordination logic, and reduces the client's responsibility for managing individual devices.

Clean Code practices improve error handling, readability, and testability.