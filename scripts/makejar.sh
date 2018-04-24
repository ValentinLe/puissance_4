#!bin/sh

sh scripts/compile.sh

jar cfe "Puissance 4.jar" src.gui.Main -C build .
