#!bin/sh

[ -d build ] || mkdir build
cd src/
javac -d ../build */*.java
cd ..