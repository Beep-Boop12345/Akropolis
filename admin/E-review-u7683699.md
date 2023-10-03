## Code Review

Reviewed by: Oliver Ross, u7683699

Reviewing code written by: Koen Dubrow u7646615
Component: isValidPlacement method in the Board class

### Comments 

Objective -- This function is an integral part of checking if a piece can be placed in a certain spot on the board.

Functionality -- This method is used in the isMoveValid method in the Akropolis class to run. This method is only concerned with a single move so through using is isValidPlacement this function separates the class based computations from the string based input manipulation of the isMoveValid function. This goes on to prevent an end-user from making an invalid move and ensures that each board state given to the gui is a possible board state. This removes the need for edge-case checks elsewhere in the program.

Tests -- Since isValidPlacement is nearly exclusively used in the isMoveValid method it is sufficient to test it using the given tests for isMoveValid. However if this function is utilised in other areas outside of the implementation in isMoveValid it may be worth constructing unit tests specifically for it.

Complexity -- Overall the function completes the task that's required from it in a logical way but there are some small improvements that could be made. One good way the method reduces complexity is by breaking the overall tasks into smaller tasks. It follows a process of checking that the move given contains the necessary information, adjacency of pieces, height of pieces and finally if there are two or more pieces being played on top of. This structure makes it clear to any reader what each part of the code is doing. There are also good uses of return statements to prevent uneeded code from running in certain cases preventing potential side effects. This is a good design that attempts to minimise complexity. A possible improvement would be to section each part into its own method. While on a small scale this may not be worth the effort, if similar tests need to be performed in isolation outside of the isValidPlacement function having them separate prevents repeated code. It also makes understanding the function even easier given the submethods are named well. When testing for bugs it also allows for more isolation of code. For example you could have hasAdjacentTile, sameHeight or samePiece methods all of which may be useful elsewhere. As the project stands however it is a good implementation.

Good names -- All variable names are descriptive and clear in purpose. e.g. tilesToBeCovered explicitely states which tiles currently occupy the space being placed on. As a minor note I found the naming of findTilePosition to be a little misleading as it returns an array of multiple tile positions. Maybe findTilePositions would be more useful.

Comments -- Comments are well used to explain each section of the function and its goal. It made understanding each part very easy and provided no misunderstandings.

Style -- The method exhibits good style and is easily readable.

Final Comments -- This is a very good implementation of this method. It is suitably tested, completes its purpose and is easily understood. While some code sections would vary slightly from my own approach to them they are not noticably different to make a significant improvement. My main recommendation is to reduce the size of the method using subfunctions which makes for easier comprehension, testing and resuse of code.
