# PlayerActual

Please note:
After downloading a copy of this directory, the java files will have errors.

In order to fix these errors, you must add the external TTS jars into the build path of the project.
To obtain these libraries, please follow this link to the FreeTTS downloads, which provides the functionality of being 
able to run the programs in the PlayerActual repository:

https://sourceforge.net/projects/freetts/files/FreeTTS/FreeTTS%201.2.2/

Choose "freetts-1.2.2-bin.zip" to download, and it will re-direct you to a page that will automically start the download
or have to click on one of the mirrors.

After downloading, you can extract the "freetts-1.2.2-bin.zip" into a folder called "freetts-1.2.2-bin".

Now go to eclipse, and right click on the Enamel project and go to Build Path-->Add External Archvies ..

From there, you would have to navigate to your folder "freetts-1.2.2-bin" or other name that contains the 
extracted files of the "freetts-1.2.2-bin.zip".

You would then navigate to the libraries using the path below:
..\freetts-1.2.2-bin\freetts-1.2\lib

From there, you would add all libaries in the lib file as apart of the external jars, and your program should now be error free!


*DISCLAIMER* ONCE YOU ADD THOSE LIBRARIES TO YOUR PROJECT, PLEASE DO NOT COMMIT/PUSH THE ./classpath FILE ANYMORE.
IF YOU DO SO, THEN YOU WOULD MESS UP THE ENTIRE PROJECT, AS THE ./classpath FILE CONTAINS PATH DIRECTORIES OF YOUR LOCAL
MACHINE TO THE EXTERNAL LIBRARY AND NO OTHER PERSON WILL BE ABLE TO DOWNLOAD/PULL THE PROJECT SINCE IT WILL ONLY WORK FOR
THE PERSON WHO PUSHED THE ./classpath FILE.
