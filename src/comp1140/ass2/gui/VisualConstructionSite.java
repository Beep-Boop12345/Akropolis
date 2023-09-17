package comp1140.ass2.gui;

import comp1140.ass2.ConstructionSite;
import comp1140.ass2.Piece;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


import java.util.ArrayList;

public class VisualConstructionSite extends Group {
    /*Holds the pieces that can be purchased*/
    public ArrayList<PurchasablePiece> purchasablePieces;

    /*Holds the backend ConstructionSite*/
    public static ConstructionSite site;

    VisualConstructionSite(double x, double y, ConstructionSite site) {
        this.site = site;
        purchasablePieces = new ArrayList<>();

        double sideLength = 25;

        Rectangle box = new Rectangle(sideLength * 5,  6 * sideLength * 4.05, Color.SEASHELL);
        box.setStroke(Color.SEASHELL.darker());
        box.setStrokeWidth(3);
        this.getChildren().add(box);

        //this.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        VBox pieceHBox = new VBox();
        pieceHBox.setLayoutX(sideLength);
        pieceHBox.setLayoutY(sideLength * 0.7);

        pieceHBox.setSpacing(10);
        this.getChildren().add(pieceHBox);

        Piece[] pieces = this.site.getCurrentPieces();

        var nonNullCount = 0;
        for (Piece piece : pieces) {
            if (piece != null) nonNullCount += 1;
        }
        System.out.println(nonNullCount);

        for (int i = 0; i < pieces.length; i++) {
            var piece = pieces[i];

            if (piece == null) continue;
            //if (nonNullCount <= 4) sideLength = 30;
            //else if (nonNullCount == 5) sideLength = 27;
           // else sideLength = 24;

            var newVisualPiece = new PurchasablePiece(0,sideLength*i*4, piece, sideLength);
            purchasablePieces.add(newVisualPiece);
            pieceHBox.getChildren().add(newVisualPiece);
        }

        this.setLayoutX(x);
        this.setLayoutY(y);

    }
    /*How the constructionSite representation responds to a click*/
    private static void onClick() {

    }
}
