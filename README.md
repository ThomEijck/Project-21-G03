# Project-21-G03
 
How to play:
 - Navigate to the src/main/java folder in the cmd (NOTE must be the cmd, the syntax of the command does not work in powershell)
 - Compile using "javac -cp ../../../lib/*;. engineTester/MainGameLoop.java"
 - Run the game using "java -cp ../../../lib/*;. engineTester.MainGameLoop"
 - To the right of the board the dice will be rolled and it will show which piece you are allowed to move
 - You can move pieces by clicking on a piece and afterwards clicking on a highlighted square to move the piece to the selected square
 - Try to capture the king of the enemy to win
 - If you want to restart the game, you can click the red arrow in the top right corner.
 - Enjoy

NOTE:some might get an error referring to libraries used, something like: "Failed to locate library"xxx.dll".
If this is the case one can fix this by downloading the libraries again from https://www.lwjgl.org/
The extract the zip file to the "lib" folder and replace the libraries by dragging the missing libraries to the lib folder
and then replacing the files when asked.
So for example if  lwjgl_stb.dll is missing you drag and drop all the files from the lwjgl-stb folder you just downloaded to the lib folder