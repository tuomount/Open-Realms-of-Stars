[![Build Status](https://travis-ci.org/tuomount/Open-Realms-of-Stars.svg?branch=master)](https://travis-ci.org/tuomount/Open-Realms-of-Stars)
[![Coverage Status](https://coveralls.io/repos/github/tuomount/Open-Realms-of-Stars/badge.svg?branch=master)](https://coveralls.io/github/tuomount/Open-Realms-of-Stars?branch=master)

# Open Realm of Stars


Open Realm of Stars is Open source 4X strategy game in stars. It is developed 
with Java language and it is should run almost every where Swing and Java 7 are 
available.

### Explore the galaxy
![](https://github.com/tuomount/Open-Realms-of-Stars/raw/master/ArtModification/screenshots/starmap.png)

Move your fleet in randomly generated galaxy. Encouter other species. Find new planets to colonize.

### Manage your own planets
![](https://github.com/tuomount/Open-Realms-of-Stars/raw/master/ArtModification/screenshots/planet.png)

Manage your planets to build better buildings and ships. Assign population to make more culture, research,
mine metal and production.

### Have diplomacy with other space faring races
![](https://github.com/tuomount/Open-Realms-of-Stars/raw/master/ArtModification/screenshots/diplomacy.png)

Make friends and enemies while encounter other realms. Make trade alliance, defensive pacts. Trade new
technology with each others.

### Deep space combat
![](https://github.com/tuomount/Open-Realms-of-Stars/raw/master/ArtModification/screenshots/combat.png)

Fight with enemies your own ship designs. Each ship's component has own hit points and energy
requirements. If either of those fails component stops working. This makes space combat very interesting.
Lucky shot to Fusion reactors might cause enemy ship floating around the battle field.
Destroying weapons is different thing than destroying scanner or shields first. Usually fleets with better
technologies rule, but luck or just number of ships can make a difference in combat.

### Conquering the planets
![](https://github.com/tuomount/Open-Realms-of-Stars/raw/master/ArtModification/screenshots/bombing.png)

Conquer enemy planets. First drop bombs to get down enemy ground troops and finally conquer the planet
with your own ground troops.

### The history
![](https://github.com/tuomount/Open-Realms-of-Stars/raw/master/ArtModification/screenshots/history.png)

When game ends you get quick review most important events in the galaxy and see how other realms did
in the whole game.

See more [screenshots](https://github.com/tuomount/Open-Realms-of-Stars/tree/master/ArtModification/screenshots)

## Warning about saved games working on newer version

Since game is under heavy development I cannot guarantee that
saved games work always with newer version. Backwards compatible is
broken when adding new features to the game.

## Compiling the Project

Open Realm of Stars can be compiled with Maven.
Install maven and then run command:
``mvn install``

In target directory there is zip file containing all dependecies to run the Game.
Unzip and go to directory you just extracted and run ``Open-Realm-of-Stars-?.?.?.jar``
with following command ``java -jar Open-Realm-of-Stars-?.?.?.jar``. Note that question
marks needs to point correct build version.

Git project also contains Eclipse project which should automatically compile it.
All java files under src folder needs to be compiled. Even when compiling Eclipse
run first maven command (``mvn install``) to fetch all the dependencies.

## Warning about using non stable version

If you compile non stable version it might be that version contains bugs that prevent
actually playing the game. This is because game is still under development and newly added
feature or enchaments might broke something and in worst cast prevent playing the game.

If this happens please make an issue about it. After that use either stable release version
or wait for fix. Or if you are interested you are also more than welcome to fix it.
See the contribute instructions below.

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
pull request back to me.

### Guide lines for making pull requests

 * Pull request should contain single logical change. 
 * Coding styling change should be on separate pull requests.
 * Commit messages should be descriptive. If you are fixing an issue start commit line with #ISSUENUMBER.
 * Pull request should not contain merge commits from other branches.
 * Contributors retain their original copyrights. All code contributions must be under GPL2.0.
 
I'll try to looking these as fast as I can. When
added new assets see paragram one below more instructions. 

### Contributing assets

Graphics should be licensed with CC-BY-SA and Sounds/Music with CC-BY. Also CC-BY-SA
for music and sounds is okay, but the credits page must be then changed.
Fonts needs to be licensed with SIL Open Font License.

