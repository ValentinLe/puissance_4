#!bin/sh

if [ ! -d build ]
then
sh scripts/compile.sh
fi

cd build/
java gui/Main &
cd ..