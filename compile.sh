#!/bin/bash
mkdir -p bin
javac -cp "lib/*:src" -d bin $(find src -name "*.java")
echo "Compilation complete."
