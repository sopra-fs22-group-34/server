# Azul Server
<p align="center">
  <img src="https://github.com/sopra-fs22-group-34/client/blob/master/src/assets/logo.png?raw=true" />
</p>

![example workflow](https://github.com/sopra-fs22-group-34/server/actions/workflows/deploy.yml/badge.svg) 
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=sopra-fs22-group-34_server&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=sopra-fs22-group-34_server) 
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=sopra-fs22-group-34_server&metric=coverage)](https://sonarcloud.io/summary/new_code?id=sopra-fs22-group-34_server)

# Introduction
Azul, famous for winning board game of the year 2018,  is a board game we all love to play, especially with each other. “In Azul players collect sets of similarly colored tiles which they place on their player board. When a row is filled, one of the tiles is moved into a square pattern on the right side of the player board, where it garners points depending on where it is placed in relation to other tiles on the board.” We used to play this game between lessons but then Covid struck and we couldn’t meet up anymore at the university to play. Not living close to each other makes it unfortunately very difficult to plan a date outside of university to play the game together. For this reason we created our own online version of this amazing game to play together wherever and whenever we want to. Also we thought it might be a cool idea to present the game to more peers, as most of them don’t know the game yet which we think is a pity. In our version of the game we implemented multiple automatic features (e.g. the points are calculated automatically) in order for the players to focus more on the fun while playing and the strategic aspects of the game.

# Technologies
We use JPA for database functionality, REST/Spring for RESTful API, the rest is mostly Java.

# High-level Components
## Game
Role: The game state, moves, and players are stored and controlled in this class. 

Relation: Every Lobby has a Game, and the game is invoked and controlled through its lobby

[Link](https://github.com/sopra-fs22-group-34/server/blob/master/src/main/java/ch/uzh/ifi/hase/soprafs22/entity/Game.java)

## Lobby
Role: Allows Users to join a game, and acts as a waiting room. Keeps track of various game settings

Relation: Is the bridge between Users and the Game

[Link](https://github.com/sopra-fs22-group-34/server/blob/master/src/main/java/ch/uzh/ifi/hase/soprafs22/entity/Lobby.java)

## User
Role: Represents a real life person and keeps tracks of their settings (password, etc.)

Relation: Needs to join a Lobby in order to play Game

[Link](https://github.com/sopra-fs22-group-34/server/blob/master/src/main/java/ch/uzh/ifi/hase/soprafs22/entity/User.java)

# Launch & Deployment
## Getting started with Spring Boot

-   Documentation: https://docs.spring.io/spring-boot/docs/current/reference/html/index.html
-   Guides: http://spring.io/guides
  -   Building a RESTful Web Service: http://spring.io/guides/gs/rest-service/
  -   Building REST services with Spring: http://spring.io/guides/tutorials/bookmarks/

## Setup this Template with your IDE of choice

Download your IDE of choice: (e.g., [Eclipse](http://www.eclipse.org/downloads/), [IntelliJ](https://www.jetbrains.com/idea/download/)), [Visual Studio Code](https://code.visualstudio.com/) and make sure Java 15 is installed on your system (for Windows-users, please make sure your JAVA_HOME environment variable is set to the correct version of Java).

1. File -> Open... -> SoPra Server Template
2. Accept to import the project as a `gradle project`

To build right click the `build.gradle` file and choose `Run Build`

### VS Code
The following extensions will help you to run it more easily:
-   `pivotal.vscode-spring-boot`
-   `vscjava.vscode-spring-initializr`
-   `vscjava.vscode-spring-boot-dashboard`
-   `vscjava.vscode-java-pack`
-   `richardwillis.vscode-gradle`

**Note:** You'll need to build the project first with Gradle, just click on the `build` command in the _Gradle Tasks_ extension. Then check the _Spring Boot Dashboard_ extension if it already shows `soprafs22` and hit the play button to start the server. If it doesn't show up, restart VS Code and check again.

## Building with Gradle

You can use the local Gradle Wrapper to build the application.
-   macOS: `./gradlew`
-   Linux: `./gradlew`
-   Windows: `./gradlew.bat`

More Information about [Gradle Wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html) and [Gradle](https://gradle.org/docs/).

### Build

```bash
./gradlew build
```

### Run

```bash
./gradlew bootRun
```

### Test

```bash
./gradlew test
```

### Development Mode

You can start the backend in development mode, this will automatically trigger a new build and reload the application
once the content of a file has been changed and you save the file.

Start two terminal windows and run:

`./gradlew build --continuous`

and in the other one:

`./gradlew bootRun`

If you want to avoid running all tests with every change, use the following command instead:

`./gradlew build --continuous -xtest`

## API Endpoint Testing

### Postman

-   We highly recommend to use [Postman](https://www.getpostman.com) in order to test your API Endpoints.

## Debugging

If something is not working and/or you don't know what is going on. We highly recommend that you use a debugger and step
through the process step-by-step.

To configure a debugger for SpringBoot's Tomcat servlet (i.e. the process you start with `./gradlew bootRun` command),
do the following:

1. Open Tab: **Run**/Edit Configurations
2. Add a new Remote Configuration and name it properly
3. Start the Server in Debug mode: `./gradlew bootRun --debug-jvm`
4. Press `Shift + F9` or the use **Run**/Debug"Name of your task"
5. Set breakpoints in the application where you need it
6. Step through the process one step at a time

## Testing

Have a look here: https://www.baeldung.com/spring-boot-testing

# Roadmap
- Add new game modes with different amounts of colors (e.g. 4 or 6), meaning there would be a 4x4 or 6x6 wall, 4 or 6 stair lines
- Add easy mode where colors can be placed somewhat freely on the wall, requiring only a sudoku-like pattern of not placing the same color twice in the same row or column, but not having to adhere to the strict color pattern as when playing with normal rules
- Add AI opponents so one can play forever alone

# Authors and acknowledgement
Samuel Brander, Ethan Ohlin, Nora Beringer, Robin Meister, Maximilian Hausdorf

# License
Apache License 2.0
