#!/bin/sh
set -e

REQUIRED=27

if ! command -v java >/dev/null 2>&1 || ! command -v javac >/dev/null 2>&1; then
  echo "Error: java/javac not found. Java $REQUIRED is required." >&2
  exit 1
fi

JAVA_VERSION=$(java -XshowSettings:properties -version 2>&1 | awk '/java.specification.version/ {print $3}')
JAVAC_VERSION=$(javac -version 2>&1 | awk '{print $2}' | cut -d. -f1)

if [ "$JAVA_VERSION" != "$REQUIRED" ] || [ "$JAVAC_VERSION" != "$REQUIRED" ]; then
  echo "Error: Java $REQUIRED is required (found java $JAVA_VERSION, javac $JAVAC_VERSION)." >&2
  echo "Put a JDK $REQUIRED first on your PATH (macOS: export PATH=\"\$(/usr/libexec/java_home -v $REQUIRED)/bin:\$PATH\")." >&2
  exit 1
fi

rm -rf out
javac --release $REQUIRED -d out --module-source-path . $(find cons.list app -name "*.java")
java --module-path out -m app/app.Main
