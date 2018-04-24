#!bin/sh

if [ ! -d build ]
then
sh scripts/compile.sh
fi

cd build/src/
java gui/Main &
cd ..