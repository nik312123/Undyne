#!/bin/bash
PATH=$PATH:/bin:/usr/bin:~/bin
export PATH
compileUndyne='cd ~/git/Undyne/Undyne/build/;ant;jar2app "$HOME/Desktop/Undyne - Absolute.jar" "$HOME/Desktop/Undyne – Absolute.app" -i ../images/undyneAbsoluteLogo.icns -v 0.0.0 -s 0.0.0 -n "Undyne – Absolute" -d "Undyne – Absolute" -b "nikunjAaron.undyneAbsolute" -c "© 2017 Nikunj Chawla and Aaron Kandikatla" -j "-Xms512M -Xmx2048M -XX:+UseG1GC -splash:\$APP_ROOT/Contents/Resources/loading.gif";cp ../images/loading.gif ~/Desktop/Undyne\ –\ Absolute.app/Contents/Resources/'
eval $compileUndyne
