#!/bin/sh
set -e
mkdir -p out
javac --enable-preview --release 26 -d out --module-source-path . $(find cons.tail app -name "*.java")
java --enable-preview --module-path out -m app/app.Main
