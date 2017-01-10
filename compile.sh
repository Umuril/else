#!/bin/bash
mkdir -p bin
cp resources/* bin
javac -d bin -cp "lib/*" -sourcepath src src/com/ELSE/Main.java
