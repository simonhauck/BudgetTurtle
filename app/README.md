# Flutter App

This is a flutter app to build standalone apps or weg pages with the server

## Prerequisites

1. Install flutter and make it available on your path according to
   the [flutter installation description](https://docs.flutter.dev/get-started/install)

## Important gradle commands

- ``gradlew prepareEnv``: Before starting development or after pulling use this command. This will pull the current
  openapi.json and will create the flutter binding.
- ``gradlew assemble``: This generates a jar that contains the web page as static resources as well as the release.apk