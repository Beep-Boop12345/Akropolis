# Test plan

## List of classes

* List below all classes in your implementation that should have unit tests.
* For each class, list methods that can be tested in isolation.
* For each class, if there are conditions on the class' behaviour that cannot
  be tested by calling one method in isolation, give at least one example of
  a test for such a condition.

Do **not** include in your test plan the `Marrakech` class or the predefined
static methods for which we have already provided unit tests.

Test Plan

Classes:

Piece
Methods to test:
- Equals: Construct identical pieces and non-identical pieces and compare
- Constructor(string): Create toString method, initialise a piece instance and get its string representation compare to original string.
- Constructor(Objects/data types): Create toString method, initialise a piece instance and get its string representation compare to expected string.
- Getters (using equals for Tile): Pieces hold an array of Tiles as they correspond to 4 hexagons on the board, use the equals method in the Tile class to check that the constructor correctly creates certain Tiles (and importantly in the correct order as order represents where each tile is)



ConstructionSite
Methods to test:
- Constructor(string)
- Create a toString method, initialise a constructionSite instance and get its string representation compare to original string.
- Constructor(Objects/data types)
- Create toString method, initialise its string representation and get its string representation compare to expected string
- Resupply does not need to be tested as it is the primary method through which resupplyConstructionSite method works.
- isEmpty
- Initialise a constructionSite that is either empty or not and compare to expected value (easy to evaluate by inspection). Test for false positives and false negatives.
- countPieces
- Initialise a constructionSite and compare to expected value (easy to evaluate by inspection).
- order (this method may never actually be used)
- Call method on order ConstructionSite instance, assert that it does not change
- Call method on unordered ConstructionSite instance, assert separately that:
- Pieces occur in the same sequence as before
- All null instances are after the Piece instances
- findPrice
- Initialise a constructionSite and compare to expected value (easy to evaluate by inspection).
- removePiece
- Call with piece not in ConstructionSite instance, assert that there is no change to construction site (compare before toString and aftertoString)
- Call with piece in ConstructionSite, assert separately that:
- There is one less piece in the constructionSite
- The removedPiece is no longer in the constructionSite
- addPiece
- Assert the constructionSite has one more piece then before


Board
- Constructor(string)
- It is not possible to test this like the other constructors as information is lost when the Board is initialised. Ways it can be tested
- createMethod to return array of all the pieceIDs in the board, or that compare this to pieceIDs in the gameString (pieceIDs in the surfaceTiles should be a subset of those in the gameString)
- Assert playerID is correct
- Constructor(objects)
- Create a toString methods
- Compare the Board toString to the toString of the Constructors.
- placePiece
- Place a variety of Pieces (note that these do not have to legal placements)
- Assert
- Height of a placed tile is one more than the height of the tile that used to be there
- New Tiles are in the correct position
- These assertions can be done by comparing Board toString before and after placement, result of this method should be easy enough to solve by hand.
- getTilePosition
- Call on Move with no Null as its Piece (the Piece is not important to this method)
- Assert the correct array for all Rotations on odd and even columns
- This assertion can be done by creating a toString method for hexCoord and comparing with expected value (this can be calculated by inspection/hand easily)
- isValidPlacement
- This is the primary method through which isMoveValid works which is already tested extensively.
- getTile
- Create a Board instance, assert that getTile at a given position matches the tile at that position in surfaceTiles


District
- Nothing here that does not work by inspection

gameState
- updateState
- Test for all for different combinations of booleans on all different original states of playing, assert to be equal to what one would expect to happen next. (Add exception for when game is both not playing and finished, add functionality for default as player 1 turn)
- cycleTurn
- cycleTurn. Test for all combinations of player turn and player count. Assert to next move
- Note that both these tests are able to test these methods in their entirety.


Move
- Constructor(String)
- Create toString method, compare original string with string produced by initialising Move with that string and then getting toString
- Constructor(Objects)
- Create toString method, string produced by initialising Move with that string and then getting toString with what should be produced.


Player
- Constructor(String)
- Create a toString method, initialise a Player instance and get its string representation compare to original (parts of the string not relating to Board).
- Constructor(Objects)
- Create toString method, initialise its string representation and get its string representation compare to expected string


Tile
- Constructor(char,int)
- initialise a Tile instance and get its string representation compare toStringRep of the tile to original. Then do same with getPieceID
- Constructor(Object)
- Create toString method, initialise its string representation and get its string representation (using toStringRep) compare to expected string.Then do same with getPieceID


Stack
- Constructor(String)
- Assert that all the tiles are in the Stack, Boards or ConstructionSite
- Assert that no tile that are in the stack are anywhere else.
- Choose
- Ask about testing randomness
- Assert if not-empty tile is returned
- Assert if empty Null is returned
- Assert if tile return that tile is no longer in the Stack, and pieceCount is one less


VisualBoard
- Methods to test
- heightDarken 
- Test that the tiles in the arrayList with a lower height have a lower brightness value than higher heighted tiles.


Visual Tile
- Methods to test
- updateColour 
- Test that the fill of the tile has a lower brightness value and the star outline even darker
- districtToColour 
- Test the cases of the switch statement so each district is matched to the right colour


HexCoord
Methods to test:
- Equals (Test different objects, null same HexCoord)
- Test identical and non-identical HexCoords, different objects compared with a HexCoord and also null against HexCoord
- Add (Non-negative, negative, 0) 
- Test different cases of different types of integers. Negative positive and zero for both the x,y in isolation and together
- Constructor (Using toString)
- Create multiple HexCoords using the string constructor and assert that is the expected object using toString

Rotation
Methods to test:
- Equals 
- Test identical and non-identical Rotations, different objects compared with a Rotation and also null against Rotation
- getAngle (eg. 720 == 0)
- Test getAngle correctly accounts for angles larger than 360
- (Don’t test negative angles) eg.  Rotation 720 == Rotation 0 
- getRad 
- Test using approximately equal to account for IEE Floating Point Precision and test all 6 possible rotation using a loop to iterate through the rotation enum values


Transform
Methods to test:
- Equals 
- Test identical and non-identical Transform, different objects compared with a Transform and also null against Transform
- add  
- Use equals to test addition of Transform objects, test different cases of integers use similar test cases for transform and test different rotations (and some rotations whose sum is larger than 360)
- Constructor (Using toString)
- Initialise several Transform objects using the string constructor and assert that the created object is as expected using toString to ensure the fields are accurate

