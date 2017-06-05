#!/usr/bin/env bash

if [[ "${CIRCLE_BRANCH}" =~ "develop" ]]; then
    ./gradlew jacocoDebugReport coveralls
fi