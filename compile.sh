#!/bin/bash
rm -rf ./bin
mkdir bin

shopt -s globstar
javac -cp "./lib/*" -d bin src/**/main/**/*.java