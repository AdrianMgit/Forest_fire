package code;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DrawData {

    private BufferedImage bgImg;
    private BufferedImage OriginalbgImg;            //do operacji na obrazie
    private BufferedImage changedbgImg;            //do operacji na obrazie
    private BufferedImage lastChangedbgImg;            //do operacji na obrazie


    private int widthPicture = 600;
    private int heightPicture = 330;
    private int histogramTab[] = new int[256];
    private ForestFire forestFire;





    public DrawData() {
        bgImg = null;
    }


    public int[] getHistogramTab() {
        return histogramTab;
    }

    public void setHistogramTab(int[] histogramTab) {
        this.histogramTab = histogramTab;
    }

    public int getWidthPicture() {
        return widthPicture;
    }

    public void setWidthPicture(int widthPicture) {
        this.widthPicture = widthPicture;
    }

    public int getHeightPicture() {
        return heightPicture;
    }

    public void setHeightPicture(int heightPicture) {
        this.heightPicture = heightPicture;
    }

    public void setBgImg(BufferedImage bgImg) {
        this.bgImg = bgImg;
    }

    public BufferedImage getBgImg() {
        return bgImg;
    }

    public ForestFire getForestFire() {
        return forestFire;
    }

    public void setForestFire(ForestFire forestFire) {
        this.forestFire = forestFire;
    }

    public BufferedImage getOriginalbgImg() {
        return OriginalbgImg;
    }

    public void setOriginalbgImg(BufferedImage originalbgImg) {
        OriginalbgImg = originalbgImg;
    }

    public BufferedImage getChangedbgImg() {
        return changedbgImg;
    }

    public void setChangedbgImg(BufferedImage changedbgImg) {
        this.changedbgImg = changedbgImg;
    }

    public BufferedImage getLastChangedbgImg() {
        return lastChangedbgImg;
    }

    public void setLastChangedbgImg(BufferedImage lastChangedbgImg) {
        this.lastChangedbgImg = lastChangedbgImg;
    }


    public void clearData(){
        bgImg=null;
        OriginalbgImg=null;
        changedbgImg=null;
        lastChangedbgImg=null;
        forestFire=null;
    }

}