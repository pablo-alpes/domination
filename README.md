# Domination V1.0
Juan Pablo Miranda Arismendi - 07 July 2024.

## Requirements
* Java 21 installed in your Operating System (OS) and with the right environment variable settings.
* Maven for dependency management (in case you want to recompile the program).

## How to run the game
Assuming java is installed, for any OS you need to decide where to storage the game.

### For macOS:
* Either using Finder or by command line you need unzip the program in a target directory.
* Once done, you open the 'Terminal' application in the launchpad and you navigate where the program is located (using cd).
* Once there, you do:
  * java -cp target/domination-1.0.jar Domination
* The game will start.

###  For windows:
* Unzip the file using winzip or any other tool in the destination folder.
* Then you initiate cmd or powershell to run a terminal of command line.
* In the command line and in the destination folder you do:
  * java -cp target/domination-1.0.jar Domination
* The game will start.

If you don't have installed java in your machine, you can download it from here.
To check your java version you can do in the command line java --version.

## Game instructions 
### Human turn:
* By default, the game gives you the first turn.
* Once you start, the game will prompt your name and your current ownerships will be displayed.
* The attack commences and it will prompt you where to attack from and to decide a target border. 
  * Please be mindful on the input the games and respect the exact names, including caps.
* The valid targets will be selected by the program, but of course try to maximize your chances.
* Once your attack done, you will see the dices results for each player and the troops maintained or lost.
  * Note: The dices and the number of armies are automatically selected chosen to attack.
* If a takeover place, it will appear in the next screen once the turn of the machine will kick in.

### Machine turn:
  * The machine take it turn, launch the dices and it will again show the results of the battle with the new board displayed.

### Troops Reinforcement:
  * You have the right to receive troops reinforcements given your country and continents ownership. This will happen everyturn and it will be informed. 
  * These armies will be destined to a random country of your ownership. 
  * Same will happen for your opponent.

### End game:
* The game finishes once a player does not have any armies, or it draws if none of the players can further attack.

## Map selection / change
* To select any map you can download them from Domination website: https://domination.sourceforge.io/getmaps.shtml
* You need to recompile the program and just edit the constants file where the path to the map is stated.
* The new map needs to be stored in the resources folder, and then /country.

## Maven recompilation
In case you want to recompile the program you need to run:
* mvn clean install 
