## Code Review

Reviewed by: Oliver Ross, u7683699

Reviewing code written by: Koen Dubrow u7646615
Component: isValidPlacement method in the Board class

### Comments 

Objective: This function is an integral part of checking if a piece can be placed in a certain spot on the board.

Functionality: 

This method is used in the isMoveValid method in the Akropolis class to run. This method is only concerned with a single move so through using is isValidPlacement this function separates the class based computations from the string based input manipulation of the isMoveValid function. This goes on to prevent an end-user from making an invalid move and ensures that each board state given to the gui is a possible board state. This removes the need for edge-case checks elsewhere in the program.

Tests: Since isValidPlacement is nearly exclusively used in the isMoveValid method it is sufficient to test it using the given tests for isMoveValid. However if this function is utilised in other areas outside of the implementation in isMoveValid it may be worth constructing unit tests specifically for it.

Complexity: Overall the function completes the task that's required from it in a logical way but there are some small improvements that could be made. One good way the method reduces complexity is by breaking the overall tasks into smaller tasks. It follows a process of checking that the move given contains the necessary information, adjacency of pieces, height of pieces and finally if there are two or more pieces being played on top of. This structure makes it clear to any reader what each part of the code is doing.

Good names: All variable names are descriptive and clear in purpose. e.g. tilesToBeCovered explicitely states which tiles currently occupy the space being placed on.

Comments: help to understand decisions and the why, not repeating code, appropriately documenting
interfaces

Conformance to project style guide / conventions.
– Formatting at a later stage can destroy attribution information, i.e. git blame become useless
