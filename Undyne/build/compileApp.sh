#!/bin/bash
PATH=$PATH:/bin:/usr/bin:~/bin
export PATH
compileUndyne='cd ~/git/Undyne/Undyne/build/;ant;jar2app "/Users/$USER/Desktop/Undyne - Absolute.jar" "/Users/$USER/Desktop/Undyne – Absolute.app" -i ../images/undyneAbsoluteLogo.icns -v 0.0.0 -s 0.0.0 -n "Undyne – Absolute" -d "Undyne – Absolute" -b "nikunjAaron.undyneAbsolute" -c "© 2017 Nikunj Chawla and Aaron Kandikatla" -r "/Users/$USER/jdk180151 Folder" -j "-Xmx3072M -XX:+UseConcMarkSweepGC -splash:\$APP_ROOT/Contents/Resources/loading.gif";cp ../images/loading.gif ~/Desktop/Undyne\ –\ Absolute.app/Contents/Resources/'
eval $compileUndyne