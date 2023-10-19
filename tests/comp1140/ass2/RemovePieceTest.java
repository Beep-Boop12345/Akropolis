package comp1140.ass2;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
public class RemovePieceTest {

    @RepeatedTest(10)
    @DisplayName("order all nulls at the end")
    public void testNullAtEnd() {
        ConstructionSite constructionSite = new ConstructionSite("4hmbtg;0010203040506;P000;P100;P200;P200;P400");
        Piece piece = new Piece(String.valueOf( ((int) Math.random()*6) + 1));
        constructionSite.removePiece(piece);
        Piece[] constructionSitePieces = constructionSite.getCurrentPieces();
        int pieceCount = 0;
        for (Piece i : constructionSitePieces) {
            if(i != null) {
                pieceCount++;
            }
            else {
                break;
            }
        }
        Assertions.assertEquals(pieceCount,5,"should have returned 5 counting from start and stopping " +
                "at first null but returned " + pieceCount);
    }

    @RepeatedTest(10)
    @DisplayName("non null pieces keep the same order")
    public void testOrderPreserved() {
        ConstructionSite constructionSite = new ConstructionSite("4hmbtg;0010203040506;P000;P100;P200;P200;P400");
        Piece piece = new Piece(String.valueOf( ((int) Math.random()*6) + 1));
        constructionSite.removePiece(piece);
        Piece[] constructionSitePieces = constructionSite.getCurrentPieces();
        int previousPiece = 0;
        boolean orderPreserved = true;
        for (Piece i : constructionSitePieces) {
            if(i != null) {
                if (previousPiece < i.getPieceID()) {
                    previousPiece = i.getPieceID();
                } else {
                    orderPreserved = false;
                }
            }
            else {
                break;
            }
        }
        Assertions.assertEquals(orderPreserved,true,"order of non-null pieces should have been " +
                "preserved, but was not");
    }

    @RepeatedTest(10)
    @DisplayName("piece is no longer in constructionSite")
    public void testRemovedCorrectly() {
        ConstructionSite constructionSite = new ConstructionSite("4hmbtg;0010203040506;P000;P100;P200;P200;P400");
        int removePieceID = (((int) Math.random()*6) + 1);
        Piece piece = new Piece(String.valueOf( removePieceID));
        constructionSite.removePiece(piece);
        Piece[] constructionSitePieces = constructionSite.getCurrentPieces();
        boolean removed = true;
        for (Piece i : constructionSitePieces) {
            if(i != null) {
                if(i.getPieceID() == removePieceID) {
                    removed = false;
                }
            }
            else {
                break;
            }
        }
        Assertions.assertEquals(removed,true,"selected piece should have been removed but was not");
    }
}
