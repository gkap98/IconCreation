/*
*
*   Written By: -> Gavin Kaepernick, Carthage College
*   Written In: -> Java
*   Description:
*       This class helps handle drawing pictures Pixel.java
*
*/

import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Icon {

    private ArrayList<ArrayList<Pixel>> pixels;

// *****************
//   Constructors
// *****************
    public Icon() {                                  // Constructor for no given values.         Pre set to 40x40 icon
        pixels = new ArrayList<ArrayList<Pixel>>();
        for (int i = 0; i < 40; i++) {
            pixels.add(new ArrayList<Pixel>());
            for (int j = 0; j < 40; j++) {
                pixels.get(i).add(new Pixel());
            }
        }
    }
    public Icon(int rows, int columns) {             // Constructor FOR given values.            NO Presets
        pixels = new ArrayList<ArrayList<Pixel>>();
        for (int i = 0; i < columns; i++) {
            pixels.add(new ArrayList<Pixel>());
            for (int j = 0; j < rows; j++) {
                pixels.get(i).add(new Pixel());
            }
        }
    }

// *****************
//     Setters
// *****************
    public void setPixel(int row, int column, int red, int green, int blue) {   // Set pixel data at given coordinates.
        // Pixel pixel = getPixel(column, row);
        // pixel.setRed(red);
        // pixel.setGreen(green);
        // pixel.setBlue(blue);

        pixels.get(row).get(column).setRed(red);
        pixels.get(row).get(column).setGreen(green);
        pixels.get(row).get(column).setBlue(blue);
    }

// *****************
//     Getters
// *****************
    public Pixel getPixel(int row, int column) {
        // Check user input for invalid data input
        if (column > pixels.size() || column < 0) {
            System.out.println("Add valid column size");
            column = 0;
        }
        // Check user input
        if (row > pixels.get(0).size() || row < 0) {
            System.out.println("Add valid column size");
            row = 0;
        }
        return pixels.get(column).get(row);
    }
    // Helper function for when it comes time to print hexidecimal values/\.
    public String toString() {
        String s = "";
        for (int i = 0; i < pixels.size(); i++) {
            for (int j = 0; j < pixels.get(i).size(); j++) {
                s += pixels.get(i).get(j).getHex() + " ";
            }
            s += "\n";
        }
        return s;
    }
// *****************
//     Methods
// *****************
    public void exportBitmap(String filename) {
        ArrayList<Byte> fileContents = new ArrayList<Byte>(); // blank ArrayList of bytes to write to .bmp file

        /* Start of BMP Header */
        fileContents.add((byte)'B');
        fileContents.add((byte)'M');
        int size = (pixels.size() * pixels.get(0).size()) * 3 + 54 + padding() * pixels.size();
        fileContents.add((byte)size);
        fileContents.add((byte)(size >> 8));
        fileContents.add((byte)(size >> 16));
        fileContents.add((byte)(size >> 24));
        fileContents.add((byte)0);
        fileContents.add((byte)0);
        fileContents.add((byte)0);
        fileContents.add((byte)0);
        fileContents.add((byte)54);
        fileContents.add((byte)0);
        fileContents.add((byte)0);
        fileContents.add((byte)0);
        /* End of BMP Header */

        /* Start of DIB Header */
        fileContents.add((byte)40);
        fileContents.add((byte)0);
        fileContents.add((byte)0);
        fileContents.add((byte)0);
        fileContents.add((byte)pixels.get(0).size());
        fileContents.add((byte)(pixels.get(0).size() >> 8));
        fileContents.add((byte)(pixels.get(0).size() >> 16));
        fileContents.add((byte)(pixels.get(0).size() >> 24));
        fileContents.add((byte)pixels.size());
        fileContents.add((byte)(pixels.size() >> 8));
        fileContents.add((byte)(pixels.size() >> 16));
        fileContents.add((byte)(pixels.size() >> 24));
        fileContents.add((byte)1);
        fileContents.add((byte)0);
        fileContents.add((byte)24);
        fileContents.add((byte)0);
        fileContents.add((byte)0);
        fileContents.add((byte)0);
        fileContents.add((byte)0);
        fileContents.add((byte)0);
        fileContents.add((byte)(size-54));
        fileContents.add((byte)0);
        fileContents.add((byte)0);
        fileContents.add((byte)0);
        for (int i = 0; i < 16; i++) {
            fileContents.add((byte)0);
        }
        /* End of DIB Header */

        /* Start of Pixel Array*/
        for (int r = pixels.size() - 1; r >= 0; r--) {
            for (int c = 0; c < pixels.get(0).size(); c++) {
                fileContents.add((byte)pixels.get(r).get(c).getBlue()); // add byte for blue value
                fileContents.add((byte)pixels.get(r).get(c).getGreen()); // add byte for green value
                fileContents.add((byte)pixels.get(r).get(c).getRed()); // add byte for red value
            }

            //add padding bytes at the end of the row
            for(int i = 0; i < padding(); i++)
            {
                fileContents.add((byte)0);
            }
        }
        try {
            FileOutputStream ofStream = new FileOutputStream(filename); // open bmp file
            try{
                for(int i = 0; i < fileContents.size(); i++)
                {
                    ofStream.write(fileContents.get(i)); // write every byte ftom the fileContents ArrayList
                }

                ofStream.close(); // close file
            } catch (IOException e) { //handle IO Exception
                System.out.println("IO Exception");
            }
        } catch(FileNotFoundException e) { // handle File Note Found Exception
            System.out.println("File Not Found");
        }

    }

    private int padding()
    {
        int pad = 0;
        if(pixels.get(0).size() % 4 != 0){ // make sure no padding will be added if the row is divisible by 4
            pad = 4 - ((pixels.get(0).size() * 3) % 4); // return how many bytes will be added to each row
        }
        return pad;
    }
}