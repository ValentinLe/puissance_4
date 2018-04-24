#!bin/sh

if [ -d build ]
then
rm -r build/
fi

mkdir build/
cd build/
mkdir src/
cd ..
cp -r ressources/ build/

cd src/
javac -d ../build/src */*.java
cd ..