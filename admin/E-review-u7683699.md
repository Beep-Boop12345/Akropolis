## Code Review

Reviewed by: Oliver Ross, u7683699

Reviewing code written by: Koen Dubrow u7646615
Component: isValidPlacement method in the Board class

### Comments 

Objective: This function is an integral part of checking if a piece can be placed in a certain spot on the board.

Functionality (for end-users and developers):
– does it do what is intended
– edge cases / bugs
– might have to run code for UI changes etc

This method is used in the isMoveValid method in the Akropolis class to run. This method is only concerned with a single move so through using is isValidPlacement this function separates the class based computations from the string based input manipulation of the isMoveValid function. This goes on to prevent an end-user from making an invalid move and ensures that each board state given to the gui is a possible board state. This removes the need for edge-case checks elsewhere in the program.

Tests: Since isValidPlacement is nearly exclusively used in the isMoveValid method it is sufficient to test it using the given tests for isMoveValid.

Complexity: design minimises / encapsulates complexity

Good names: convey information and not too long

Comments: help to understand decisions and the why, not repeating code, appropriately documenting
interfaces

Conformance to project style guide / conventions.
– Formatting at a later stage can destroy attribution information, i.e. git blame become useless
