#!/bin/bash
# Integration test harness backed by Gradle + JUnit

set -euo pipefail

echo "🧪 Running automated test suite via Gradle"
echo "========================================="
echo ""

if [ ! -x "./gradlew" ] && ! command -v gradle >/dev/null 2>&1; then
    echo "❌ Gradle is not installed and no Gradle wrapper was found."
    echo "   Install Gradle or add the wrapper to run the automated tests."
    exit 1
fi

run_cmd() {
    if [ -x "./gradlew" ]; then
        ./gradlew "$@"
    else
        gradle "$@"
    fi
}

run_cmd clean test

echo ""
echo "✅ Gradle tests finished successfully"
echo "========================================="
echo ""
echo "Quick commands:"
echo "  ./gradlew run        # Run the game (if wrapper present)"
echo "  ./gradlew build      # Compile + package"
echo "  ./gradlew test       # Execute test suite"
