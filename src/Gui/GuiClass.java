package Gui;

import code.DrawData;
import code.ForestFire;
import code.Functions;


import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.Random;


public class GuiClass extends JFrame {
    private JPanel rootPanel;
    private JPanel drawPanel;
    private JPanel controllerPanel;
    private JButton createSpaceButton;
    private JButton addRedTreeButton;
    private JComboBox cellSizeComboBox;
    private JTextField iterationAmountTextField;
    private JButton startButton;
    private JButton stopButton;
    private JTextField propabilityTextField;
    private JButton previousButton;
    private JButton nextButton;
    private JProgressBar iterationProgressBar;
    private JComboBox directionComboBox;
    private JSlider timeSlider;
    private JLabel timeSliderValueLabel;
    private JSpinner yellowMaxIterationsSpinner;
    private JSpinner redTreeMaxIterationsSpinner;
    private JButton nWButton;
    private JButton nEButton;
    private JButton nButton;
    private JButton sWButton;
    private JButton sEButton;
    private JButton sButton;
    private JSlider windPowerSlider;
    private JButton wButton;
    private JButton eButton;
    private JPanel windImagePanel;
    private JPanel windControllsPanel;
    private JRadioButton fireTreeRadioButton;
    private JRadioButton newTreeRadioButton;
    private JRadioButton firedTreeRadioButton;
    private JRadioButton lakeRadioButton;
    private JComboBox sizeDrawComboBox;
    private JPanel radioButtonPanel;
    private JPanel southControllPanel;
    private JTextField backStepsTextField;
    private JButton previousMenuButton;
    private JButton mainMenuButton;
    private JRadioButton roadRadioButton;
    private JLabel fireRadiusLabel;
    private JComboBox fireRadiusComboBox;
    private JRadioButton firemanRadioButton;


    //---FIRST PANEL----
    private JPanel firstRootPanel;
    private JButton readImageButton;
    private JButton emptySpaceButton;
    private JButton exampleImageButton;
    private JPanel firstMenuPanel;

    //----CREATE PICTURE PANEL-----
    private JPanel createPictureRootPanel;
    private JPanel createPictureControllerPanel;
    private JComboBox contextTransComboBox;
    private JButton transformButton;
    private JPanel createPictureDrawPanel;
    private JPanel createPicturePanel;
    private JSlider brightenSlider;
    private JSlider darkenSlider;
    private JSlider binarizationSlider;
    private JButton histogramButton;
    private JButton openPictureButton;
    private JButton backButtonCreatePicturePanel;
    private JButton nextButtonCreatePicturePanel;
    private JLabel brightenLabel;
    private JLabel darkenLabel;
    private JLabel binarizationLAbel;
    private JLabel transformationsLabel;


    DrawData drawData;
    //wielkosc poczatkowa panelu
    private int minimumWidthMainPanel = 600;
    private int minimumHeightMainPanel = 300;
    private int minimumWidthForestPanel = 800;
    private int minimumHeightForestPanel = 700;
    private int minimumWidthCreatePicturePanel = 600;
    private int minimumHeightCreatePicturePanel = 500;
    private int histogramPanelwidth = 900;
    private int histogramPanelHeight = 500;
    //O ile panel będzie większy od wczytanego obrazka
    private int widthDifference = 400;
    private int heightDifference = 100;
    private Timer iterationTimer;
    private int drawSize = 0;
    private int yellowTreeMaxIterationInitialize = 80;
    private int redTreeMaxIterationInitialize = 5;
    private int yellowTreeMinIteration = 10;
    private int redTreeMinIteration = 1;
    private int windPowerSliderMinValue = 0;
    private int windPowerSliderMaxValue = 100;
    private int timeSliderMaxValue = 1000;
    private int timeSliderMinValue = 0;
    private String previousMenuFlag;
    private boolean isImageBrighten = false;
    private boolean isImageDarken = false;
    private boolean isImageBinarization = false;
    private String imageChangedBy;
    private final String lowPassFilterString = "Low pass filter";
    private final String highPassFilterString = "High Pass filter";
    private final String gaussFilterString = "Gauss filter";
    private final String embossString = "Emboss";
    private final String erosionString = "Erosion";
    private final String dilationString = "Dilation";
    private final String openingString = "Opening";
    private final String closingString = "Closing";


    ForestFire forestFire;

