# How to start a new project

## Rename files to match your project

1. Rename the root project name in ``./settings.gradle.kts``
2. Rename plugin files in the buildSrc directory && in all projects3

## Configure Access

1. Create an environment Dockerhub or replace the environment in the build workflows. This determines the secrets and
   location where to push docker images
    1. DOCKER_REGISTRY = The username for dockerhub or a custom registry
    2. DOCKER_USR = The docker username
    3. DOCKER_PSW = Docker password
2. Configure a personal access token to use the release action
    1. Key: PERSONAL_ACCESS_TOKEN Value: The personal access
       token [Tutorial](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/creating-a-personal-access-token)
       with scope repository
3. Configure protection rules
4. Protect tags with format ``v*``