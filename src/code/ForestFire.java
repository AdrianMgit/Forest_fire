package code;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

import static java.lang.StrictMath.abs;

public class ForestFire {
    private int spaceWidth;
    private int spaceHeight;
    private Cell[][][] space;           //-1 jezioro    0 czerowone     1 zielone           2 zolte    -2 droga
    private int cellSize;
    private int iterationAmount;
    private int iterationNow = 0;               //ilosc iteracji do iterMax
    private int redTreeMaxIteration;            //po ilu iteracjach drzewo splonie
    private int YellowTreeMaxIteration;         //po ilu iteracjach drzewo sie odrodzi
    private int fireManMaxIteration=50;         // po ilu iteracjach zniknie strazak
    private int backStepAmount;              //tyle iteracji przetrzymuje w tabicy
    private int realIt=0;                     //obecny narysowany indeks tablicy
    private int backStepCount=0;              //licznik o ile iteracji sie cofnalem previusButton albo timer
    private  int propabilityAllDirections;
    private int radius;                     // promien pozaru
    private int propabilityMatrix[][];
    private boolean isAutomaticFireMan;
    private int fireManSize;


    public ForestFire(int imageWidth, int imageHeight, int cellSize, int iterationAmount,int backStepAmount,int radius) {
        this.backStepAmount=backStepAmount;
        this.spaceWidth = imageWidth;
        this.spaceHeight = imageHeight;
        this.cellSize = cellSize;
        this.radius=radius;
        this.iterationAmount = iterationAmount;
        this.propabilityMatrix=new int[radius][radius];
        space = new Cell[backStepAmount][spaceHeight][spaceWidth];
    }






    //------------------------------------- TWORZENIE PLANSZY NA PODSTAWIE OBRAZU -----------------------
    public void createForestSpace(BufferedImage originalImage) {
        if (originalImage != null) {

            // INICJALIZACJA MACIERZY
            for (int t = 0; t < backStepAmount; t++) {         //iterationAmount
                for (int i = 0; i < spaceHeight; i++) {
                    for (int j = 0; j < spaceWidth; j++) {
                        space[t][i][j] = new Cell(cellSize, -2);
                    }
                }
            }

            Color pixelColor;
            int red;
            for (int y = 0; y < originalImage.getHeight(); y++) {
                for (int x = 0; x < originalImage.getWidth(); x++) {
                    pixelColor = new Color(originalImage.getRGB(x, y));
                    red = pixelColor.getRed();
                    if (red == 255) {
                        space[0][y][x].setValue(1);                                 //1 zdrowe drzewo
                    } else {
                        space[0][y][x].setValue(-1);                                //-1 jezioro
                    }
                }
            }
        }
    }


