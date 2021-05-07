#!/bin/bash
cd ..
rm -rf ./bin
mkdir bin

shopt -s globstar
javac -Xlint:unchecked -cp "./lib/*" -d bin src/**/main/**/*.java