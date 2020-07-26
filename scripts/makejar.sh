#!bin/sh

cd $(dirname $0)/..

if [ -d dist ]
then
rm -r dist/
fi
mkdir dist/

sh scripts/compile.sh
cd build
jar cfe "Puissance 4.jar" gui.Main .
mv "Puissance 4.jar" ../dist/