    //---------------------------------------------- START --------------------------------------------------
    public boolean play() {

        Random rand = new Random();
        int drawnNumber;
        boolean ignited;

        if (iterationNow < iterationAmount-1) {
            backStepCount=0;
            iterationNow++;
            realIt=iterationNow%backStepAmount;
            int realPreviousIteration=(iterationNow-1)%backStepAmount;
            //PRZECHDOZENIE PO MACIERZY
            for (int y = 0; y < spaceHeight; y++) {
                for (int x = 0; x < spaceWidth; x++) {

                    space[realIt][y][x].setValue(space[realPreviousIteration][y][x].getValue());
                    if (y > (radius-1)/2 && y < spaceHeight -(radius-1)/2 && x > (radius-1)/2 && x < spaceWidth -(radius-1)/2) {
                        ignited = false;
                        if (space[realPreviousIteration][y][x].getValue() == 1) {     //sprawdzam sasiadow tylko zdrowego drzewa
                            space[realIt][y][x].setIterationAmount(0);

                            drawnNumber = rand.nextInt(100);                //losuje liczbe dla prawdopodobienstwa
                            //PRZECHODZENIE PO SASIADACH
                            for (int i = 0; i < radius; i++) {
                                if (ignited == false) {
                                    for (int j = 0; j < radius; j++) {
                                        if (ignited == false) {
                                            if (i == (radius-1)/2 && j == (radius-1)/2) ;         //NIE LICZE SAMEGO SIEBIE
                                            if (space[realPreviousIteration][y -(radius-1)/2 + i][x -(radius-1)/2 + j].getValue() == 0) {      //gdy ma sasiada plonacego
                                                if (propabilityMatrix[i][j]>drawnNumber) {
                                                    space[realIt][y][x].setValue(0);
                                                    ignited = true;
                                                }
                                            }
                                        } else break;
                                    }
                                } else break;
                            }
                        } else if (space[realPreviousIteration][y][x].getValue() == 0) {         //gdy plonace
                            if (space[realPreviousIteration][y][x].getIterationAmount() < redTreeMaxIteration) {    //gdy iter <maxiter
                                space[realIt][y][x].setIterationAmount(space[realPreviousIteration][y][x].getIterationAmount()+1);
                                //iteracja ++

                            }
                            else {
                                space[realIt][y][x].setValue(2);                  //splonelo
                                space[realIt][y][x].setIterationAmount(0);
                            }
                        } else if (space[realPreviousIteration][y][x].getValue() == 2) {         //gdy juz splonelo
                            if (space[realPreviousIteration][y][x].getIterationAmount() < YellowTreeMaxIteration) {   //gdy iter <maxiter
                                space[realIt][y][x].setIterationAmount(space[realPreviousIteration][y][x].getIterationAmount() + 1);         //iteracja ++
                                //iteracja ++

                            } else {
                                space[realIt][y][x].setValue(1);                  //odradza sie
                                space[realIt][y][x].setIterationAmount(0);
                            }
                        }
                         else if (space[realPreviousIteration][y][x].getValue() == 5) {         //gdy strazak
                            if (space[realPreviousIteration][y][x].getIterationAmount() < fireManMaxIteration) {   //gdy iter <maxiter
                                space[realIt][y][x].setIterationAmount(space[realPreviousIteration][y][x].getIterationAmount() + 1);         //iteracja ++
                                //iteracja ++
                                for (int i = 0; i < fireManSize; i++) {
                                    for (int j = 0; j < fireManSize; j++) {
                                        if (i == (fireManSize - 1) / 2 && j == (fireManSize - 1) / 2) ;         //NIE LICZE SAMEGO SIEBIE
                                        if ( (x-(fireManSize-1)/2+j) > 0 && (x-(fireManSize-1)/2+j) < spaceWidth && (y-(fireManSize-1)/2+i) > 0 && (y-(fireManSize-1)/2+i) < spaceHeight){
                                            if (space[realIt][y - (fireManSize - 1) / 2 + i][x - (fireManSize - 1) / 2 + j].getValue() == 0) {
                                                space[realIt][y - (fireManSize - 1) / 2 + i][x - (fireManSize - 1) / 2 + j].setValue(5);
                                                space[realIt][y][x].setIterationAmount(0);

                                            }
                                            if (space[realPreviousIteration][y - (fireManSize - 1) / 2 + i][x - (fireManSize - 1) / 2 + j].getValue() == 0) {
                                                space[realIt][y - (fireManSize - 1) / 2 + i][x - (fireManSize - 1) / 2 + j].setValue(5);
                                                space[realIt][y][x].setIterationAmount(0);

                                            }

                                        }
                                    }
                                }
                            }
                            else {
                                space[realIt][y][x].setValue(2);                  //ustawiam na zolte
                                space[realIt][y][x].setIterationAmount(0);
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    // ----------------------------------------------- DODANIE PLONACEGO DRZEWA ----------------------------------
    public void addRedTree(int x, int y,int size) {
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                if ( (x-(size-1)/2+j) > 0 && (x-(size-1)/2+j) < spaceWidth && (y-(size-1)/2+i) > 0 && (y-(size-1)/2+i) < spaceHeight&&space[realIt][y-(size-1)/2+i][x-(size-1)/2+j].getValue()!=-1)
                    space[realIt][y-(size-1)/2+i][x-(size-1)/2+j].setValue(0);
            }
        }
    }

    // ----------------------------------------------- DODANIE SPALONEGO DRZEWA -------------------------------------
    public void addYellowTree(int x, int y,int size) {
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                if ( (x-(size-1)/2+j) > 0 && (x-(size-1)/2+j) < spaceWidth && (y-(size-1)/2+i) > 0 && (y-(size-1)/2+i) < spaceHeight&&space[realIt][y-(size-1)/2+i][x-(size-1)/2+j].getValue()!=-1)
                    space[realIt][y-(size-1)/2+i][x-(size-1)/2+j].setValue(2);
            }
        }
    }

    // ----------------------------------------------- DODANIE ZDROWEGO DRZEWA ------------------------------------
    public void addGreenTree(int x, int y,int size) {
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                if ( (x-(size-1)/2+j) > 0 && (x-(size-1)/2+j) < spaceWidth && (y-(size-1)/2+i) > 0 && (y-(size-1)/2+i) < spaceHeight&&space[realIt][y-(size-1)/2+i][x-(size-1)/2+j].getValue()!=-1)
                    space[realIt][y-(size-1)/2+i][x-(size-1)/2+j].setValue(1);
            }
        }
    }

    // -------------------------------------------------- DODANIE JEZIORA ----------------------------------------
    public void addLake(int x, int y, int size) {

        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                if ( (x-(size-1)/2+j) > 0 && (x-(size-1)/2+j) < spaceWidth && (y-(size-1)/2+i) > 0 && (y-(size-1)/2+i) < spaceHeight)
                    space[realIt][y-(size-1)/2+i][x-(size-1)/2+j].setValue(-1);
            }
        }
    }

    // -------------------------------------------------- DODANIE JEZIORA ----------------------------------------
    public void addRoad(int x, int y, int size) {

        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                if ( (x-(size-1)/2+j) > 0 && (x-(size-1)/2+j) < spaceWidth && (y-(size-1)/2+i) > 0 && (y-(size-1)/2+i) < spaceHeight&&space[realIt][y-(size-1)/2+i][x-(size-1)/2+j].getValue()!=-1)
                    space[realIt][y-(size-1)/2+i][x-(size-1)/2+j].setValue(-2);
            }
        }
    }

    public void fireMan(int x, int y, int size) {

        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                if ( (x-(size-1)/2+j) > 0 && (x-(size-1)/2+j) < spaceWidth && (y-(size-1)/2+i) > 0 && (y-(size-1)/2+i) < spaceHeight&&space[realIt][y-(size-1)/2+i][x-(size-1)/2+j].getValue()!=-1)
                    space[realIt][y-(size-1)/2+i][x-(size-1)/2+j].setValue(5);
            }
        }
    }

    // ------------------------------------------------- DEKREMENTOWANIE ITERACJI (COFANIE) -------------------------
    public boolean decrementIteration(){
        if(iterationNow>0 && backStepCount<backStepAmount-1) {
            backStepCount++;
            iterationNow--;
            realIt=(realIt-1)%backStepAmount;
            return true;
        }
        return false;
    }

    // ------------------------------------------------ USTAWIENIE PRAWDOP. DAL KAZDEGO KERUNKU --------------------
    public void setPropability(){
        for(int i=0;i<radius;i++){
            for(int j=0;j<radius;j++){
                propabilityMatrix[i][j]=propabilityAllDirections;
            }
        }
    }

    // ------------------------------------------------ USTAWIENIE SILY WIATRU W ZADANYM KIERUNKU ------------------
    public void setWind(String direction, int value){
        int matrixPropabilityRowIndex=0;
        int matrixPropabilityColumnIndex=0;
        setPropability();           //zeruje wszystkie kierunki wiatru

        switch (direction){
            case "N":
                matrixPropabilityRowIndex=(radius-1);
                matrixPropabilityColumnIndex=(radius-1)/2;
                break;
            case "NE":
                matrixPropabilityRowIndex=(radius-1);
                matrixPropabilityColumnIndex=0;
                break;
            case "E":
                matrixPropabilityRowIndex=(radius-1)/2;
                matrixPropabilityColumnIndex=0;
                break;
            case "SE":
                matrixPropabilityRowIndex=0;
                matrixPropabilityColumnIndex=0;
                break;
            case "S":
                matrixPropabilityRowIndex=0;
                matrixPropabilityColumnIndex=(radius-1)/2;
                break;
            case "SW":
                matrixPropabilityRowIndex=0;
                matrixPropabilityColumnIndex=(radius-1);
                break;
            case "W":
                matrixPropabilityRowIndex=(radius-1)/2;
                matrixPropabilityColumnIndex=(radius-1);
                break;
            case "NW":
                matrixPropabilityRowIndex=(radius-1);
                matrixPropabilityColumnIndex=(radius-1);
                break;
        }
        if(propabilityMatrix[matrixPropabilityRowIndex][matrixPropabilityColumnIndex]+value<100)
            propabilityMatrix[matrixPropabilityRowIndex][matrixPropabilityColumnIndex]+=value;
        else
            propabilityMatrix[matrixPropabilityRowIndex][matrixPropabilityColumnIndex]=100;

    }













    public int getPropabilityAllDirections() {
        return propabilityAllDirections;
    }

    public void setPropabilityAllDirections(int propabilityAllDirections) {
        this.propabilityAllDirections = propabilityAllDirections;
    }

    public int getSpaceHeight() {
        return spaceHeight;
    }

    public void setSpaceHeight(int spaceHeight) {
        this.spaceHeight = spaceHeight;
    }

    public int getSpaceWidth() {
        return spaceWidth;
    }

    public void setSpaceWidth(int spaceWidth) {
        this.spaceWidth = spaceWidth;
    }

    public Cell[][][] getSpace() {
        return space;
    }

    public void setSpace(Cell[][][] space) {
        this.space = space;
    }

    public int getCellSize() {
        return cellSize;
    }

    public void setCellSize(int cellSize) {
        this.cellSize = cellSize;
    }

    public int getIterationAmount() {
        return iterationAmount;
    }

    public void setIterationAmount(int iterationAmount) {
        this.iterationAmount = iterationAmount;
    }

    public int getIterationNow() {
        return iterationNow;
    }

    public void setIterationNow(int iterationNow) {
        this.iterationNow = iterationNow;
    }

    public int getRedTreeMaxIteration() {
        return redTreeMaxIteration;
    }

    public void setRedTreeMaxIteration(int redTreeMaxIteration) {
        this.redTreeMaxIteration = redTreeMaxIteration;
    }

    public int getYellowTreeMaxIteration() {
        return YellowTreeMaxIteration;
    }

    public void setYellowTreeMaxIteration(int yellowTreeMaxIteration) {
        YellowTreeMaxIteration = yellowTreeMaxIteration;
    }

    public int getBackStepAmount() {
        return backStepAmount;
    }

    public void setBackStepAmount(int backStepAmount) {
        this.backStepAmount = backStepAmount;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public boolean isAutomaticFireMan() {
        return isAutomaticFireMan;
    }

    public void setAutomaticFireMan(boolean automaticFireMan) {
        isAutomaticFireMan = automaticFireMan;
    }

    public int getFireManSize() {
        return fireManSize;
    }

    public void setFireManSize(int fireManSize) {
        this.fireManSize = fireManSize;
    }
}
