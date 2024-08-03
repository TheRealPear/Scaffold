## Contributing to Scaffold

If you wish to submit a feature request or bug report, please do so on the [Issues page](https://github.com/Warzone/Scaffold/issues).

We also welcome code contributions. Follow the steps below to compile the Scaffold plugin.

1. Clone the latest version of the Scaffold repository
2. Using Java 21, compile the plugin with `$ ./gradlew build`
   1. You may need to assign executable permission for the `gradlew` binary depending on your environment (e.g. `$ chmod +x ./gradlew` on UNIX)
3. The plugin JAR can be found at `build/libs/Scaffold-1.0-SNAPSHOT.jar`

Before starting to work on sizeable contributions, we highly recommend you [create an issue](https://github.com/Warzone/Scaffold/issues/new/choose) for the idea or comment on the issue if it already exists. Alternatively, mention it in the `#contributing` channel on [our Discord server](https://warz.one/discord). This is to make sure others don't accidentally work on the same thing as you. If it's a new issue, it is always good to get validation of your idea before spending time on implementation. Of course, it's fine if you don't, but there's no guarantee the contribution will be accepted.