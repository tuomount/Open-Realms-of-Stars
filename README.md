# Open Realm of Stars

![](src/main/resources/resources/images/oros-logo128.png)

Open Realm of Stars is an open source 4X strategy game. Developed
using Java.

### Explore the galaxy
![](ArtModification/screenshots/starmap.png)

Move your fleet in a randomly generated galaxy. Encounter other species, and find new planets to colonize. Explore mysterious space anomalies.

### Manage your own planets
![](ArtModification/screenshots/planet.png)

Manage your planets gain access to better buildings and ships. Population management is key to acquiring more culture, research, metal and production.

### Have diplomacy with other space faring races
![](ArtModification/screenshots/diplomacy.png)

Make friends and enemies while traveling to outer realms. Form trade alliances, and defensive pacts to establish technology trade with other races. Make good diplomatic relations all other realms and win voting for ruler of the galaxy.

### Deep space combat
![](ArtModification/screenshots/combat.png)

Fight enemies with customizable ship designs. Ship components have their own hit points and energy requirements to use them. Components will be rendered useless if hitpoints are depleted or energy requirements are not met. This makes space battles dynamic. A lucky shot by allied ship to an enemies fusion reactor will make them immobile on the battlefield. Fleets with advanced technologies have significant advantages, however luck or number of ships can sway the battle in your favor.

### Conquering the planets
![](ArtModification/screenshots/bombing.png)

Conquer enemy planets. Drop bombs to clear out enemy troops, then deploy your own ground troops to seize control.
Regular space weapons can be used for causing suppression to defending troops.

### Leaders
![](ArtModification/screenshots/leaders.png)

Each realm can have unique characters called leaders. These leaders can act as governors, fleet commanders and rulers. Each of the leader will gain perks which give bonus(negative or positive) on task they are performing. Leaders have limited life span and they will die for old age, but new ones can be replace the old one. Some rulers can have heirs which will eventually replace parents. These leaders can have also internal power struggles in certain government types.

### The history
![](ArtModification/screenshots/history.png)

After a game players are given a quick overview of major galactic events and how other realms did during the game. There will be also history events of all major events that leaders did during their time in the game.

#### The Story
![](ArtModification/screenshots/story.png)

When you start the game, background story will be generated for your realm. At the end of the game you can
see your realm's full story. There is also possible to see other realms' stories.

See more [screenshots](ArtModification/screenshots)

### Features

 * Randomized galaxy with three different starting placements
 * 16 different space races to play
 * 23 different government types to play
 * Any realm can start as an elder realm even human player
 * Random events
 * Planetary events
 * Space anomalies
 * Space pirates
 * Privateering, trade fleets are actual ships which can be privateered
 * Possibility to steal other ships with tractor beam
 * Possibility to tame space monster with tractor beam
 * Diplomatic relations between realms (War, Peace, Trade war, Trade Alliance, Defensive Pact and Alliance)
 * AI remembers how it has been treated and will treat others accordingly
 * Galactic Olympics to influence to other realms
 * Technology can be traded in diplomatic trades
 * If two realms are in alliance they won the game together.
 * Possibility to fake military power in the galactic news (Can be lower or higher)
 * Espionage ships and espionage missions with leaders
 * Leaders acting as rulers, governors and commanders
 * 6 Different technology branches each with 10 levels
 * Each new technology will give either new ship component, ship hull or planetary improvement.
 * All ships are designed based on currently available technology
 * AI does not cheat and each AI is trying to make their own realm as strong as possible
 * 3 Difficulty levels to choose for each AI realm.
 * Turn based space combat
 * Planets can conquered with troops and bombs

## Warning about saved games working on newer version

Since Open Realm of Stars is still under development I cannot guarantee that
old saves will work for new versions. Backwards compatibility will
be broken when adding new features.

## Compiling the Project

Open Realm of Stars can be compiled with Maven.
Install maven and then run command:
`mvn install`

In the target directory a zip file contains all dependecies to run the Game.
Unzip, navigate to the newly extracted path and run `Open-Realm-of-Stars-?.?.?-SNAPSHOT.jar`
with following command `java -jar Open-Realm-of-Stars-?.?.?-SNAPSHOT.jar`. Note that the question
marks need to correspond to the correct build version.

Release builds are done by adding `-Dchangelist= ` (blank intentional)
option when running Maven. Those builds will not have the `-SNAPSHOT` suffix.
If you wish to encode Git commit SHA of current commit to the build name,
add option `-Dsha1="-$(git rev-parse --short HEAD)"`.

## Warning about using non stable version

Compiling non-stable releases is not recommended.
Non-stable versions of Open Realm of Stars may contain game breaking bugs that make the game unplayable.
The game is constantly being developed for and new features may introduce bugs.

If you encounter any critical bugs create a new issue for it. Use the stable release version, or wait for a new fix.
If you're interested in contributing to the project you are welcome to fix it. Contribution guidelines are outlined below.

## Running the game

In order to run Open Realm of Stars Java(Java 11 or newer) is required to be installed.
There are many different OpenJDK which are suitable for this, here is link for one:
[Adoptium](https://adoptium.net/temurin/releases/)

Also if you are using linux, your distro's OpenJDK should work just fine. Instruction for installing OpenJDK in Ubuntu is following:
``sudo apt install openjdk-11-jre``

Open Realm of Stars can be run from the Jar file or running compiled class.
On Windows double clicking the Jar file should be enough if Java has been installed correctly.
It can be also run from terminal or command line with following command:
``java -jar Open-Realm-of-Stars-0.23.0Beta.jar``

### System requirements

Minimum requirements:

* PC with Java JRE11
* 4 GB of RAM
* 200 MB space on disc
* Display at least 1024x768 resolution

Recommended system:

* PC with Java JRE11
* CPU with 2 cores
* 4 GB of RAM
* 200 MB space on disc
* Display with 1920x1080 resolution
* Philips HUE bridge with 1 - 3 color lights

## How to contribute

Before contributing to the project you should play the game first. Understand the underlying game mechanics.
The simplest way to contribute to the project is to test out the game and if you encounter any bug raise an issue on the Git page.
Please check to see if your issue has been posted before making new issue.

If you want to contribute code, fork the project and make the change. Be sure to add any unit tests where needed.
Run the code through JUnits and checkstyle before making a pull request. This can be done using ``mvn verify``.

### Guide lines for making pull requests

 * Pull request should contain single logical change.
 * Coding styling change should be on separate pull requests.
 * Commit messages should be descriptive. If you are fixing an issue start commit line with #ISSUENUMBER.
 * Pull request should not contain merge commits from other branches.
 * Contributors retain their original copyrights. All code contributions must be under GPL2.0.
 * Please try to implement JUnit for your code.

I will try to take a look at pull requests as fast as I can.
If you are adding any new assets take a look at the next section for instructions.

### Contributing assets

Graphics should be licensed with CC-BY-SA and Sounds/Music with CC-BY. Also CC-BY-SA
for music and sounds is okay, but the credits page will need to be updated.
Fonts needs to be licensed with SIL Open Font License.

See more information on [CONTRIBUTING.md](CONTRIBUTING.md)
