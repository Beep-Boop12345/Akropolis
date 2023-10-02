## Code Review

Reviewed by: Andrew, Nguyen

Reviewing code written by: Oliver, Ross, u7683699

Component: https://gitlab.cecs.anu.edu.au/u7683699/comp1140-ass2/-/blob/main/src/comp1140/ass2/gui/VisualBoard.java#L14-94

### Comments 

<The code written in the VisualBoard class written by Oliver Ross (u7683699) serves the purpose of creating the visual representation of the board given a Board object as defined in the board class. 

The code maintains proper functionality as it correctly displays the tiles on the board, however, there could be potential issues with edgecases where instances of a visual tile are created with a tile object that has null field instances.

The code currently lacks explicit tests, which is appropriate given
that issues with javaFX testing, but, unit tests might be helpful for ensuring that object creation doesn't break under edgecases.

The code is well designed and efficient. There is a consistent code style that conforms to the project structure (camelCase) and comments are minimal and helpful. The variable and function names are also concise and useful for understanding the code structure. 

The best feature in my opinion is the utilisation of lambda functions for event handling, effectively reducing the amount of code that needs to be written whilst also improving readabiltiy of code.

 >


