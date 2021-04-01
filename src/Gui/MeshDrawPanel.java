package Gui;

import code.*;

import javax.swing.*;
import java.awt.*;


public class MeshDrawPanel extends JPanel {
    private DrawData drawData;

    public MeshDrawPanel(DrawData dm) {

        this.drawData = dm;

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);


        //------------------------------------------ FOREST FIRE ----------------------------------------------
       if (drawData.getForestFire() != null) {

            int pointSize = drawData.getForestFire().getCellSize();
            int iteration = drawData.getForestFire().getIterationNow()%drawData.getForestFire().getBackStepAmount();

            for (int y = 0; y < drawData.getForestFire().getSpaceHeight(); y++) {
                for (int x = 0; x < drawData.getForestFire().getSpaceWidth(); x++) {
                    if (drawData.getForestFire().getSpace()[iteration][y][x].getValue() == 0) {  //PLANACE
                        g.setColor(new Color(180, 0, 0));
                    } else if (drawData.getForestFire().getSpace()[iteration][y][x].getValue() == 1) {  //ZDROWE DRZEWO
                        g.setColor(new Color(0, 180, 0));
                    } else if (drawData.getForestFire().getSpace()[iteration][y][x].getValue() == 2) {  //SPALONE
                        g.setColor(Color.YELLOW);
                    } else if (drawData.getForestFire().getSpace()[iteration][y][x].getValue() == -1) {  //BRAK DRZEW
                        g.setColor(new Color(0, 0, 180));
                    } else if (drawData.getForestFire().getSpace()[iteration][y][x].getValue() == -2) {  //droga
                        g.setColor(new Color(180, 180, 180));
                    } else if (drawData.getForestFire().getSpace()[iteration][y][x].getValue() == 5) {  //strazak
                        g.setColor(Color.MAGENTA);
                    }

                    g.fillRect(x * pointSize, y * pointSize, pointSize, pointSize);

                }
            }

        }else if(drawData.getBgImg()!=null){                                        //gdy zaladuje panel rysuje obraz
            ((Graphics2D) g).drawImage(drawData.getBgImg(),0,0,null);
        }


    }



    @Override
    public void repaint() {
        super.repaint();
    }


}
