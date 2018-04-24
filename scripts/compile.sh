#!bin/sh

if [ ! -d build ]
then
mkdir build/
cd build/
mkdir src/
cd ../
cp -r ressources/ build/
fi

cd src/
javac -d ../build/src */*.java
cd ..