package comp1140.ass2.gui;

import comp1140.ass2.Akropolis;
import comp1140.ass2.ConstructionSite;
import comp1140.ass2.Piece;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


import java.util.ArrayList;

/**
 * GUI class that represents any backend construction site class
 * @author u7683699
 * @author u7646615
 */
public class VisualConstructionSite extends Group {
    /*Holds the pieces that can be purchased*/
    public ArrayList<PurchasablePiece> purchasablePieces;

    /*Holds the backend ConstructionSite*/
    public static ConstructionSite site;

    /**
     * Constructor
     * @author u7683699
     *
     * @param x x-coordinate
     * @param y y-coordinate
     * @param site the site that it represents
     * @param viewer that is displaying it
     * @param akropolis game object that it is representing*/
    VisualConstructionSite(double x, double y, ConstructionSite site, Viewer viewer, Akropolis akropolis) {
        this.site = site;
        purchasablePieces = new ArrayList<>();

        double sideLength = 25;

        //Bounding Box For Site
        Rectangle box = new Rectangle(sideLength * 5,  6 * sideLength * 4.05, Color.SEASHELL);
        box.setStroke(Color.SEASHELL.darker());
        box.setStrokeWidth(3);
        this.getChildren().add(box);

        Piece[] pieces = this.site.getCurrentPieces();
        for (int i = 0; i < pieces.length; i++) {
            if (pieces[i] == null) {
                continue;
            }
            PurchasablePiece nextVisualPiece = new PurchasablePiece(1.8*sideLength,
                    sideLength*i*4+sideLength * 3.5,
                    pieces[i],
                    sideLength, true, viewer, akropolis);
            purchasablePieces.add(nextVisualPiece);
            this.getChildren().add(nextVisualPiece);
        }


        this.setLayoutX(x);
        this.setLayoutY(y);

    }
}
