#!bin/sh

cd $(dirname $0)/..

sh scripts/compile.sh

cd build/
java gui/Main
cd ..
