# COMP1140 Assignment 2

## Academic Honesty and Integrity

Honesty and integrity are of utmost importance. These goals are *not* at odds with being resourceful and working
collaboratively. You *should* be resourceful, you should collaborate within your team, and you should discuss the
assignment and other aspects of the course with others taking the class. However, *you must never misrepresent the work
of others as your own*. If you have taken ideas from elsewhere or used code sourced from elsewhere, you must say so
with *utmost clarity*. At each stage of the assignment you will be asked to submit a statement of originality, either as
a group or as individuals. This statement is the place for you to declare which ideas or code contained in your
submission were sourced from elsewhere.

Please read the ANU's [official position](http://academichonesty.anu.edu.au/) on academic honesty. If you have any
questions, please ask me.

Carefully review each statement of originality, which you must complete at each stage of this assignment. You can find
originality statements for [D2B](admin/B-originality.yml), [D2C](admin/C-originality.yml)
, [D2D](admin/D-originality-u1234567.yml), [D2E](admin/E-originality.yml), [D2F](admin/F-originality.yml)
and [D2G](admin/G-originality.yml). Ensure that when you complete each stage, a truthful statement is committed and
pushed to your repo.

## Purpose

In this assignment you will work as a group to master a number of major themes of this course,
including software design and implementation,
using development tools such as Git and IntelliJ, and using JavaFX to build a user interface.
As an extension task, you may also explore strategies for writing agents that play games.
Above all, this assignment will emphasize group work;
while you will receive an individual mark for your work based on your contributions to the assignment,
you can only succeed if all members contribute to your group's success.

## Assignment Deliverables

