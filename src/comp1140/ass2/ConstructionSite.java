package comp1140.ass2;

import java.lang.Math;

public class ConstructionSite {

    public static int size;

    public static Piece[] currentPieces;

    public ConstructionSite (int size, Piece[] initialPieces) {
        //An exception could go here
        this.size = size;
        this.currentPieces = initialPieces;
    }
    /*Constructor from string input*/
    public ConstructionSite (String gamestate) {
        char[][] pieceIDs = formatPieceIDs(isolateConstructionSite(gamestate));
        this.size = pieceIDs.length;
        this.currentPieces = new Piece[size];
        for (int i = 0; i < size; i++) {
            this.currentPieces[i] = new Piece(new String(pieceIDs[i]));
        }
    }

    /*Produces char array just of piece ids in the construction site
    * @Param String gamestate
    * @Return Char[] pieceIDs the IDs of the pieces in the constructions site*/
    private static char[] isolateConstructionSite (String gamestate) {
        String shared = gamestate.split(";")[1];
        char[] pieceIDs = new char[shared.length() - 1];
        for (int i = 1; i < shared.length(); i++) {
            pieceIDs[i] = shared.charAt(i);
        }
        return pieceIDs;
    }

    /*Groups numbers part of the same Piece ID together into a 2 dimensional array*/
    private static char[][] formatPieceIDs (char[] pieceIDs) {
        char[][] formatedPieceIDs = new char[pieceIDs.length/2][2];
        for (int i = 0; i < pieceIDs.length; i++) {
            formatedPieceIDs[(Math.floorDiv(i,2))][i%2] = pieceIDs[i];
        }
        return formatedPieceIDs;
    }
    /*Removes an amount of Pieces from the construction site from the left
    * @Param nuber of pieces to be removed*/
    private static void removePieces(int numberOfPieces) {

    }

    /*Adds pieces to the construction Site
    * @Param pieces to be added*/
    private static void addPieces(Piece[] piecesToAdd) {

    }
}
