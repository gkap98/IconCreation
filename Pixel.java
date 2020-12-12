/*
*
*   Written By: -> Gavin Kaepernick, Carthage College
*   Written In: -> Java
*   Description:
*       This class helps handle RGB Color Coding for drawing pictures
*
*/

public class Pixel {

// *****************
//  Private Colors
// *****************
    private int RGB;
    // Binary masks that make future calculations easier.
    private int redMask = 0b11111111;
    private int greenMask = 0b1111111100000000;
    private int blueMask = 0b111111110000000000000000;

// *****************
//   Constructor
// *****************
    public Pixel() {
        RGB = 255 << 16 | 255 << 8 | 255;
    }
    public Pixel(int r, int g, int b) {
        /*
        BIT MODEL - Zeros of the unused bits (MUST BE ZEROS)
            |00000000|BBBBBBBB|GGGGGGGG|RRRRRRRR|
            Need to take each of the getters and use them and the >> <<
            but operators in order to put all three values into the RGB int
            From here will need to figure out the HEX transition
        */
    // Step 1 : Set RGB using Colors ->
        RGB = b << 16 | g << 8 | r;
    }
// *****************
//     Setters
// *****************
    // Should be between 0 - 225
    public void setRed(int r) {
        if (r > 255 || r < 0) {
            return;
        } else {
            // For the model shown above in the constructor
            RGB = getBlue() << 16 | getGreen() << 8 | r;
        }
    }
    public void setGreen(int g) {
        if (g > 255 || g < 0) {
            return;
        } else {
            // For the model shown above in the constructor
            RGB = getBlue() << 16 | g << 8 | getRed();
        }
    }
    public void setBlue(int b) {
        if (b > 255 || b < 0) {
            return;
        } else {
            // For the model shown above in the constructor
            RGB = b << 16 | getGreen() << 8 | getRed();
        }
    }
// *****************
//     Getters
// *****************
    public int getRed() {
        return RGB & redMask;
    }
    public int getGreen() {
        return (RGB & greenMask) >> 8;
    }
    public int getBlue() {
        return (RGB & blueMask) >> 16;
    }
    public int getRGB() {
        return RGB;
    }
    public String getHex() {        // Calculating hexidecimal values based on the RGB values of a given coordinate.
        String hex = String.format("#%02X%02X%02X", getRed(), getGreen(), getBlue());
        return hex;
    }
// *****************
//    Functions
// *****************
    public String printHex() {
        return getHex();
    }
}