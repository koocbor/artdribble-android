#
#   Circle CI & securestrings.properties
#
#   Storing sensitive infor in securestrings.properties file locally.  On Circle CI
#   these values are stored in environment variables.  Use this shell script to
#   copy the environment variables into a securestrings.properties file that is
#   used to do the app build.gradle.
#
#   This script must be executed as a pre-process dependency.
#   dependencies:
#       pre:
#           - source .build-helpers/securestringsSetup.sh && copyEnvVarsToSecureStringsProperties
#

#!/usr/bin/env bash

function copyEnvVarsToSecureStringsProperties {
    SECURESTRINGS_PROPERTIES=$HOME"/app/securestrings.properties"
    export SECURESTRINGS_PROPERTIES
    echo "Securestrings Properties should exist at $SECURESTRINGS_PROPERTIES"

    if [ ! -f "$SECURESTRINGS_PROPERTIES" ]; then
        echo "Securestrings Properties does not exist"

        echo "Creating Securestrings Properties file..."
        touch $SECURESTRINGS_PROPERTIES

        echo "Writing properties to securestrings.properties"
        echo "DRIBBLE_API_HOST=$DRIBBLE_API_HOST" >> $SECURESTRINGS_PROPERTIES
        echo "DRIBBLE_CLIENT_ID=$DRIBBLE_CLIENT_ID" >> $SECURESTRINGS_PROPERTIES
        echo "DRIBBLE_CLIENT_SECRET=$DRIBBLE_CLIENT_SECRET" >> $SECURESTRINGS_PROPERTIES
    fi
}