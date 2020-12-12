import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import java.awt.Color;

public class IconPixel extends JButton implements ActionListener {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private int masterX;
    private int masterY;

    public IconPixel(int row, int col) {
        addActionListener(this);
        setRows(row);
        setCols(col);
    }

// GETTERS AND SETTERS
    public int getRows() {
        return masterX;
    }
    public int getCols() {
        return masterY;
    }
    public void setRows(int row) {
        if (row >= 0) {
            masterX = row;
        } else {
            masterX = 0;
        }
    }
    public void setCols(int col) {
        if (col >= 0) {
            masterY = col;
        } else {
            masterY = 0;
        }
    }

    public Color getColor() {
        return getBackground();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

    }

}