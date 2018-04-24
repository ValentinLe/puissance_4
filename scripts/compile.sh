#!bin/sh

if [ -d build ]
then
rm -r build/
fi

mkdir build/

cd src/
javac -d ../build */*.java
cd ..