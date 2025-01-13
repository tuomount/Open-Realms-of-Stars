# Open Realm of Stars Modding

![](src/main/resources/resources/images/oros-logo128.png)

Open Realm of Stars is an open source 4X strategy game. Developed
using Java. Game also supports modding.

First thing game needs to be run at least one so that correct folders are being created to your home folder.

### Built-in Government Editor

Creating custom government is quite simple with editor. Give government new name. Select how ruler is selected, give title names for rulers and finally pick government traits that value is at zero. Then save government and you are done with it.
Now when creating new game, AI realms or your realm may pick that government type.

New governments are saved in following folder `~/.oros/custom/government`

### Built-in Space Race Editor

Creating custom space race is quite simple with editor. Give space race new name in plural and single form.Pick space race traits so that value is at zero. Next tab haves behaviour settings. Those are drop downs where to pick. Note that speech set
can contain also custom speechset if those are available.

Third tab is for appearance. Pick space race image, space ship bridge image, space ship image, theme music and room lights effect for diplomatic screen. Space ship bridge image and space ship image can be custom ones if new graphic sets has been
created. These should be automatically visible in drop down menus.
Space race image can be also custom, pick custom and use "Select image" button to pick image. Note that new custom image should be at following folder `~/.oros/custom/images/spacerace`. 

New space races are saved in following folder `~/.oros/custom/spacerace`


### Speechsets

New speechsets are looked from following folder `~/.oros/custom/speeches`.
These speeches are JSON file with following like content:

```
[
  {
    "id": "HUMAN",
    "data": {
      "TRADE": ["How about this?"],
      "MOVE_FLEET": ["Sorry about the fleet. I'll call it back."],
      "LIKE_GREET": ["Good day! I'll hope we can make business today."],
      "DEFESIVE_PACT": ["Let's make defensive pact together!"],
      "BORDER_WARS": ["Your fleet %1$s has crossed our borders! This means war!"],
      "ASK_PROTECTION": ["If you leave my fleet alone for 5 star years, I'll pay for it."],
      "ASK_MOVE_FLEET": ["Your fleet %1$s has crossed my sector. Please move it away!"],
      "OFFER_SPY_TRADE": ["How about some intelligence trades for 20 star years?"],
      "MAKE_WAR": ["This means war!%1$s"],
      "OFFER_ACCEPTED": ["Glad to make business with you!"],
      "TRADE_EMBARGO": ["How about trade embargo for 20 star years against %1$s?"],
      "DECLINE_WAR": ["This was your last insult! Die!%1$s"],
      "TRADE_EMBARGO_REALM_CHOICE": [],
      "ACKNOWLEDGE_WAR": ["This means war then!"],
      "NOTHING_TO_TRADE": ["Sorry about this call. I thought you would have something to trade."],
      "NEUTRAL_GREET": ["Good day! Let's get to know each others, %1$s."],
      "DECLINE_ANGER": ["I'll take this as an insult!"],
      "FRIENDS_GREET": ["Good day, friend! Let's sit down and talk a bit!"],
      "OFFER_REJECTED": ["I am waiting your counter offer then."],
      "ALLIANCE": ["Let's build alliance together!"],
      "DECLINE": ["I am sorry, I cannot accept this."],
      "TRADE_ALLIANCE": ["Let's build trade alliance together!"],
      "DISLIKE_GREET": ["Think carefully what you do next..."],
      "DEMAND": ["This is take it or leave it deal!"],
      "HATE_GREET": ["This meeting is something we both hate, but let's try it anyway!"],
      "AGREE": ["Glad to make business with you!"],
      "PEACE_OFFER": ["How about peace?"],
      "INSULT_RESPOND": ["Hmm, childish insulting..."],
      "ASK_MOVE_SPY": ["Your espionage fleet %1$s has crossed my sector. Please move it away!"]
    }
  }
]
```

Element id needs to be unique. It is used for identifying speeches. Each line can have multiple choices. If there are more
than one choice then one of them is picked and used during diplomacy. ``%1$s`` is special marker in speeches. It can mean another realm name, space race name, fleet name and so on, so that speech can react what is being discussed.

### Custom Space Race image

Custom space race images are looked from following folder `~/.oros/custom/images/spacerace`.
Images need to be PNG format and around size of 115-130 pixels wide and around 190-200 pixels high. Height is more important than wide, since bridge images have Y coordinate offset and it should not be much different than 200 pixels. Color mode needs to be ARGB.

### Custom Space Ship Images

Custom space ship images consist two files: PNG image file and JSON file.
Image files are in following folder `~/.oros/custom/images/ships`.
JSON files are in following folder `~/.oros/custom/graphset/ships`.

Space ship image files needs to be 320x320 pixel in size with ARGB image.
Each individual ship is sized to 64x64 pixels. So image can fit 5x5 space ship images. Ships in image are from top left corner: Scout, Colony, Destroyer, Probe, Small freighter, small starbase, Corvette, Medium starbase, Medium freighter, Cruiser, Battleship, Privateer, Large privateer, Large freighter, Large starbase, Battlecruiser, Massive freighter, Massive starbase, Capital ship and Artificial planet.

JSON files contain instruction for image to read. JSON file should contain something like this:

```
[
  {
    "id": "RedCentaur",
    "path": "red_centaurships.png",
    "monsters": false,
    "custom": true
  }
]
```

Id element is identify for ships and should be unique. Path is png file under image folder. Monsters flag is only for indicating these images are for monster space race and should be always false. Custom element should be always true, so
that image is search from under custom folder and not inside the JAR file.

### Custom Space Ship Brige Images

Custom space ship bridge images consist two files: PNG image file and JSON file.
Image files are in following folder `~/.oros/custom/images/bridges`.
JSON files are in following folder `~/.oros/custom/graphset/bridges`.

Space ship bridge image files needs to be 711x400 pixel in size with ARGB image.
Transparent color is used for show possible stars/planets or nebula in diplomatic screen.

JSON files contain instruction for image to read. JSON file should contain something like this:

```
[
  {
    "id": "RedCentaur",
    "path": "red_centaur_bridge.png",
    "y-offset": 20,
    "custom": true
  }
]
```

Id element is identify for ships and should be unique. Path is png file under image folder. y-offset how many pixels captain will be placed from middle of the bridge. Positive y-offset set captain image a bit higher and negative set captain bit lower on the screen. Custom element should be always true, so that image is search from under custom folder and not inside the JAR file.
