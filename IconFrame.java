import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.image.BufferedImage;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

public class IconFrame extends JFrame {
    /**
     *
     */
    private static final long serialVersionUID = 1675721345165995296L;
//ADVANCED
    private JDialog advancedSettings;

    private int row;
    private int col;
    // Labels for prompts
    private JLabel rowLabel;
    private JLabel colLabel;
    // Text Fields for promts
    private JTextField rowsField;
    private JTextField colsField;
    // Buttons
    private JButton importButton;
    private JButton submitButton;
    // Panel to hold input form
    private JPanel advancedPanel;
    public boolean rowsColsSelected = true;
    public boolean newBitmapSeleected = false;
//ADVANCED ENDS
    private boolean didColorChange = false;
    // CHECKBOX
    private JCheckBox advancedCheckBox = new JCheckBox();
    // ICON
    Icon icon;
    // BUTTONS
    private JButton currentColor = new JButton();
    // Color Values
    private int currentRed = 255;
    private int currentGreen = 255;
    private int currentBlue = 255;
    // PANELS
    private JPanel buttonPanel;
    private JPanel colorPickerPanel;
    private JPanel advancedCheckPanel;
    private JPanel historyColorPanel;
    // SLIDERS
    JSlider redSlider;
    JSlider greenSlider;
    JSlider blueSlider;
// **********************
//         MAIN
// **********************
    public IconFrame(int rows, int cols) {
        super("Design Your Icon");
        setUpGUI(rows, cols);
    }

// **********************
//     Setup Pixels
// **********************
    public void setUpPixelsFrame(int rows, int cols) {
        icon = new Icon(rows, cols);
        row = rows;
        col = cols;
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(rows, cols));
        for (int i = 0; i < rows * cols; i++) {
            int number = i;
            JButton b = new JButton();
            b.setMaximumSize(new Dimension(10, 10));
            b.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // ADVANCED SETTINGS CHECKBOX SELECTED
                    if (advancedCheckBox.isSelected()) {
                        // Create Advanced GUI HERE
                        showAdvancedSettingsWindow(new Point(number%rows,number/cols));
                    }
                    //  NORMAL COLOR SETTING
                    else {
                        b.setBackground(new Color(currentRed,currentGreen,currentBlue));
                        didColorChange = false;
                        for (int i = 0; i < 5; i++) {
                            Color lastUsedColor = historyColorPanel.getComponent(i).getBackground();
                            if (currentRed == lastUsedColor.getRed() && currentGreen == lastUsedColor.getGreen() && currentBlue == lastUsedColor.getBlue()) {
                                didColorChange = true;
                            }
                        }
                        if (!didColorChange) {
                            addColorToHistory();
                        }
                    }// normal color change ends here
                }// end of action
            });// end of button control
            b.setBackground(Color.WHITE);
            buttonPanel.add(b);
        }// end of for loops
    } // end of setupPixelFrame function