The assignment is worth 35% of your total assessment, and it will be marked out of 35.
So each mark in the assignment corresponds to a mark in your final assessment for the course.
Note that for some stages of the assignment you will get a _group_ mark, and for others you will be _individually_
marked.
The mark breakdown and the due dates are described on
the [deliverables](https://cs.anu.edu.au/courses/comp1110/assessments/deliverables/) page.

Your work will be marked via your tutor accessing Git, so it is essential that you carefully follow instructions for
setting up and maintaining your group repository.
At each deadline you will be marked according to whatever is committed to your repository at the time of the deadline.
You will be assessed on how effectively you use Git as a development tool.

## Problem Description

Your task is to implement in Java, using JavaFX, a board game
called [Akropolis](https://www.hachetteboardgames.com/products/akropolis),
made by Jules Messaud and Pauline D'Étraz, and published by games developer [Gigamic](https://en.gigamic.com/).
[BoardGameGeek](https://boardgamegeek.com/boardgame/357563/akropolis) provides an overview of the game, including
pictures.

## Game Rules

Akropolis is a two to four player game in which each player (or architect) tries to build
the highest-scoring city in ancient Greece.
Players take turns taking tiles from a collection of available tiles, which they then add to their
city. Points are awarded via numerous mechanisms, all of which encourage players to plan their
city carefully and place *Districts* in advantageous areas.

The game ends when there is only one tile remaining in the *Construction Site* (supply). The winner
is the player with the highest score. In the case of a draw, the player with the most *stones* wins.

There is a great video walkthrough of this game on YouTube by
[Meeple University](https://www.youtube.com/watch?v=e73LgPhD0z4), however, please make sure you also
read this document in its entirety.

We have also provided you with the [official rules](assets/rules.pdf). We recommend consulting the official rules if
you need further clarity, however, all the game rules will also be sufficiently explained in this readme.
There is no difference between the official rules and rules used in this assignment.

The image below shows an example game of a finished game of Akropolis between
two players. The Construction Site appears on the left of the photo -
importantly the last tile is never played. The stones each player has appears
as the grey cubes next to their boards.

![Finished Game](assets/images/example_game.jpg)

Also note that tiles can be placed on top of other tiles. This, and rules around placing tiles in general,
will be discussed in more detail later in this readme.

### Plazas and Districts

Each hexagon of a specific colour (without stars) corresponds to an important element of an ancient Greek city.
*Blue* tiles correspond to **Houses**, *yellow* tiles correspond to **Markets**, *red* tiles
correspond to **Barracks**, *purple* tiles correspond to **Temples**, and *green* tiles
correspond to **Gardens**. These types of tiles are called *Districts*.

![Plazas and Districts](assets/images/districts_and_plazas.jpg)

Each District has a corresponding *Plaza*, which is a hexagon of the same colour with a certain number of stars
on it. House Plazas have one star; market, barracks, and temple Plazas have two stars; and garden Plazas have
three stars. Each Plaza type multiplies the score of their corresponding Districts, by the total number of stars of that
colour in the player's city.

#### Placement Conditions

As per the game rules, each District has a specific rule enabling it to be scored at the end of the game:

- **Houses**: You only earn points for Houses that are part of your largest group of adjacent Houses (in the case of a
  tie choose the one which would score the most).
- **Markets**: A Market must not be adjacent to any other Market District.
- **Barracks**: Barracks must be placed on the edge of your city (must have at least one empty adjacent space).
- **Temples**: Temples must be completely surrounded.
- **Gardens**: There are no placement conditions for gardens.

The value for a District meeting the score placement condition is the height of that District on the board. The total
value for each District type is the sum of the values of the individual Districts. No value is scored from Plazas.

#### Scoring Variants

There are optional variant options for the five modes of scoring in the game. The value of Districts meeting the
following criteria is doubled:

- **Houses**: If the group of House Districts has a value of 10 or more.
- **Markets**: If a Market District is adjacent to a Market Plaza.
- **Barracks**: If a Barracks District has three or four adjacent empty spaces.
- **Temples**: If a Temple District is built on a level higher than one.
- **Gardens**: If a Garden District is adjacent to a lake (an empty space that is completely surrounded by tiles).

### Quarries and Stone

Quarries are the remaining type of hexagon in the game. Each Quarry produces one *Stone*
when another tile is placed directly on top of it.

![Quarries and Stone](assets/images/quarries.jpg)

Stone is a valuable resource in this game. Spending Stone gives a player more options in
which tile they can draft from the Construction Site. This is explained in more detail
[later on in this readme](#playing-the-game).

## Game Setup

### City Tiles

Certain city tiles are only used in three or four player games. Consult the Game Encoding
section for how to tell which tiles are used for each version of this game.

The city tiles that are in play are arranged into 11 equal stacks, placed face down. The
number of tiles per stack varies depending on how many players are playing the game:

- 2 players: 3 tiles per stack
- 3 players: 4 tiles per stack
- 4 players: 5 tiles per stack

### Construction Site Setup

After arranging the stacks of city tiles, there will be some city tiles remaining.
These tiles are placed face up on the table, in a line. This line is called the
*Construction Site*.

### Board Setup

Each player takes a starting tile and places it in their city in the centre of the board
as shown in the below image. The coordinate system will be discussed in greater length
[later on](#coordinate-system). The board can be arbitrarily extended in any direction as the
game is played.

![Initial Tile](assets/images/initial_placement.jpg)

### Stones

The first player begins with one stone, and the second player with two stones. In the three and four player versions,
the third and fourth players would start with three and four stones respectively.

## Playing the Game

### Player Turn

Starting with the first player, then moving clockwise, each player takes the following two actions on their turn:

1. Take a Tile
2. Add a Tile to their City

#### Take a Tile

The player chooses a tile from the *Construction Site* they wish to add to their city. The player must pay 1 stone for
each tile that precedes it to the left in the *Construction Site*. The first tile does not cost any stone.

#### Add a Tile to their City

The tile can be played on either the first level, or a higher level.

If the tile is placed on the first level, it must be adjacent to at least one other tile.

If instead the tile is placed on a higher level, every tile below that will be covered must be of the same level, and it
must cover hexes of at least two different tiles.

### End of Turn

At the end of the turn, if the *Construction Site* contains only one tile, a stack of tiles ([as explained earlier in
the readme](#City-Tiles)) are drawn and placed after the remaining tile. Play continues normally. If there were no
stacks of tiles remaining, the game immediately ends.

## End of Game

At the end of the game points are calculated for each district type based off
the [placement conditions](#placement-conditions), noting scoring for any [variants](#scoring-variants) that are
enabled. Each district type is scored by the number of plaza stars multiplied by the value of districts meeting the
placement conditions. A player's score is the sum of points for each district type, plus the number of stones the player
is left with. The winner is the player with the greatest total number of points. In the event of a tie, the tied player
with the most stones wins. Otherwise, the tied players share the victory.

## Tasks and Evaluation Criteria

The assignment is broken down into a set of tasks, and a set of deliverables.
Deliverables are listed on [the deliverables page](https://cs.anu.edu.au/courses/comp1110/assessments/deliverables/) of
the course web site.
There is not a direct correspondence between tasks and deliverables: some of the tasks are included in more than one
deliverable (e.g., tasks 3, 4, 5 etc. are included in
both [D2C](https://cs.anu.edu.au/courses/comp1110/assessments/deliverables/#D2C)
and [D2F](https://cs.anu.edu.au/courses/comp1110/assessments/deliverables/#D2F) meaning you can get marks twice for
completing these tasks), and some of the deliverables require you to do things not in the list of tasks below (e.g.,
integration test and code review).

#### Part One

* Task 1: Read the instructions, fork the assignment repo, and complete admin files.
  Detailed instructions for this task are found
  in [course deliverable D2A](https://cs.anu.edu.au/courses/comp1110/assessments/deliverables/#D2A).
* Task 2: Make an initial *design* for your implementation of the game, including skeleton code.
  Read the description of [deliverable D2B](https://cs.anu.edu.au/courses/comp1110/assessments/deliverables/#D2B) for
  more information about what your design should include.
* Task 3: Begin the implementation of your design by storing appropriate information in instances of your classes. We
  will provide you with a string representation of game states (see
  section [String Representation](#string-representation) below) for testing purposes. Your first implementation step
  should be to internalise this representation, i.e., to create instances of the classes in your design initialised with
  the information from the string representation.
* Task 4: Implement the `isMoveStringWellFormed()` method in the `Akropolis` class.
* Task 5: Implement the `isStateStringWellFormed()` method in the `Akropolis` class.
* Task 6: Implement the `isGameOver()` method in the `Akropolis` class.
* Task 7: Implement the `resupplyConstructionSite()` method in the `Akropolis` class.
* Task 8: Implement the `heightAt()` method in the `Akropolis` class.
* Task 9: Implement the `tileAt()` method in the `Akropolis` class.
* Task 10: Construct a state viewer for your game, allowing you to visualise a game state String.
* Task 11: Implement the `isMoveValid()` method in the `Akropolis` class.

Part one of the assignment culminates
in [deliverable D2C](https://cs.anu.edu.au/courses/comp1110/assessments/deliverables/#D2C).

#### Part Two

In part two, your main objective is to create a working game, using JavaFX to implement a playable graphical version of
the game in a 1200x700 window.

Aside from the window size, the details of exactly how the game appears and how players interact with it are **
intentionally** left up to you. The images in this document are for illustration purposes only.
A good game implementation should be faithful to the game rules, as described above, and have an easy-to-use, intuitive
user interface.
However, the evaluation (marking) of Tasks 13 and 21 below are not all-or-nothing: we will consider different aspects of
the game, and the degree or extent to which each of them has been achieved.

The only **firm** requirements are that:

* you use Java 17 and JavaFX,
* it runs in a 1200x700 window, and
* that it is executable on a standard lab machine from a jar file called `game.jar`.

Your game must successfully run from `game.jar` from within another
user's (i.e. your tutor's) account on a standard lab machine (in
other words, your game must not depend on features not self-contained
within that jar file and the Java runtime).

* Task 12: Implement the `applyMove()` method in the `Akropolis` class.
* Task 13: Implement a basic playable game.
  For the game to be playable, the GUI should display the state of the game,
  allow players to take turns, handle the Construction Site, and update the
  state shown to reflect players' actions. It does not have to implement all
  rules correctly (for example, it may not enforce all rules to do with
  placing tiles, may not correctly enforce stone cost, may not compute final
  scores, etc).
* Task 14: Implement the `generateAllValidMoves()` method in the `Akropolis` class.
* Task 15: Implement the `calculateHouseScores()` method in the `Akropolis` class.
* Task 16: Implement the `calculateMarketScores()` method in the `Akropolis` class.
* Task 17: Implement the `calculateBarracksScores()` method in the `Akropolis` class.
* Task 18: Implement the `calculateTempleScores()` method in the `Akropolis` class.
* Task 19: Implement the `calculateGardenScores()` method in the `Akropolis` class.
* Task 20: Implement the `calculateCompleteScores()` method in the `Akropolis` class.
* Task 21: Implement a fully working game.
  A fully working game extends the basic game by enforcing all game rules,
  ensuring players can in any situation make any move that they are allowed
  to take and none that they shouldn't, ending the game when it is over,
  displaying the score and declaring the correct player as the winner.
* Task 22: Implement a smart computer opponent, and integrate it into your game
  so that a human player/s can choose to play against one or more computer-controlled
  opponents.
  Here, smart means that the AI should incorporate some kind of lookahead/heuristics
  to evaluate which move is the best.
* Task 23: Extend the six scoring methods so that they can also score the variant
  options of the game. Additionally enhance your GUI so that the players can choose
  which variants they wish to play with.
* Task 24: Extend your game in some interesting way.
  Some ideas may include extending the AI for your game even further,
  enhancing your GUI in some meaningful way, or even implementing new variations
  to the game (such as a solo variant which Gigamic has also published)!

There is no requirement to complete the tasks in the order they are numbered, and you are not required to complete all
of them.

### Evaluation Criteria

A detailed breakdown of the marking criteria for each of the deliverables is given on
the [deliverables page](https://cs.anu.edu.au/courses/comp1110/assessments/deliverables/) of the course web site.
In coarse terms, the distribution of assignment marks over different categories is:

* Timely and correct completion of admin tasks and files: 6%
* Healthy use of git and teamwork: 6%
* Viewable, playable, and interesting game (tasks 10, 13, 21-24): 26%
* Appropriate object-oriented design and implementation, and other aspects of code quality: 25%
* Unit-testable tasks (tasks 4-9, 11-12, 14-20, 23): 24%
* Developing and using tests: 6%
* Code review: 3%
* Presentation: 4%

It is essential that you refer to
the [deliverables page](https://cs.anu.edu.au/courses/comp1110/assessments/deliverables/) to understand each of the
deadlines and what is required.
All submitted material be in your team's git repo, and in the *correct* locations, as prescribed
by the [deliverables page](https://cs.anu.edu.au/courses/comp1110/assessments/deliverables/), by each deadline. We will
only mark what is in your team's repo; anything else that you have done but not committed or pushed before the deadline
will not count.
Some parts of your assignment will be marked via tests run through git's continuous integration (CI) framework.

## String Representation

In order for us to provide unit tests for some of the tasks listed above, and thus to give you an indication of whether
you have correctly solved them, we require a consistent representation of game state shared across all groups. This is
done by providing a String representation of the state of the game at any given point.
The String representation is **only for testing purposes**, and should not be the basis of your solution to the
assignment. We expect you to create your own object-oriented representation of the game state, write constructors for
your classes that create instances from the information in the game Strings (task 3), and to use your own objects in
your implementation of the other tasks.
For more guidance on this point, see the section of the FAQ below
titled ["What does 'appropriate use of object-oriented features' mean?"](#what-does-appropriate-use-of-object-oriented-programming-features-mean)
.

The game is encoded by a *state string* which contains information pertaining
to each player's city, the Construction Site, amongst other necessary
information; and, a *pool string* containing information about the tiles that
are able to be played. Moves the players make are encoded by a *move string*

### Coordinate System

![Coordinates](assets/images/coordinates.jpg)

Locations on the board are represented as offsets in the North, East, South and West directions
from the origin of the board. The image uses a single digit to represent these offsets for brevity,
however in the encoding hereafter, two digits are used for the offsets. If the vertical or horizontal
offsets are 0, then South and East are used for the directions respectively. Pay particular attention
to how the North and South offsets are applied to the hexagons when the horizontal offset is odd, due to the tiling
nature of hexagons.

![Initial Tile](assets/images/initial_placement.jpg)

At the start of the game, each player has in their city the starting tile placed in the centre of
the board (`S00E00`), as shown in the image above.

### Pool String (`TILE_POOL`)

The *pool string* is divided into three sections starting with either "2:", "3:" or
"4:". Each section encodes information about a sequence of tiles. The number that
begins each of these sections corresponds to the minimum player count where the tile
is a part of the game. For instance, in a three player game the tiles in play are those
in the "2:" and "3:" sections, but not the "4:" section.

Each section is composed of a sequence of tile types formatted with a two-digit id,
followed by three characters representing what is in bottom left, top left and right of the tile respectively (when the
tile is not rotated). For the purposes of the coordinate system, **the first character represents the origin of the
tile.** Examples relating to this are given in the move string section.
Lowercase letters are used to represent Districts while uppercase represent Plazas. The letters
that are used are:

- 'h': Houses
- 'm': Markets
- 'b': Barracks
- 't': Temples
- 'g': Gardens
- 'q': Quarries (only lowercase is used)

### Move String (`move`)

The *move string* encodes information about a player's move. It contains the ID of the
tile, followed by the coordinates of the origin of the tile, and the tile's rotation.
It is formatted as below:

`move = ID + position + "R" + rotation`

- `ID` is the ID of the tile used by the move
- `position` represents the offset of the origin of the tile from the centre of the
  board
- `R` represents rotation
- `rotation` is a number between 0 and 5 inclusive representing how many turns the tile
  has been rotated in the clockwise direction

#### Position String

Positions on the board are encoded as a non-negative offset from the centre of the board
in the directions North, South, East and West (up, down, right and left respectively).
It is formatted as below, with the `|` symbol representing only one of the two options
are used.

`position = ("N" | "S") + offset + ("E" | "W") + offset`

- `N,S,E,W` represents the direction of the offset
- `offset` is a non-negative two-digit number representing the offset of the position
  on the board from its centre.
- If an offset is 0, then "S" or "E" are used for each direction.

The coordinates [described earlier](#coordinate-system) are displayed again in the below image, noting that only one
digit is used in the image for brevity, while the string representation always uses two digits.

![Coordinates](assets/images/coordinates.jpg)

#### Rotation

The table below shows tile `02` being played to the board, which appears as `02Mbq` in the `TILE_POOL` string. That is,
there is a Market Plaza (the origin of the tile) in the bottom left, a Barracks District in the top left, and a quarry
on the right on the tile. It has been played at position `S01E02` on the board. When rotating the tile, the origin of
the tile remains in the same position, and the other parts are shifted the given number of steps clockwise. In the
table, in the order of top left, top right, bottom left and bottom right, the move strings are `02S01E02R0`
, `02S01E02R1`, `02S01E02R2` and `02S01E02R3` respectively.

<table>
  <tr>
    <td> <img src="assets/images/02S01E02R0.jpg"  alt="02S01E02R0" ></td>
    <td><img src="assets/images/02S01E02R1.jpg" alt="02S01E02R1" ></td>
   </tr> 
   <tr>
    <td> <img src="assets/images/02S01E02R2.jpg"  alt="02S01E02R2" ></td>
    <td><img src="assets/images/02S01E02R3.jpg" alt="02S01E02R3" ></td>
  </tr>
</table>

As a final example, the below image shows the same tile for the move string `02S01E03R5`.

![02S01E03R5](assets/images/02S01E03R5.jpg)

### State String (`gameState`)

The *state string* is composed of multiple statements, each terminated by a semicolon.
The statements are a *settings string* containing the number of players and which
scoring variants are available; a *shared string* containing the player turn and the
tiles in the construction site; and, a series of 2-4 *player strings* containing
information about each player's city. The number of *player strings* is equal to
the number of players in the *settings string*. The string is written as below, with
square brackets indicating that the statement is optional and "+" representing
concatenation.

`state = settings + ";" + shared + ";" + player + ";" + player + ";" + [player + ";"] + [player + ";"]`

- `settings` is the *settings string*
- `shared` is the *shared string*
- `player` is a *player string*
- The player strings are put in ascending order of player ID, the number of player
  strings must be equal to the number of players

#### Settings String

The *settings string* contains the number of players, followed by a representation
of which scoring variants are enabled.

`settings = players + scoring`

- `players` is an integer between 2 and 4 inclusive - the number of players.
- `scoring` is the ordered sequence of either upper or lowercase characters `hmbtg`.
  An uppercase letter means the scoring variant for houses, markets, barracks, temples
  or gardens is activated, otherwise the standard scoring is used.

#### Shared String

The *shared string* contains information shared between the players. It contains
which player's turn it is, as well as the tiles currently available to select
from the construction site in order. It is formatted:

`shared = turn + constructionSite`

- `turn` is an integer between 0 and `p-1` inclusive where `p` is the number of players.
  The player whose turn it is.
- `constructionSite` is a sequence of between 1 and 6 two-digit tile IDs.
  These are the tiles in the Construction Site available to be played (in order of
  stone cost).

#### Player String

Each *player string* displays information about the respective players. It contains
an identifier for the player, followed by the number of stones a player has, and finally
a sequence of *move string* that the player has played. The player string is formatted
below `(x)*` represents that `x` is repeated any number of times (including 0).

`player = "P" + ID + stones + (move)*`

- `ID` is an integer from 0 to `p-1` inclusive where `p` is the number of players.
- `stones` is a two-digit number representing the number of stones that player has
- `move` is a *move string*. The sequence of moves is the moves (in order) that player
  has played

## Marking FAQ and Per-Task Hints

In this section we provide answers to some frequently asked questions about the marking of the
assignment, as well as some specific guidance for some of the more open-ended tasks. This section
may be updated throughout the semester in response to questions from other students, so make sure
to do an upstream pull of this assignment repo at least once per week.

### What does "appropriate use of object-oriented programming features" mean?

This mark is provided for groups who make use of the object-orientation features we teach
throughout the course.

Groups who score well on this criterion tend to be those who are not using raw Strings in their
task code, but are instead converting the provided Strings into objects in their design, and using
the methods and fields contained within those objects to complete the required tasks. High-scoring
groups will only ever convert information from the game String into another datatype in the
constructor of one (or more) of the objects in their design.

Groups who score poorly on this criterion tend to be those who don't fully implement their objects
(e.g. they have a class file, but it is rarely or never actually called in their `Game` or
`Akrpolis` classes), or those who don't use the information inside the objects they create to
complete a given task. For example, groups who create objects, but then still use String
manipulation code to complete the relevant tasks.

Note that this mark is provided totally independently of the correctness of your solution. You
could provide us the most perfect imperative, String-manipulation-driven, object-less code we've
ever seen that passes every test, and you would still get zero on this criterion, which will cap
your mark for the assignment at 75% if it's perfect in every other way.

### Why is the CI failing on the checksum stage?

This could be caused by a couple of different issues, but most likely you need to do an upstream
pull. If you're still getting checksum issues after a member of your group has done an upstream
pull, and then pushed to the repo, post on Ed.

### What does "healthy use of Git" mean?

This criterion primarily relates to the frequency and informativeness of your commits. You should
be committing frequently (ideally every time you stop working on the assignment for the
day/morning/evening, that's the time to make a commit and push your work). These commits should
have informative messages. For example, something like "task 4" is not an acceptable commit
message, but something like "Finished implementing the isMoveStringWellFormed function according
to the docstring" could be acceptable, and "Completed the rotation bound checking in the
isMoveStringWellFormed function" would be even better.

Groups who use the same message on multiple commits that do different things will not receive full
marks in this section, nor will groups which commit large amounts of code at once. At a
**bare minimum** there should be at least one commit made per task completed, but really you
should be committing more often than this.

### Task 3:

For the avoidance of doubt, the following lists the minimal information that we expect to find in
your object-oriented representation of the game by the time your D2C submission is complete for
task 3. You should use appropriate types (including your own classes) for this information. Please
note that this is the bare minimum to collect the task 3 mark in D2C. Depending on the details of
your design, you may need to store additional information in your objects in order to be eligible
for the marks associated with good object-oriented design.

Information about the tiles including:

- The tile ID
- The composition of the tile

From the `settings` component of the State String:

- The number of players
- Which of the five variants the players are playing with

From the `shared` component of the State String:

- The current player turn
- The Construction Site

Derived from each `player` component of the State String:

- The player ID
- The number of stones they have
- A useful representation of the player's board

Again, this list is not prescriptive, and is not designed to constrain your design; marks will be
awarded in the sections for good object-oriented design to groups who find clever ways of storing
multiple of these pieces of information compactly, or in ways that make it easier to program the
game logic. The list simply serves to give you a reference point for what your tutor will be
checking for to confirm you've met the requirements of task 3 of the assignment.

### Viewer

#### Hints on object-oriented design

As mentioned above, it is critically important that you follow an object-oriented design in this
assignment, and your GUI classes are no exception. In order to receive full marks for good
object-oriented design in the D2C deliverable, you should not be reading GUI information from the
String directly, but should instead be reading from your skeleton classes. For example, something
like this is good.

```java
// Some code here...
MyObj newObj=new MyObj(inputString);
int myX=newObj.x;
int myY=newObj.y;
// Code continues, using myX and myY to display a piece in the window
```

But something of this pattern would be considered poor use of object orientation, and thus
**not be acceptable**.

```java
// Some code here...
int myX=Integer.parseInt(inputString.substring(12,16));
int myY=Integer.parseInt(inputString.substring(16,20));
// Code continues, using myX and myY to display a piece in the window
```

Remember that in task 3 you should already have made constructors that convert from the String
representation into your own object-oriented representation, so it is as simple as just creating
a new object to meet this requirement. Doing so will make your code far more understandable, allow
you to use the methods you've made in your backend to better effect, and ensure that you're
demonstrating your understanding of object orientation.

#### Information to be displayed

The information that needs to be displayed by your viewer is similar to the information you
derived into your fields in task 3, that is:

- The tiles available in the Construction Site (the ID of the tile alone is not sufficient)
- The number of stones each player has
- The turn of the current player
- The current player's board (though if you wish to depict all player boards you are welcome to)

The current player's board must show:

- The tile at each position (distinguishing plazas from districts as well as the different tile
  types)
- You must have some way of depicting the height of each tile
- You must have some way of depicting the different tiles on the board (for the rule that if a
  tile is placed on a higher level, it must cover at least two different tiles)

What follows are some steps you may want to follow to help you with implementing the viewer.
You do not have to follow this procedure, but it's here to help if you get stuck:

1. Draw a blank hexagonal board.
2. Fill in the hexagonal board with the different tiles at each position.
3. Find a way to depict the height of each tile/the different tile pieces (text is sufficient for
   now but for the fully working game, you may want to find a neater way to depict this)
4. Draw the Construction Site
5. Display other extraneous information that's not on the board but should be in the window (e.g. information about
   players)

### Game

#### What counts as a "basic playable game"?

A basic working game is one where some of the rules may not have been implemented, but the game
has some functionality that allows it to be visualised. In particular, a basic working game will
allow for a full visualisation of the current state of the game. It will allow for tiles to be
chosen from the Construction Site and played to the board, though not necessarily checking the
move is valid. It will advance the player turn and also resupply the Construction Site when
appropriate. It does not need to show the score. The game should be relatively intuitive to play,
and should not require knowledge of the backend of the game or the String encoding system.

#### What counts as a "fully working game"?

A fully working game is one where all the rules have been implemented. As such, players should be
able to play through their turns with the game enforcing all of the rules until the game ends.
The game should also display the scores of each player and report the winner of the game. The game
should be intuitive to play, using a reasonable user interface that any person who has no knowledge
of java, or the assignment, could pick up and play (for instance, one of your friends or family
members).

### Task 22: Smart Computer Opponent

For this task, we ask you to provide a computer opponent that is doing something more intelligent
than a random computer opponent. There are a variety of ways you can do this, but here are some
hints.

- If you can come up with a way of working out how good a game state is for a particular player,
  then an intelligent AI agent can simply pick the move which will yield the best state after it
  happens.
    - As a starting point, the eventual game score is not a bad criterion for evaluating how good a
      state is (that is, if a state leads to you having a better score than a different state,
      then it is likely better).
    - You should implement some form of lookahead. That is, consider what moves your opponents may
      make, and potentially you on future turns, to help decide what may be the best move.

You should document the logic/strategy that your computer player uses with comments in the code
where appropriate.

There is no specific requirement on how good your computer player has to be (though of course it
should perform somewhat well in the game). Evaluation of this task is a matter of degree, and we
will consider your implementation from the software engineering perspective (i.e., how
well-designed and documented it is) just as much as its performance.

## Legal and Ethical Issues

First, as with any work you do, you must abide by the principles of
[honesty and integrity](http://academichonesty.anu.edu.au). You are
expected to demonstrate honesty and integrity in everything you do.

In addition to those ground rules, you are to follow the rules one
would normally be subject to in a commercial setting. In particular,
you may make use of the works of others under two fundamental
conditions: a) your use of their work must be clearly acknowledged,
and b) your use of their work must be legal (for example, consistent
with any copyright and licensing that applies to the given
material). *Please understand that violation of these rules is a very
serious offence.* However, as long as you abide by these rules, you
are explicitly invited to conduct research and make use of a variety
of sources. You are also given an explicit means with which to declare
your use of other sources (via originality statements you must
complete). It is important to realize that you will be assessed on the
basis of your original contributions to the project. While you won't
be penalized for correctly attributed use of others' ideas, the work
of others will not be considered as part of your
contribution. Therefore, these rules allow you to copy another
student's work entirely if: a) they gave you permission to do so, and
b) you acknowledged that you had done so. Notice, however, that if you
were to do this you would have no original contribution and so would
receive no marks for the assignment (but you would not have broken any
rules either).
