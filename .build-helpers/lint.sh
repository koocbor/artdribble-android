#!/usr/bin/env bash

LINTED=false
REPORT="lint-results"
RESULT=0

if [[ "${CIRCLE_BRANCH}" =~ "develop" ]]; then
    ./gradlew lintDebug
    RESULT="$?"
    LINTED=true
fi

if [[ "${LINTED}" == true ]]; then
    mkdir -p "${CIRCLE_TEST_REPORTS}/Lint"
    cp ${HOME}/artdribble-android/app/build/reports/lint*.html "${CIRCLE_TEST_REPORTS}/Lint/${REPORT}.html"
    cp ${HOME}/artdribble-android/app/build/reports/lint*.xml "${CIRCLE_TEST_REPORTS}/Lint/${REPORT}.xml"
    if [[ "${RESULT}" != "0" ]]; then
        exit 1
    fi
fi