// **********************
//     Setup Sliders
// **********************
    private void setUpSliders() {
        int sliderMin = 0;
        int sliderMax = 255;
        JPanel sliderPanel = new JPanel();
        sliderPanel.setLayout(new GridLayout(6, 1));
    // CHANGE LISTENER
        class ColorListener implements ChangeListener {
            @Override
            public void stateChanged(ChangeEvent e) {
                mixColors();    // Mix Color function can be found below
            }
        }
        ChangeListener listener = new ColorListener();
        // ***************************************
        // RED
        JPanel redSliderPanel = new JPanel();
        redSliderPanel.setLayout(new GridLayout(1, 2));
        redSlider = new JSlider(SwingConstants.HORIZONTAL, sliderMin, sliderMax, sliderMax);
        redSlider.addChangeListener(listener);

        sliderPanel.add(redSlider);
        sliderPanel.add(new JLabel("RED"));
        // ***************************************
        // GREEN
        JPanel greenSliderPanel = new JPanel();
        greenSliderPanel.setLayout(new GridLayout(1, 2));
        greenSlider = new JSlider(SwingConstants.HORIZONTAL, sliderMin, sliderMax, sliderMax);
        greenSlider.addChangeListener(listener);

        sliderPanel.add(greenSlider);
        sliderPanel.add(new JLabel("GREEN"));
        // ***************************************
        // BLUE
        JPanel blueSliderPanel = new JPanel();
        blueSliderPanel.setLayout(new GridLayout(1, 2));
        blueSlider = new JSlider(SwingConstants.HORIZONTAL, sliderMin, sliderMax, sliderMax);
        blueSlider.addChangeListener(listener);

        sliderPanel.add(blueSlider);
        sliderPanel.add(new JLabel("BLUE"));
        // ***************************************
        sliderPanel.add(redSliderPanel);
        sliderPanel.add(greenSliderPanel);
        sliderPanel.add(blueSliderPanel);
        colorPickerPanel.add(sliderPanel, BorderLayout.WEST);
        currentColor.setBackground(new Color(currentRed,currentGreen,currentBlue));
        colorPickerPanel.add(currentColor, BorderLayout.EAST);
        setUpHistoryColors();
        colorPickerPanel.add(historyColorPanel, BorderLayout.NORTH);
        // ***************************************
    }
// **********************
// Setup History Colors
// **********************
    private void setUpHistoryColors() {
        historyColorPanel = new JPanel();
        historyColorPanel.setLayout(new GridLayout(1,5));
        for (int i = 0; i < 5; i++) {
            JButton button = new JButton();
            button.setBackground(Color.WHITE);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Access new color
                    currentColor.setBackground(button.getBackground());
                    currentRed = currentColor.getBackground().getRed();
                    currentGreen = currentColor.getBackground().getGreen();
                    currentBlue = currentColor.getBackground().getBlue();

                    // reset the sliders
                    redSlider.setValue(button.getBackground().getRed());
                    greenSlider.setValue(button.getBackground().getGreen());
                    blueSlider.setValue(button.getBackground().getBlue());
                }
            });
            historyColorPanel.add(button);
        }
    }
    private void addColorToHistory() {
        for (int i = 4; i > 0; i--) {
            Color newColor = historyColorPanel.getComponent(i-1).getBackground();
            historyColorPanel.getComponent(i).setBackground(newColor);
        }
        historyColorPanel.getComponent(0).setBackground(new Color(currentRed, currentGreen, currentBlue));
    }
// **********************
//       Setup GUI
// **********************
    public void setUpGUI(int rows, int cols) {
        setUpPixelsFrame(rows, cols);

        getContentPane().add(buttonPanel, BorderLayout.CENTER);

        colorPickerPanel = new JPanel();
        colorPickerPanel.setLayout(new BorderLayout());
        // create panel
        advancedCheckPanel = new JPanel();
        // add checkbox to panel
        advancedCheckPanel.add(advancedCheckBox, BorderLayout.WEST);
        // set selected variable for checkbox
        advancedCheckBox.setSelected(false);
        // create label for checkbox
        JLabel advancedLabel = new JLabel("Advanced Settings");
        // add label to panel
        advancedCheckPanel.add(advancedLabel, BorderLayout.EAST);
        // Create Bitmap button
        JButton createMapButton = new JButton("Create BitMap");
        createMapButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // save file to any directory
                JFileChooser fc = new JFileChooser();
                int retVal;
                retVal = fc.showSaveDialog(null);
                if (retVal == JFileChooser.APPROVE_OPTION) {
                    saveBitMap(row, col, fc.getSelectedFile().getAbsolutePath());
                } else {
                    System.out.println("Save Operation Canceled");
                }
            }
        });
        advancedCheckPanel.add(createMapButton, BorderLayout.EAST);
        // Add panel to pane
        colorPickerPanel.add(advancedCheckPanel, BorderLayout.SOUTH);
        setUpSliders();
        getContentPane().add(colorPickerPanel, BorderLayout.SOUTH);
        setSize(700, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(200, 200);
        setVisible(true);
    }

    private void saveBitMap(int rows, int columns, String filename) {
        Icon icon = new Icon(rows, columns);
        int pixel = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Color color = new Color(0,0,0);
                color = buttonPanel.getComponent(pixel).getBackground();
                icon.setPixel(i, j, color.getRed(), color.getGreen(), color.getBlue());
                pixel++;
            }
        }
        icon.exportBitmap(filename);
    }
