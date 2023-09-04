package comp1140.ass2;

import java.util.Arrays;

public class Stack {

    private static int pieceCount;

    private static Piece[] currentPieces;

    public Stack (Piece[] pieces){
        pieceCount = pieces.length;
        currentPieces = pieces;
    }

    public Stack (String gamestate) {
        char[][] constructionSiteIDs = formatPieceIDs(isolateConstructionSite(gamestate));
        char[][] movedPieceIDs = isolateIDs(isolatePLayers(gamestate));
        int[] activePieces = new int[constructionSiteIDs.length + movedPieceIDs.length];
        for (int i = 0; i < constructionSiteIDs.length; i++) {
            activePieces[i] = Integer.parseInt(new String(constructionSiteIDs[i]));
        }
        for (int i = 0; i < movedPieceIDs.length; i++) {
            activePieces[i + constructionSiteIDs.length] = Integer.parseInt(new String(movedPieceIDs[i]));
        }
        Arrays.sort(activePieces);
        int playerCount = gamestate.charAt(0);
        int idCap = 0;
        switch (playerCount) {
            case 2:
                idCap = 37;
                break;
            case 3:
                idCap = 49;
                break;
            case 4:
                idCap = 61;
                break;
        }
        pieceCount = idCap - activePieces.length;
        currentPieces = new Piece[pieceCount];
        int index = 0;
        for (int i = 1; i <= idCap; i++) {
            if (Arrays.binarySearch(activePieces, i) < 0) {
                String id = String.valueOf(i);
                if (id.length() == 1) {
                    id = "0" + id;
                }
                currentPieces[index] = new Piece(id);
                index++;
            }
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

    /*Isolates player strings from string representation*/
    private static String[] isolatePLayers (String gamestate) {
        String[] blocks = gamestate.split(";");
        String[] players = new String[blocks.length - 2];
        for (int i = 0; i < players.length; i++) {
            players[i] = blocks[i + 2];
        }
        return players;
    }

    private static char[][] isolateIDs (String[] players) {
        char[] holdr;
        int amountOfMoves = 0;
        for (int i = 0; i < players.length; i ++) {
            holdr = new char[players[i].length() - 4];
            amountOfMoves = amountOfMoves + players[i].length() - 4;
            players[i].getChars(4,4+holdr.length, holdr,0);
            players[i] = new String(holdr);
        }
        String allMoves = String.join("",players);
        amountOfMoves = Math.floorDiv(amountOfMoves, 10);
        char[][] movedPieces = new char[amountOfMoves][2];
        for (int i =0; i < amountOfMoves; i++) {
            movedPieces[i][0] = allMoves.charAt(i*10);
            movedPieces[i][1] = allMoves.charAt(i*10+1);
        }
        return movedPieces;
    }


    /*Chooses given amount of random tiles for refilling construction site*/
    private Piece[] choose (int numberOfTiles) {

        return new Piece[0];
    }

    public static int getPieceCount() {
        return pieceCount;
    }

    public static Piece[] getCurrentPieces() {
        return currentPieces;
    }
}
