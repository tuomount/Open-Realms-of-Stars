[![Build Status](https://travis-ci.org/tuomount/Open-Realms-of-Stars.svg?branch=master)](https://travis-ci.org/tuomount/Open-Realms-of-Stars)
[![Coverage Status](https://coveralls.io/repos/github/tuomount/Open-Realms-of-Stars/badge.svg?branch=master)](https://coveralls.io/github/tuomount/Open-Realms-of-Stars?branch=master)

# Open Realm of Stars

Open Realm of Stars is Open source 4X strategy game in stars. It is developed 
with Java language and it is should run almost every where Swing and Java 7 are 
available.

## Compiling the Project

Open Realm of Stars can be compiled with Maven.
Install maven and then run command:
``mvn install``

Runnable Jar file can then located from target directory.

Git project also contains Eclipse project which should automatically compile it.
All java files under src folder needs to be compiled.

## Running the game

Game can be run from Jar file or running compiled class.
Main method is in src/org.openRealmOfStars.game.Game class. Currently this
is the only main class in project.

## How to contribute

If you wish to contribute the project you should first try it out. See a bit
how game mechanics works. Easiest way to contribute is simple play the game
and if you find a bug just make a bug to Project's GitHub page. Before posting
new bug check that there are no duplicates.

If you want to add new code fork the project make the change. Run JUnits and 
checkstyle before committing. These can be run with ``mvn verify``. After this make
pull request back to me. I'll try to looking these as fast as I can. When
added new assets see paragram one below more instructions. 

### Contributing assets

Graphics should be licensed with CC-BY-SA and Sounds/Music with CC-BY. Also CC-BY-SA
for music and sounds is okay, but the credits page must be then changed.
Fonts needs to be licensed with SIL Open Font License.

