#!bin/sh

sh scripts/compile.sh
cd build
jar cfe "Puissance 4.jar" gui.Main .
mv "Puissance 4.jar" ..
