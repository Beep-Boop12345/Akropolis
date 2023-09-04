package comp1140.ass2.gui;

import comp1140.ass2.Akropolis;
import comp1140.ass2.Stack;

public class PieceStack {
    /*Holds the movable pieces within the stack*/
    public static MovablePiece[] pieceStack;

    public PieceStack() {
        Stack stack = Akropolis.getStack();
        pieceStack = new MovablePiece[stack.getPieceCount()];
        for (int i = 0; i < pieceStack.length; i++) {
            pieceStack[i] = new MovablePiece(stack.getCurrentPieces()[i]);
        }
    }
    /*How the object responds to being Clicked*/
    private void onMousePressed() {

    }
    /*How the object responds to being dragged*/
    private void onMouseDragged() {

    }
    /*How the object responds to being released by mouse*/
    private void onMouseDragStop() {

    }
}
