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

        //Bounding Box For Site
        Rectangle box = new Rectangle(sideLength * 5,  6 * sideLength * 4.05, Color.SEASHELL);
        box.setStroke(Color.SEASHELL.darker());
        box.setStrokeWidth(3);
        this.getChildren().add(box);

        //VBox used to order purchasable pieces automatically
        VBox pieceVBox = new VBox();
        pieceVBox.setLayoutX(sideLength);
        pieceVBox.setLayoutY(sideLength * 0.7);

        pieceVBox.setSpacing(10);
        this.getChildren().add(pieceVBox);

        Piece[] pieces = this.site.getCurrentPieces();

        for (int i = 0; i < pieces.length; i++) {
            var piece = pieces[i];

            if (piece == null) continue;

            var newVisualPiece = new PurchasablePiece(0,sideLength*i*4, piece, sideLength);
            purchasablePieces.add(newVisualPiece);
            pieceVBox.getChildren().add(newVisualPiece);
        }

        this.setLayoutX(x);
        this.setLayoutY(y);

    }
}