    public GuiClass() {

        //funkcja ustawiajaca wartosci poczatkowe atrybutow
        initializePanels();
        initializeAtributes();
        initializeValues();

        setMinimumSize(new Dimension(minimumWidthMainPanel, minimumHeightMainPanel));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("App");
        setLocationRelativeTo(null);
        add(firstRootPanel);


        // ==================================== MAIN MENU PANEL ===================================================

        // ----------------------------------- READ IMAGE BUTTON -----------------------------------
        readImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousMenuFlag = "MainMenu";
                isImageBinarization = false;
                isImageBrighten = false;
                isImageDarken = false;
                imageChangedBy = "None";

                firstRootPanel.removeAll();
                createPictureRootPanel.removeAll();

                createPictureDrawPanel = new MeshDrawPanel(drawData);
                createPictureDrawPanel.setBackground(new Color(124, 125, 127));
                createPictureRootPanel.add(BorderLayout.CENTER, createPictureDrawPanel);
                createPictureRootPanel.add(BorderLayout.EAST, createPictureControllerPanel);
                firstRootPanel.add(createPictureRootPanel);
                firstRootPanel.repaint();
                setMinimumSize(new Dimension(minimumWidthCreatePicturePanel, minimumHeightCreatePicturePanel));
                setSize(new Dimension(minimumWidthCreatePicturePanel, minimumHeightCreatePicturePanel));
                setLocationRelativeTo(null);

            }
        });

        // ------------------------------------ EMPTY SPACE BUTTON ------------------------------------
        emptySpaceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousMenuFlag = "MainMenu";

                firstRootPanel.removeAll();
                rootPanel.removeAll();

                drawPanel = new MeshDrawPanel(drawData);
                drawPanel.setBackground(new Color(124, 125, 127));
                addMouseListenerToDrawPanel();
                rootPanel.add(BorderLayout.CENTER, drawPanel);
                rootPanel.add(BorderLayout.EAST, controllerPanel);
                rootPanel.add(BorderLayout.SOUTH, southControllPanel);
                firstRootPanel.add(rootPanel);
                firstRootPanel.repaint();
                BufferedImage image = new BufferedImage(500, 500, BufferedImage.TYPE_BYTE_BINARY);
                Functions.brightenImage(image, 255);
                drawData.setBgImg(image);

                //USTAWIAM WIELKOSC PANELU WIEKSZA O ZADANA ROZNICE
                setMinimumSize(new Dimension(minimumWidthForestPanel, minimumHeightForestPanel));
                setSize(500 + widthDifference, 500 + heightDifference);
                setLocationRelativeTo(null);
                drawPanel.repaint();
            }
        });

        // ------------------------------------- EXAMPLE IMAGE BUTTON -----------------------------------
        exampleImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousMenuFlag = "MainMenu";

                firstRootPanel.removeAll();
                rootPanel.removeAll();

                drawPanel = new MeshDrawPanel(drawData);
                drawPanel.setBackground(new Color(124, 125, 127));
                addMouseListenerToDrawPanel();
                rootPanel.add(BorderLayout.CENTER, drawPanel);
                rootPanel.add(BorderLayout.EAST, controllerPanel);
                rootPanel.add(BorderLayout.SOUTH, southControllPanel);
                firstRootPanel.add(rootPanel);
                firstRootPanel.repaint();
                setMinimumSize(new Dimension(minimumWidthForestPanel, minimumHeightForestPanel));
                setLocationRelativeTo(null);

                try {
                    //wczytanie obrazu
                    BufferedImage image = Functions.readImage("Mapa_MD_no_terrain_low_res_dark_Gray.bmp");
                    if (image != null) {
                        //binaryzacja 150
                        Functions.binarizationImage(image, 150);
                        //2x dylatacja
                        image = Functions.morphTransfor(image, 'd');
                        image = Functions.morphTransfor(image, 'd');
                        //2x erozja
                        image = Functions.morphTransfor(image, 'e');
                        image = Functions.morphTransfor(image, 'e');

                        drawData.setBgImg(image);
                        //USTAWIAM WIELKOSC PANELU WIEKSZA O ZADANA ROZNICE
                        setSize(image.getWidth() + widthDifference, image.getHeight() + heightDifference);
                        setLocationRelativeTo(null);
                        drawPanel.repaint();

                    } else
                        JOptionPane.showMessageDialog(null, "PICTURE FILE NOT FOUND!");
                } catch (NullPointerException nullEx) {
                    JOptionPane.showMessageDialog(null, "PICTURE NOT LOADED!");
                }
            }
        });


        //========================================= CREATE PICTURE PANEL===========================================

        // -------------------- OPEN PICTURE BUTTON -----------------------------------
        openPictureButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser(".");
                fileChooser.setDialogTitle("Open an Image");
                fileChooser.setAcceptAllFileFilterUsed(false);
                FileNameExtensionFilter filterBmp = new FileNameExtensionFilter("Image bmp", "bmp");
                FileNameExtensionFilter filterJpeg = new FileNameExtensionFilter("Image jpg", "jpg");
                fileChooser.addChoosableFileFilter(filterBmp);
                fileChooser.addChoosableFileFilter(filterJpeg);
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    // dwa image bo chce miec osobno oryginalny i ten ktory zmienilem rysuje
                    BufferedImage image = Functions.readImage(fileChooser.getSelectedFile().getAbsolutePath());
                    BufferedImage image2 = Functions.readImage(fileChooser.getSelectedFile().getAbsolutePath());
                    BufferedImage image3 = Functions.readImage(fileChooser.getSelectedFile().getAbsolutePath());
                    BufferedImage image4 = Functions.readImage(fileChooser.getSelectedFile().getAbsolutePath());

                    drawData.setBgImg(image);
                    drawData.setOriginalbgImg(image2);
                    drawData.setChangedbgImg(image3);
                    drawData.setLastChangedbgImg(image4);
                    //USTAWIAM WIELKOSC PANELU WIEKSZA O ZADANA ROZNICE
                    setMaximumSize(new Dimension(drawData.getWidthPicture() + widthDifference, drawData.getHeightPicture() + heightDifference));
                    setSize(drawData.getWidthPicture() + widthDifference, drawData.getHeightPicture() + heightDifference);
                    setLocationRelativeTo(null);
                    createPictureDrawPanel.repaint();
                }
            }
        });

        // --------------------------BRIGHTEN SLIDER LISTENER ---------------------------------------
        brightenSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                try {
                    BufferedImage imageTochange;
                    if (isImageBinarization == false && isImageDarken == false) {
                        imageTochange = drawData.getOriginalbgImg();
                    } else if (imageChangedBy.equals("Darken") || imageChangedBy.equals("Binarization")) {
                        imageTochange = drawData.getLastChangedbgImg();
                        Graphics g2 = drawData.getChangedbgImg().getGraphics();
                        g2.drawImage(drawData.getLastChangedbgImg(), 0, 0, null);
                        g2.dispose();
                    } else
                        imageTochange = drawData.getChangedbgImg();

                    int brightenValue = brightenSlider.getValue();
                    if (brightenValue == 0)
                        isImageBrighten = false;
                    else {
                        isImageBrighten = true;
                        //przepisuje do obrazu ktory zmienie oryginalny wczytany obraz (na nim dzialam)
                        Graphics g = drawData.getBgImg().getGraphics();
                        g.drawImage(imageTochange, 0, 0, null);
                        g.dispose();
                        Functions.brightenImage(drawData.getBgImg(), brightenValue);
                        imageChangedBy = "Brighten";

                        //zapisuje zmiany do lastChangedImage
                        Graphics g2 = drawData.getLastChangedbgImg().getGraphics();
                        g2.drawImage(drawData.getBgImg(), 0, 0, null);
                        g2.dispose();

                        createPictureDrawPanel.repaint();
                    }
                } catch (NullPointerException nullEx) {
                    JOptionPane.showMessageDialog(null, "PICTURE NOT LOADED!");
                }
            }
        });

        // --------------------------DARKEN SLIDER LISTENER ---------------------------------------
        darkenSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                try {
                    BufferedImage imageTochange;
                    if (isImageBinarization == false && isImageBrighten == false) {
                        imageTochange = drawData.getOriginalbgImg();
                    } else if (imageChangedBy.equals("Brighten") || imageChangedBy.equals("Binarization")) {
                        imageTochange = drawData.getLastChangedbgImg();
                        Graphics g2 = drawData.getChangedbgImg().getGraphics();
                        g2.drawImage(drawData.getLastChangedbgImg(), 0, 0, null);
                        g2.dispose();
                    } else
                        imageTochange = drawData.getChangedbgImg();
                    int darkenValue = darkenSlider.getValue();
                    if (darkenValue == 0)
                        isImageDarken = false;
                    else
                        isImageDarken = true;
                    //przepisuje do obrazu ktory zmienie oryginalny wczytany obraz (na nim dzialam)
                    Graphics g = drawData.getBgImg().getGraphics();
                    g.drawImage(imageTochange, 0, 0, null);
                    g.dispose();
                    Functions.darkenImage(drawData.getBgImg(), darkenValue);
                    createPictureDrawPanel.repaint();
                    imageChangedBy = "Darken";

                    //zapisuje zmiany do lastChangedImage
                    Graphics g2 = drawData.getLastChangedbgImg().getGraphics();
                    g2.drawImage(drawData.getBgImg(), 0, 0, null);
                    g2.dispose();
                } catch (NullPointerException nullEx) {
                    JOptionPane.showMessageDialog(null, "PICTURE NOT LOADED!");
                }

            }
        });

        // --------------------------BINARIZATION SLIDER LISTENER ---------------------------------------
        binarizationSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                try {
                    BufferedImage imageTochange;
                    if (isImageDarken == false && isImageBrighten == false) {
                        imageTochange = drawData.getOriginalbgImg();
                    } else if (imageChangedBy.equals("Brighten") || imageChangedBy.equals("Darken")) {
                        imageTochange = drawData.getLastChangedbgImg();
                        Graphics g2 = drawData.getChangedbgImg().getGraphics();
                        g2.drawImage(drawData.getLastChangedbgImg(), 0, 0, null);
                        g2.dispose();
                    } else
                        imageTochange = drawData.getChangedbgImg();
                    int binarizationLimit = binarizationSlider.getValue();
                    if (binarizationLimit == 0) {
                        isImageBinarization = false;
                        //zapisuje zmiany do lastChangedImage
                        Graphics g = drawData.getBgImg().getGraphics();
                        g.drawImage(imageTochange, 0, 0, null);
                        g.dispose();

                        //zapisuje zmiany do lastChangedImage
                        Graphics g2 = drawData.getLastChangedbgImg().getGraphics();
                        g2.drawImage(drawData.getBgImg(), 0, 0, null);
                        g2.dispose();
                        createPictureDrawPanel.repaint();
                    } else {
                        isImageBinarization = true;
                        //przepisuje do obrazu ktory zmienie oryginalny wczytany obraz (na nim dzialam)
                        Graphics g = drawData.getBgImg().getGraphics();
                        g.drawImage(imageTochange, 0, 0, null);
                        g.dispose();
                        Functions.binarizationImage(drawData.getBgImg(), binarizationLimit);
                        createPictureDrawPanel.repaint();
                        imageChangedBy = "Binarization";

                        //zapisuje zmiany do lastChangedImage
                        Graphics g2 = drawData.getLastChangedbgImg().getGraphics();
                        g2.drawImage(drawData.getBgImg(), 0, 0, null);
                        g2.dispose();
                    }

                } catch (NullPointerException nullEx) {
                    JOptionPane.showMessageDialog(null, "PICTURE NOT LOADED!");
                }

            }
        });


        // --------------------- TRANSFORMATION BUTTON ----------------------------------
        transformButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //GDY OBRAZ ZOSTAL WCZYTANY
                if (drawData.getBgImg() != null) {

                    switch (contextTransComboBox.getSelectedItem().toString()) {

                        case lowPassFilterString:
                            float[][] lowPassFilterMatrix = new float[3][3];
                            for (int i = 0; i < lowPassFilterMatrix.length; i++) {
                                for (int j = 0; j < lowPassFilterMatrix[i].length; j++) {
                                    lowPassFilterMatrix[i][j] = (float) 1.0;
                                }
                            }
                            drawData.setBgImg(Functions.filr(drawData.getBgImg(), lowPassFilterMatrix));
                            break;

                        case highPassFilterString:
                            float[][] highPassFilterMatrix = new float[3][3];
                            for (int i = 0; i < highPassFilterMatrix.length; i++) {
                                for (int j = 0; j < highPassFilterMatrix[i].length; j++) {
                                    highPassFilterMatrix[i][j] = (float) (-1.0);
                                    System.out.println(highPassFilterMatrix[i][j]);
                                }
                            }
                            highPassFilterMatrix[1][1] = 9.0f;
                            drawData.setBgImg(Functions.filr(drawData.getBgImg(), highPassFilterMatrix));
                            break;

                        case gaussFilterString:
                            float[][] gaussFilterMatrix = new float[3][3];
                            gaussFilterMatrix[0][0] = 1;
                            gaussFilterMatrix[0][1] = 4;
                            gaussFilterMatrix[0][2] = 1;
                            gaussFilterMatrix[1][0] = 4;
                            gaussFilterMatrix[1][1] = 32;
                            gaussFilterMatrix[1][2] = 4;
                            gaussFilterMatrix[2][0] = 1;
                            gaussFilterMatrix[2][1] = 4;
                            gaussFilterMatrix[2][2] = 1;
                            drawData.setBgImg(Functions.filr(drawData.getBgImg(), gaussFilterMatrix));
                            break;

                        case embossString:
                            float[][] embosMatrix = new float[3][3];
                            embosMatrix[0][0] = -2;
                            embosMatrix[0][1] = -1;
                            embosMatrix[0][2] = 0;
                            embosMatrix[1][0] = 1;
                            embosMatrix[1][1] = 1;
                            embosMatrix[1][2] = 1;
                            embosMatrix[2][0] = 0;
                            embosMatrix[2][1] = 1;
                            embosMatrix[2][2] = 2;
                            drawData.setBgImg(Functions.filr(drawData.getBgImg(), embosMatrix));
                            break;

                        case erosionString:
                            drawData.setBgImg(Functions.morphTransfor(drawData.getBgImg(), 'e'));
                            break;

                        case dilationString:
                            drawData.setBgImg(Functions.morphTransfor(drawData.getBgImg(), 'd'));
                            break;

                        case openingString:
                            drawData.setBgImg(Functions.morphTransfor(drawData.getBgImg(), 'e'));
                            drawData.setBgImg(Functions.morphTransfor(drawData.getBgImg(), 'd'));
                            break;

                        case closingString:
                            drawData.setBgImg(Functions.morphTransfor(drawData.getBgImg(), 'd'));
                            drawData.setBgImg(Functions.morphTransfor(drawData.getBgImg(), 'e'));
                            break;

                        default:
                            JOptionPane.showMessageDialog(null, "Operation not found in switch");
                    }
                    createPictureDrawPanel.repaint();
                } else
                    JOptionPane.showMessageDialog(null, "PICTURE NOT LOADED!");
            }

        });

        // ----------------------- HISTOGRAM BUTTON ---------------------------
        histogramButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    drawData.setHistogramTab(Functions.createHistogramTab(drawData.getBgImg(), histogramPanelHeight - 100));         //ustawienie tablicy histogramu w drawData
                    new HistogramPanel(drawData, histogramPanelwidth, histogramPanelHeight).show();  // uruchomienie nowego okna
                } catch (NullPointerException nullEx) {
                    JOptionPane.showMessageDialog(null, "PICTURE NOT LOADED!");
                } catch (NumberFormatException numberEx) {
                    JOptionPane.showMessageDialog(null, "WRONG VALUE!");
                }
            }
        });


        // ----------------------- BACK BUTTON (CREATE PICTURE PANEL) --------------
        backButtonCreatePicturePanel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawData.clearData();
                initializeValues();                     //ustawiam poczatkowe wartosci komponentom
                initializeAtributes();
                createPictureDrawPanel.repaint();

                //ustawiam nowe okno
                firstRootPanel.removeAll();
                createPictureRootPanel.removeAll();
                setMinimumSize(new Dimension(minimumWidthMainPanel, minimumHeightMainPanel));
                setSize(new Dimension(minimumWidthMainPanel, minimumHeightMainPanel));
                setLocationRelativeTo(null);
                firstRootPanel.add(firstMenuPanel);
                firstRootPanel.repaint();

            }
        });

        //----------------------- NEXT BUTTON (CREATE PICTURE PANEL) --------------
        nextButtonCreatePicturePanel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (drawData.getBgImg() != null) {
                    previousMenuFlag = "CreatePicturePanel";

                    firstRootPanel.removeAll();
                    rootPanel.removeAll();

                    drawPanel = new MeshDrawPanel(drawData);
                    drawPanel.setBackground(new Color(124, 125, 127));
                    addMouseListenerToDrawPanel();
                    rootPanel.add(BorderLayout.CENTER, drawPanel);
                    rootPanel.add(BorderLayout.EAST, controllerPanel);
                    rootPanel.add(BorderLayout.SOUTH, southControllPanel);

                    firstRootPanel.add(rootPanel);
                    firstRootPanel.repaint();
                    drawPanel.repaint();

                    setMinimumSize(new Dimension(minimumWidthForestPanel, minimumHeightForestPanel));
                    setSize(drawData.getBgImg().getWidth() + widthDifference, drawData.getBgImg().getHeight() + heightDifference);
                    setLocationRelativeTo(null);

                }
            }
        });


        //========================================= FOREST FIRE PANEL =============================================

        //----------------------------------------- ZADANIE TMERA ---------------------------------------
        ActionListener timerFunction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (directionComboBox.getSelectedItem().toString().equals("Forward")) {
                    boolean stillPlay = drawData.getForestFire().play();
                    if (stillPlay)
                        iterationProgressBar.setValue(iterationProgressBar.getValue() + 1);
                    else {
                        iterationTimer.stop();
                        JOptionPane.showMessageDialog(null, "FINISH");
                    }


                }
                if (directionComboBox.getSelectedItem().toString().equals("Backward")) {
                    boolean canBack = drawData.getForestFire().decrementIteration();
                    if (canBack)
                        iterationProgressBar.setValue(iterationProgressBar.getValue() - 1);
                    else {
                        iterationTimer.stop();
                        JOptionPane.showMessageDialog(null, "FINISH");
                    }
                }

                drawPanel.repaint();
            }
        };


        //-------------------------------- BUTTON CREATE SPACE -------------------------------
        createSpaceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // tworzenie Forest fire
                if (drawData.getBgImg() != null) {
                    BufferedImage image = drawData.getBgImg();
                    int cellSize = Integer.parseInt(cellSizeComboBox.getSelectedItem().toString());
                    int iterationAmount = Integer.parseInt(iterationAmountTextField.getText());
                    int propability = Integer.parseInt(propabilityTextField.getText());
                    int backSteps = Integer.parseInt(backStepsTextField.getText());
                    int fireRadius = Integer.parseInt(fireRadiusComboBox.getSelectedItem().toString());
                    if (iterationAmount > 0 && propability >= 0 && propability <= 100 && backSteps > 0 && backSteps < 100 && cellSize * image.getWidth() + widthDifference < 1601 && cellSize * image.getHeight() + heightDifference < 761) {
                        forestFire = new ForestFire(image.getWidth(), image.getHeight(), cellSize, iterationAmount, backSteps, fireRadius);
                        forestFire.setPropabilityAllDirections(propability);
                        forestFire.setPropability();
                        forestFire.createForestSpace(image);

                        drawData.setForestFire(forestFire);
                        //USTAWIAM WIELKOSC PANELU WIEKSZA O ZADANA ROZNICE
                        setSize(forestFire.getSpaceWidth() * forestFire.getCellSize() + widthDifference, forestFire.getSpaceHeight() * forestFire.getCellSize() + heightDifference);
                        setLocationRelativeTo(null);
                        iterationTimer.addActionListener(timerFunction);
                        iterationProgressBar.setMaximum(iterationAmount - 1);
                        iterationProgressBar.setValue(0);
                        redTreeMaxIterationsSpinner.setValue(redTreeMaxIterationInitialize);
                        drawData.getForestFire().setRedTreeMaxIteration(redTreeMaxIterationInitialize);
                        yellowMaxIterationsSpinner.setValue(yellowTreeMaxIterationInitialize);
                        drawData.getForestFire().setYellowTreeMaxIteration(yellowTreeMaxIterationInitialize);
                        drawData.getForestFire().setFireManSize(Integer.parseInt(sizeDrawComboBox.getSelectedItem().toString()));
                        iterationTimer.stop();
                        drawPanel.repaint();
                    } else
                        JOptionPane.showMessageDialog(null, "WRONG VALUE!");
                } else
                    JOptionPane.showMessageDialog(null, "PICTURE FILE NOT FOUND!");
            }
        });


        //------------------------------- BUTTON ADD RED TREE --------------------------------------
        addRedTreeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (drawData.getForestFire() != null) {
                    Random rand = new Random();
                    int amount = 20;
                    int x;
                    int y;
                    for (int i = 0; i < amount; i++) {
                        x = rand.nextInt(drawData.getForestFire().getSpaceWidth() - 10) + 5;
                        y = rand.nextInt(drawData.getForestFire().getSpaceHeight() - 10) + 5;
                        drawData.getForestFire().addRedTree(x, y, drawData.getForestFire().getCellSize());
                    }
                    drawPanel.repaint();
                }
            }
        });


        // ------------------------------------- NEXT BUTTON ----------------------------------------
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (drawData.getForestFire() != null) {
                    iterationTimer.stop(); // STOPUJE TIMER
                    boolean stillPlay = drawData.getForestFire().play();
                    if (stillPlay) {
                        iterationProgressBar.setValue(iterationProgressBar.getValue() + 1);
                        drawPanel.repaint();
                    } else
                        JOptionPane.showMessageDialog(null, "FINISH");
                }
            }
        });

        // -------------------------------------- PREVIOUS BUTTON ------------------------------------
        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (drawData.getForestFire() != null) {
                    iterationTimer.stop(); // STOPUJE TIMER
                    boolean canBack = drawData.getForestFire().decrementIteration();
                    if (canBack) {
                        iterationProgressBar.setValue(iterationProgressBar.getValue() - 1);
                        drawPanel.repaint();
                    } else
                        JOptionPane.showMessageDialog(null, "FINISH");

                }
            }
        });

        //--------------------------------------------- START BUTTON --------------------------
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (drawData.getForestFire() != null)
                    iterationTimer.start();
            }
        });


        // ------------------------------------------ STOP BUTTON ------------------------------------------
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iterationTimer.stop();
            }
        });


        // ------------------------------------------ TIME SLIDER ------------------------------------------
        timeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                iterationTimer.setDelay(timeSlider.getValue());
                timeSliderValueLabel.setText("Delay: " + timeSlider.getValue() + "ms");

            }
        });


        // ------------------------------ SPINNER RED TREE ITERACJI (MINIMUM I AKTUALIZACJA) -------------
        redTreeMaxIterationsSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (Integer.parseInt(redTreeMaxIterationsSpinner.getNextValue().toString()) <= redTreeMinIteration)
                    redTreeMaxIterationsSpinner.setValue(redTreeMinIteration);
                else
                    drawData.getForestFire().setRedTreeMaxIteration(Integer.parseInt(redTreeMaxIterationsSpinner.getValue().toString()));
            }
        });

        // ------------------------------ SPINNER YELLOW TREE ITERACJI (MINIMUM I AKTUALIZACJA) -------------
        yellowMaxIterationsSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (Integer.parseInt(yellowMaxIterationsSpinner.getNextValue().toString()) <= yellowTreeMinIteration)
                    yellowMaxIterationsSpinner.setValue(yellowTreeMinIteration);
                else
                    drawData.getForestFire().setYellowTreeMaxIteration(Integer.parseInt(yellowMaxIterationsSpinner.getValue().toString()));
            }
        });


        //---------------------------------------   WIND BUTTONS  -------------------------------------------
        nButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawData.getForestFire().setWind("N", windPowerSlider.getValue());
            }
        });
        nEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawData.getForestFire().setWind("NE", windPowerSlider.getValue());
            }
        });
        eButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawData.getForestFire().setWind("E", windPowerSlider.getValue());
            }
        });
        sEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawData.getForestFire().setWind("SE", windPowerSlider.getValue());
            }
        });
        sButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawData.getForestFire().setWind("S", windPowerSlider.getValue());
            }
        });
        sWButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawData.getForestFire().setWind("SW", windPowerSlider.getValue());
            }
        });
        wButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawData.getForestFire().setWind("W", windPowerSlider.getValue());
            }
        });
        nWButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawData.getForestFire().setWind("NW", windPowerSlider.getValue());
            }
        });


        // ----------------------------------COMBOBOX ROZMIAR RYSOWANIA ---------------------------------
        sizeDrawComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawSize = Integer.parseInt(sizeDrawComboBox.getSelectedItem().toString());
                if (drawData.getForestFire() != null)
                    drawData.getForestFire().setFireManSize(drawSize);          //ustawiam zasieg strazaka
            }
        });


        // ---------------------------------- PREVIOUS MENU BUTTON ---------------------------------------
        previousMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (previousMenuFlag.equals("MainMenu")) {
                    mainMenuButton.doClick();
                }
                if (previousMenuFlag.equals("CreatePicturePanel")) {
                    initializeValues();                     //ustawiam poczatkowe wartosci komponentom
                    drawData.clearData();
                    readImageButton.doClick();
                    createPictureDrawPanel.repaint();

                }
            }
        });

        // ---------------------------- BACK TO MAIN MENU BUTTON ---------------------------------
        mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iterationTimer.stop();                  //zatrzymuje timer
                drawData.clearData();                   //zeruje dane
                initializeValues();                     //ustawiam poczatkowe wartosci komponentom
                drawPanel.repaint();

                //ustawiam nowe okno
                firstRootPanel.removeAll();
                rootPanel.removeAll();
                setMinimumSize(new Dimension(minimumWidthMainPanel, minimumHeightMainPanel));
                setSize(new Dimension(minimumWidthMainPanel, minimumHeightMainPanel));
                setLocationRelativeTo(null);
                firstRootPanel.add(firstMenuPanel);
                firstRootPanel.repaint();
            }
        });


    }


    //-----------------------FUNKCJA NADAJACA POCZATKOWE WARTOSCI ATRYBUTOM-----------------
    private void initializeValues() {
        timeSlider.setValue(timeSliderMaxValue / 2);
        windPowerSlider.setValue(windPowerSliderMaxValue / 2);
        brightenSlider.setValue(0);
        darkenSlider.setValue(0);
        binarizationSlider.setValue(0);
        redTreeMaxIterationsSpinner.setValue(redTreeMaxIterationInitialize);
        yellowMaxIterationsSpinner.setValue(yellowTreeMaxIterationInitialize);
        timeSliderValueLabel.setText("Delay: " + timeSlider.getValue() + "ms");
        iterationProgressBar.setValue(0);


    }

    private void addMouseListenerToDrawPanel() {
        // -------------------------------------- MOUSE LISTENER MOTION -------------------------------------
        drawPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                int x = e.getX() / drawData.getForestFire().getCellSize();
                int y = e.getY() / drawData.getForestFire().getCellSize();

                if (fireTreeRadioButton.isSelected())
                    drawData.getForestFire().addRedTree(x, y, drawSize);
                if (firedTreeRadioButton.isSelected())
                    drawData.getForestFire().addYellowTree(x, y, drawSize);
                if (newTreeRadioButton.isSelected())
                    drawData.getForestFire().addGreenTree(x, y, drawSize);
                if (lakeRadioButton.isSelected())
                    drawData.getForestFire().addLake(x, y, drawSize);
                if (roadRadioButton.isSelected())
                    drawData.getForestFire().addRoad(x, y, drawSize);
                if (firemanRadioButton.isSelected())
                    drawData.getForestFire().fireMan(x, y, drawSize);

                drawPanel.repaint();

            }
        });
        // -------------------------------------- MOUSE LISTENER CLICKED -------------------------------------
        drawPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int x = e.getX() / drawData.getForestFire().getCellSize();
                int y = e.getY() / drawData.getForestFire().getCellSize();

                if (fireTreeRadioButton.isSelected())
                    drawData.getForestFire().addRedTree(x, y, drawSize);
                if (firedTreeRadioButton.isSelected())
                    drawData.getForestFire().addYellowTree(x, y, drawSize);
                if (newTreeRadioButton.isSelected())
                    drawData.getForestFire().addGreenTree(x, y, drawSize);
                if (lakeRadioButton.isSelected())
                    drawData.getForestFire().addLake(x, y, drawSize);
                if (roadRadioButton.isSelected())
                    drawData.getForestFire().addRoad(x, y, drawSize);
                if (firemanRadioButton.isSelected())
                    drawData.getForestFire().fireMan(x, y, drawSize);

                drawPanel.repaint();


            }
        });
    }

    // -------------------------------------------- INICJALIZACJA ATRYBUTOW ---------------------------------------------
    private void initializeAtributes() {
        drawData = new DrawData();

        cellSizeComboBox.addItem("1");
        cellSizeComboBox.addItem("2");
        cellSizeComboBox.addItem("3");
        cellSizeComboBox.addItem("4");
        directionComboBox.addItem("Forward");
        directionComboBox.addItem("Backward");
        sizeDrawComboBox.addItem("9");
        sizeDrawComboBox.addItem("3");
        sizeDrawComboBox.addItem("6");
        sizeDrawComboBox.addItem("12");
        sizeDrawComboBox.addItem("15");
        sizeDrawComboBox.addItem("20");
        sizeDrawComboBox.addItem("25");
        sizeDrawComboBox.addItem("30");
        sizeDrawComboBox.addItem("35");
        sizeDrawComboBox.addItem("40");
        fireRadiusComboBox.addItem("3");
        fireRadiusComboBox.addItem("5");
        fireRadiusComboBox.addItem("7");
        fireRadiusComboBox.addItem("9");
        fireRadiusComboBox.addItem("11");
        fireRadiusComboBox.addItem("13");
        fireRadiusComboBox.addItem("15");
        contextTransComboBox.addItem(lowPassFilterString);
        contextTransComboBox.addItem(highPassFilterString);
        contextTransComboBox.addItem(gaussFilterString);
        contextTransComboBox.addItem(embossString);
        contextTransComboBox.addItem(erosionString);
        contextTransComboBox.addItem(dilationString);
        contextTransComboBox.addItem(openingString);
        contextTransComboBox.addItem(closingString);
        drawSize = Integer.parseInt(sizeDrawComboBox.getSelectedItem().toString());

        iterationTimer = new Timer(100, null);
        timeSlider.setMinimum(timeSliderMinValue);
        timeSlider.setMaximum(timeSliderMaxValue);
        timeSlider.setMajorTickSpacing(timeSliderMaxValue / 5);

        windPowerSlider.setMinimum(windPowerSliderMinValue);
        windPowerSlider.setMaximum(windPowerSliderMaxValue);
        windPowerSlider.setMajorTickSpacing(windPowerSliderMaxValue / 5);

        brightenSlider.setMinimum(0);
        brightenSlider.setMaximum(255);
        brightenSlider.setMajorTickSpacing(50);

        darkenSlider.setMinimum(0);
        darkenSlider.setMaximum(255);
        darkenSlider.setMajorTickSpacing(50);

        binarizationSlider.setMinimum(0);
        binarizationSlider.setMaximum(255);
        binarizationSlider.setMajorTickSpacing(50);

        wButton.setPreferredSize(new Dimension(80, 20));
        eButton.setPreferredSize(new Dimension(75, 20));


        iterationProgressBar.setBackground(Color.LIGHT_GRAY);
        propabilityTextField.setBackground(Color.LIGHT_GRAY);
        iterationAmountTextField.setBackground(Color.LIGHT_GRAY);
        cellSizeComboBox.setBackground(Color.LIGHT_GRAY);
        directionComboBox.setBackground(Color.LIGHT_GRAY);

        ButtonGroup group = new ButtonGroup();
        group.add(firedTreeRadioButton);
        group.add(fireTreeRadioButton);
        group.add(newTreeRadioButton);
        group.add(lakeRadioButton);
        group.add(roadRadioButton);
        group.add(firemanRadioButton);

    }


    private void initializePanels() {

        //------------------- FIRST MENU PANEL ----------------------------
        firstRootPanel = new JPanel();
        firstRootPanel.setLayout(new BorderLayout(0, 0));
        firstRootPanel.setBackground(new Color(-13872795));
        firstMenuPanel = new JPanel();
        firstMenuPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 4, new Insets(0, 0, 0, 0), -1, -1));
        firstMenuPanel.setBackground(new Color(-13872795));
        firstRootPanel.add(firstMenuPanel, BorderLayout.CENTER);
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        firstMenuPanel.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(2, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        readImageButton = new JButton();
        readImageButton.setBackground(new Color(-9868692));
        readImageButton.setText("Read Image");
        firstMenuPanel.add(readImageButton, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        emptySpaceButton = new JButton();
        emptySpaceButton.setBackground(new Color(-9868692));
        emptySpaceButton.setText("Empty Space");
        firstMenuPanel.add(emptySpaceButton, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        exampleImageButton = new JButton();
        exampleImageButton.setBackground(new Color(-9868692));
        exampleImageButton.setText("Example Image");
        firstMenuPanel.add(exampleImageButton, new com.intellij.uiDesigner.core.GridConstraints(2, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setBackground(new Color(-9868692));
        label1.setForeground(new Color(-4473925));
        label1.setText("Choose Option");
        firstMenuPanel.add(label1, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer2 = new com.intellij.uiDesigner.core.Spacer();
        firstMenuPanel.add(spacer2, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer3 = new com.intellij.uiDesigner.core.Spacer();
        firstMenuPanel.add(spacer3, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));


        //------------------------- CREATE PICTURE PANEL--------------------------

        createPictureRootPanel = new JPanel();
        createPictureRootPanel.setLayout(new BorderLayout(0, 0));
        createPicturePanel = new JPanel();
        createPicturePanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        createPicturePanel.setBackground(new Color(-9868692));
        createPictureRootPanel.add(createPicturePanel, BorderLayout.CENTER);
        createPictureControllerPanel = new JPanel();
        createPictureControllerPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(15, 1, new Insets(0, 0, 0, 0), -1, -1));
        createPictureControllerPanel.setBackground(new Color(-13872795));
        createPicturePanel.add(createPictureControllerPanel, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer4 = new com.intellij.uiDesigner.core.Spacer();
        createPictureControllerPanel.add(spacer4, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        transformationsLabel = new JLabel();
        transformationsLabel.setText("Transformations");
        createPictureControllerPanel.add(transformationsLabel, new com.intellij.uiDesigner.core.GridConstraints(8, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        contextTransComboBox = new JComboBox();
        contextTransComboBox.setBackground(new Color(-9868692));
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        contextTransComboBox.setModel(defaultComboBoxModel1);
        createPictureControllerPanel.add(contextTransComboBox, new com.intellij.uiDesigner.core.GridConstraints(9, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        transformButton = new JButton();
        transformButton.setText("Transform");
        createPictureControllerPanel.add(transformButton, new com.intellij.uiDesigner.core.GridConstraints(10, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        brightenLabel = new JLabel();
        brightenLabel.setText("Brighten");
        createPictureControllerPanel.add(brightenLabel, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        binarizationLAbel = new JLabel();
        binarizationLAbel.setText("Binarization");
        createPictureControllerPanel.add(binarizationLAbel, new com.intellij.uiDesigner.core.GridConstraints(6, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        brightenSlider = new JSlider();
        brightenSlider.setBackground(new Color(-13872795));
        brightenSlider.setPaintLabels(true);
        brightenSlider.setPaintTicks(true);
        createPictureControllerPanel.add(brightenSlider, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        darkenLabel = new JLabel();
        darkenLabel.setText("Darken");
        createPictureControllerPanel.add(darkenLabel, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        darkenSlider = new JSlider();
        darkenSlider.setBackground(new Color(-13872795));
        darkenSlider.setPaintLabels(true);
        darkenSlider.setPaintTicks(true);
        createPictureControllerPanel.add(darkenSlider, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        binarizationSlider = new JSlider();
        binarizationSlider.setBackground(new Color(-13872795));
        binarizationSlider.setPaintLabels(true);
        binarizationSlider.setPaintTicks(true);
        createPictureControllerPanel.add(binarizationSlider, new com.intellij.uiDesigner.core.GridConstraints(7, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        histogramButton = new JButton();
        histogramButton.setText("Histogram");
        createPictureControllerPanel.add(histogramButton, new com.intellij.uiDesigner.core.GridConstraints(11, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer5 = new com.intellij.uiDesigner.core.Spacer();
        createPictureControllerPanel.add(spacer5, new com.intellij.uiDesigner.core.GridConstraints(12, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        openPictureButton = new JButton();
        openPictureButton.setText("Open Picture");
        createPictureControllerPanel.add(openPictureButton, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setBackground(new Color(-13872795));
        createPictureControllerPanel.add(panel1, new com.intellij.uiDesigner.core.GridConstraints(13, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        backButtonCreatePicturePanel = new JButton();
        backButtonCreatePicturePanel.setText("Back ");
        panel1.add(backButtonCreatePicturePanel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nextButtonCreatePicturePanel = new JButton();
        nextButtonCreatePicturePanel.setText("Next");
        panel1.add(nextButtonCreatePicturePanel, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer6 = new com.intellij.uiDesigner.core.Spacer();
        createPictureControllerPanel.add(spacer6, new com.intellij.uiDesigner.core.GridConstraints(14, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        createPictureDrawPanel = new JPanel();
        createPictureDrawPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        createPictureDrawPanel.setBackground(new Color(-9868692));
        createPicturePanel.add(createPictureDrawPanel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(300, 200), null, 0, false));


    }


    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        rootPanel = new JPanel();
        rootPanel.setLayout(new BorderLayout(0, 0));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setBackground(new Color(-13872795));
        rootPanel.add(panel1, BorderLayout.CENTER);
        controllerPanel = new JPanel();
        controllerPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(11, 1, new Insets(0, 0, 0, 0), -1, -1));
        controllerPanel.setBackground(new Color(-13872795));
        panel1.add(controllerPanel, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        createSpaceButton = new JButton();
        createSpaceButton.setText("Create space");
        controllerPanel.add(createSpaceButton, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addRedTreeButton = new JButton();
        addRedTreeButton.setText("Add fire trees");
        controllerPanel.add(addRedTreeButton, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(5, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel2.setBackground(new Color(-13872795));
        controllerPanel.add(panel2, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Zoom");
        panel2.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cellSizeComboBox = new JComboBox();
        cellSizeComboBox.setBackground(new Color(-9868692));
        panel2.add(cellSizeComboBox, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Iterations");
        panel2.add(label2, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        iterationAmountTextField = new JTextField();
        iterationAmountTextField.setHorizontalAlignment(10);
        iterationAmountTextField.setText("1000");
        panel2.add(iterationAmountTextField, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Propability");
        panel2.add(label3, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        propabilityTextField = new JTextField();
        propabilityTextField.setText("50");
        panel2.add(propabilityTextField, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Back steps");
        panel2.add(label4, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        backStepsTextField = new JTextField();
        backStepsTextField.setBackground(new Color(-4473925));
        backStepsTextField.setForeground(new Color(-12236470));
        backStepsTextField.setText("20");
        panel2.add(backStepsTextField, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        fireRadiusLabel = new JLabel();
        fireRadiusLabel.setText("Fire radius         ");
        panel2.add(fireRadiusLabel, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        fireRadiusComboBox = new JComboBox();
        fireRadiusComboBox.setBackground(new Color(-4473925));
        panel2.add(fireRadiusComboBox, new com.intellij.uiDesigner.core.GridConstraints(4, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(5, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel3.setBackground(new Color(-13872795));
        controllerPanel.add(panel3, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        startButton = new JButton();
        startButton.setText("Start");
        panel3.add(startButton, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        stopButton = new JButton();
        stopButton.setText("Stop");
        panel3.add(stopButton, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        previousButton = new JButton();
        previousButton.setText("Previous");
        panel3.add(previousButton, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nextButton = new JButton();
        nextButton.setText("Next");
        panel3.add(nextButton, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Direction");
        panel3.add(label5, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        directionComboBox = new JComboBox();
        directionComboBox.setBackground(new Color(-9868692));
        panel3.add(directionComboBox, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        yellowMaxIterationsSpinner = new JSpinner();
        panel3.add(yellowMaxIterationsSpinner, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        redTreeMaxIterationsSpinner = new JSpinner();
        redTreeMaxIterationsSpinner.setBackground(new Color(-9868692));
        redTreeMaxIterationsSpinner.setForeground(new Color(-9868692));
        panel3.add(redTreeMaxIterationsSpinner, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("Rebirth time");
        panel3.add(label6, new com.intellij.uiDesigner.core.GridConstraints(4, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setText("Fire time");
        panel3.add(label7, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        iterationProgressBar = new JProgressBar();
        iterationProgressBar.setForeground(new Color(-3195592));
        iterationProgressBar.setStringPainted(true);
        iterationProgressBar.setToolTipText("Iteration progress");
        controllerPanel.add(iterationProgressBar, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        timeSlider = new JSlider();
        timeSlider.setBackground(new Color(-13872795));
        timeSlider.setPaintLabels(false);
        timeSlider.setPaintTicks(true);
        timeSlider.setSnapToTicks(false);
        controllerPanel.add(timeSlider, new com.intellij.uiDesigner.core.GridConstraints(6, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        timeSliderValueLabel = new JLabel();
        timeSliderValueLabel.setText("Delay:");
        controllerPanel.add(timeSliderValueLabel, new com.intellij.uiDesigner.core.GridConstraints(7, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        windControllsPanel = new JPanel();
        windControllsPanel.setLayout(new BorderLayout(0, 0));
        controllerPanel.add(windControllsPanel, new com.intellij.uiDesigner.core.GridConstraints(8, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridBagLayout());
        windControllsPanel.add(panel4, BorderLayout.NORTH);
        nWButton = new JButton();
        nWButton.setText("NW");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel4.add(nWButton, gbc);
        nEButton = new JButton();
        nEButton.setText("NE");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel4.add(nEButton, gbc);
        nButton = new JButton();
        nButton.setHorizontalTextPosition(0);
        nButton.setText("N");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel4.add(nButton, gbc);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridBagLayout());
        windControllsPanel.add(panel5, BorderLayout.SOUTH);
        sWButton = new JButton();
        sWButton.setText("SW");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel5.add(sWButton, gbc);
        sEButton = new JButton();
        sEButton.setText("SE");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel5.add(sEButton, gbc);
        sButton = new JButton();
        sButton.setText("S");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel5.add(sButton, gbc);
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new BorderLayout(0, 0));
        panel6.setBackground(new Color(-13872795));
        windControllsPanel.add(panel6, BorderLayout.CENTER);
        wButton = new JButton();
        wButton.setHorizontalAlignment(0);
        wButton.setText("W");
        panel6.add(wButton, BorderLayout.WEST);
        eButton = new JButton();
        eButton.setText("E");
        panel6.add(eButton, BorderLayout.EAST);
        windImagePanel = new JPanel();
        windImagePanel.setLayout(new BorderLayout(0, 0));
        panel6.add(windImagePanel, BorderLayout.CENTER);
        windPowerSlider = new JSlider();
        windPowerSlider.setBackground(new Color(-13872795));
        windPowerSlider.setPaintLabels(true);
        windPowerSlider.setPaintTicks(true);
        controllerPanel.add(windPowerSlider, new com.intellij.uiDesigner.core.GridConstraints(9, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JSeparator separator1 = new JSeparator();
        controllerPanel.add(separator1, new com.intellij.uiDesigner.core.GridConstraints(10, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        drawPanel = new JPanel();
        drawPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        drawPanel.setBackground(new Color(-8618625));
        drawPanel.setEnabled(true);
        panel1.add(drawPanel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(300, 200), null, 0, false));
        southControllPanel = new JPanel();
        southControllPanel.setLayout(new BorderLayout(0, 0));
        rootPanel.add(southControllPanel, BorderLayout.SOUTH);
        radioButtonPanel = new JPanel();
        radioButtonPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 7, new Insets(0, 0, 0, 0), -1, -1));
        radioButtonPanel.setBackground(new Color(-13872795));
        southControllPanel.add(radioButtonPanel, BorderLayout.NORTH);
        fireTreeRadioButton = new JRadioButton();
        fireTreeRadioButton.setBackground(new Color(-13872795));
        fireTreeRadioButton.setText("Fire tree");
        radioButtonPanel.add(fireTreeRadioButton, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        newTreeRadioButton = new JRadioButton();
        newTreeRadioButton.setBackground(new Color(-13872795));
        newTreeRadioButton.setText("New tree");
        radioButtonPanel.add(newTreeRadioButton, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        firedTreeRadioButton = new JRadioButton();
        firedTreeRadioButton.setBackground(new Color(-13872795));
        firedTreeRadioButton.setText("Fired tree");
        radioButtonPanel.add(firedTreeRadioButton, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lakeRadioButton = new JRadioButton();
        lakeRadioButton.setBackground(new Color(-13872795));
        lakeRadioButton.setHideActionText(false);
        lakeRadioButton.setSelected(true);
        lakeRadioButton.setText("Lake");
        radioButtonPanel.add(lakeRadioButton, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        sizeDrawComboBox = new JComboBox();
        sizeDrawComboBox.setBackground(new Color(-4473925));
        sizeDrawComboBox.setEditable(false);
        sizeDrawComboBox.setForeground(new Color(-12236470));
        radioButtonPanel.add(sizeDrawComboBox, new com.intellij.uiDesigner.core.GridConstraints(1, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label8 = new JLabel();
        label8.setText("Draw size");
        radioButtonPanel.add(label8, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel7.setBackground(new Color(-13872795));
        radioButtonPanel.add(panel7, new com.intellij.uiDesigner.core.GridConstraints(1, 6, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        mainMenuButton = new JButton();
        mainMenuButton.setText("Main menu");
        panel7.add(mainMenuButton, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        radioButtonPanel.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(1, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        roadRadioButton = new JRadioButton();
        roadRadioButton.setBackground(new Color(-13872795));
        roadRadioButton.setText("Road");
        radioButtonPanel.add(roadRadioButton, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        previousMenuButton = new JButton();
        previousMenuButton.setText("Previous menu");
        radioButtonPanel.add(previousMenuButton, new com.intellij.uiDesigner.core.GridConstraints(1, 5, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        firemanRadioButton = new JRadioButton();
        firemanRadioButton.setBackground(new Color(-13872795));
        firemanRadioButton.setText("Fireman");
        radioButtonPanel.add(firemanRadioButton, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPanel;
    }

}
