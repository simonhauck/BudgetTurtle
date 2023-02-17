# How to start development

## App

The frontend application is programmed with flutter. To start developing you have
to [install Flutter](https://docs.flutter.dev/get-started/install) and add it to path
variables (This is required for the upcoming gradle commands).

The flutter app uses a generated api client for the server. To get this client locally you have to
execute ```gradlew app:prepareEnv``` in the root project.