// **********************
//  Mix Colors Function
// **********************
    // Mixing the colors from all three sliders
    public void mixColors() {
        currentRed = redSlider.getValue();
        currentGreen = greenSlider.getValue();
        currentBlue = blueSlider.getValue();
        currentColor.setBackground(new Color(currentRed,currentGreen,currentBlue));
        // Should repaint the button everytime a slider is changed
    }
// SETUP ADVANCED GUI
    private void showAdvancedSettingsWindow(Point button) {
        advancedSettings = new JDialog(this,true);
        setupAdvancedSettingsWindow(button);
        advancedPanel.setVisible(true);
        advancedSettings.add(advancedPanel);
        advancedSettings.setSize(350,150);
        advancedSettings.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        advancedSettings.setLocation(500,350);
        advancedSettings.setVisible(true);
    }
    private void setupAdvancedSettingsWindow(Point button) {
        advancedPanel = new JPanel();
        // Labels
        rowLabel = new JLabel("Number of Rows: ");
        colLabel = new JLabel("Number of Columns: ");
        // Text Fields
        rowsField = new JTextField(10);
        colsField = new JTextField(10);
        // Buttons with Handler
        importButton = new JButton("Import 24Bit BitMap");
        importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newBitmapSeleected = true;
                JFileChooser fc = new JFileChooser();
                int retVal;
                retVal = fc.showOpenDialog(null);
                if (retVal == JFileChooser.APPROVE_OPTION) {
                    // BRING IN BMP FILE
                    BufferedImage image = null;
                    try {
                        // Set up helpers
                        File input = new File(fc.getSelectedFile().getAbsolutePath());
                        image = ImageIO.read(input);
                        int width = image.getWidth();
                        int height = image.getHeight();
                        int count = 0;
                        ArrayList<Color> imageArray = new ArrayList<Color>();
                        // Read in the file
                        for (int i = 0; i < width; i++) {
                            for (int j = 0; j < height; j++) {
                                Color color = new Color(image.getRGB(i,j));
                                imageArray.add(color);
                            }
                        }
                        // write out the file to the program
                        for (int k = (int)button.getX(); k < button.getX() + width && k < col; k++) {
                            for (int g = (int)button.getY(); g < button.getY() + height && g < row; g++) {
                                buttonPanel.getComponent(k + col * g).setBackground(imageArray.get(count));
                                count++;
                            }
                        }
                        advancedSettings.setVisible(false);
                    } catch (Exception error) {
                        System.out.println(error.getMessage());
                    }
                } else {
                    System.out.println("Open Operation Canceled");
                }
            }
        });
        submitButton = new JButton("Add Pixels To BitMap");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // set up helpers
                int rowsToFill = Integer.parseInt(rowsField.getText());
                int colsToFill = Integer.parseInt(colsField.getText());
                // User input Validation
                if (rowsToFill <= 0 || colsToFill <= 0) {
                    System.out.println("please enter valid numbers");
                } else {
                    // Add Color based on Rows and Cols selected in Advanced Settings
                    for (int k = (int)button.getX(); k < button.getX() + colsToFill && k < col; k++) {
                        for (int g = (int)button.getY(); g < button.getY() + rowsToFill && g < row; g++) {
                            buttonPanel.getComponent(k + col * g).setBackground(new Color(currentRed,currentGreen,currentBlue));
                        }
                    }
                    advancedSettings.setVisible(false);
                }
            }
        });
        advancedPanel.setLayout(new GridLayout(3,2));
        advancedPanel.add(rowLabel);
        advancedPanel.add(rowsField);
        advancedPanel.add(colLabel);
        advancedPanel.add(colsField);
        advancedPanel.add(importButton);
        advancedPanel.add(submitButton);
    }
}