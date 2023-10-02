## Code Review

Reviewed by: Koen Dubrow, u7646615

Reviewing code written by: Andrew Nguyen u7330006
Component: isStateStringWellFormed method in Akropolos class

### Comments 

-There are many nitpicks in this review. There are only about 3 real issues or points of improvement, that should be properly 
considered. I have attempted to stress throughout the review but the code is very well-written, and a good example of effective
and stylish code.

Best features of the Code:
Line 201: Reference to method isMoveStringWellFormed
-This is a very good of encapsulation and avoid repetetion
Line 125: use of set
-The way you use set is a key example of where it is best not to use an array and significantly reduces accidental complexity
-The code overall works, and the way that it works it clear, hence the code does its job excellenlty.

Documentation:
Yes. Comments are concise and indicate to reader what check is being performed or why something is occuring.
Nit (nitpick): 
-not all checks are commented with what they are checking (e.g. line 112 to 115). This is not really an issue as
its function is very self-explanatory. In practise I had no problem following the code. 
Good feature:
-I appreciate how you were sure to make comment explaining which part of the stateString was being checked
Nit:
-you introduce maxTileID and seenTileIDs together. While this is reasonable because they are both used to check if a pieceID
is valid, it was the only part of the code I had to think about when reading. I would like to stress that this is the only
part of the code I struggled to read. Your code is incredibly easy to follow otherwise.

Java conventions and encapsulation:
-Variable names are informative.
Nit:
-"components" in isolation is not a particularly informative variable name.
-Method names are not relevant here, they have all been chosen for you.
-Style is consistent. In general the code follow the very simple style of if X the return false. This is again very easy to follow,
and much appreciated.
Nit (very minor):
-At line 144 tileID is declared a substring of constructionSite. This substring is used repeatedly by taking the same substring
again instead of referencing tileID.
I (Issue):
-This method is centered around string manipulation. This is very reasonable as it is checking if a string is valid, and is
unlikely to cause issues later on, as our object orientated backend will not need this method. However this does go against the style
of Java's object orientated paradigm. However if this method were to written by checking that constructors can succeed and that
objects have values in line with the game rules then it could be used to check if a state of Akropolos encoded as objects is in a
valid state, which would be very useful especially for testing. An aproach that could be taken to make the method more object 
orientated could be to first attempt to construct the relevant game objects and then check that they have legal values (like 
each piece only occuring once).
MI (mild issue):
-The method is very long. This could be ammended by creating private methods which check that each component of the stateString is
wellFormed. This has the added benefit of reducing the complexity of methods that are interfaced with.
-I would like to make it clear that I find the codes style to be very good, in consistency and convention (as I understand it).
My only real issue is in the exclusive use of String manipulation, however there is even a good arguement as to why this
is an appropriate decision for this method.

Redundancy:
-As far as I can tell no check is unnesarry.
Nit: 
-The method is very long (100+ lines). This is not really a problem because as a result the method is very easy to follow. 
However, I believe there is some redundancy in your code that could be adressed to bring down the methods length, hence this redundancy section which is really a subsection of Java conventions and encapsulation.
Suggestion:
-It is required multiple times to check that a segment of the string represents a positive number in natural language. In same cases
these regions are as large 3 elements (line 172). An alternative aproach could be try Integer.parseInt.
Point of Improvement:
-To expand on the last comment the check that a region of the gameState contains a two digit integer is repeated thoroughout the code.
The method would benefit for this check to be encapsulated into a private method.
Nit, line 133:
-It has already been checked that turn is positive (shared.charAt(0) would not return a digit if this was the case and
the method would reutrn false). Checking again is completely redundant. That said it is pretty trivial and a bit of a non-issue.

Testing/Edge-cases:
-Method is extremely thorough. It also passes all the pre-made unit tests.
Nit:
-There are some edge casses that I believe would produce a false positive. I will list the below, it is important to keep in mind that 
I read the code with the intention of trying to spot even the most unlikely edge casses.
  -The amount of piece the constructionSite may hold is dependent on how many players ther are. A stateString describing a 2 player
  game with 5 pieces in the constructionSite would pass the test, but it should be illegal. There is an arguement to make that
the method does not need to determine if a state is legal in the rules, just that the string is well formed. However, the
specifications require some aspects of a legal state be tested (e.g. no piece double ups).
  -The code does not check that there are no two playerStrings with the same ID.

Overall comments:
-Code is highly effective, reasonable and logical. It only seems likely to fail on the most minor of edge casses.
