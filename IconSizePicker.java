import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class IconSizePicker extends JFrame implements ActionListener {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    // Labels for prompts
    private JLabel rows;
    private JLabel columns;
    // Text Fields for promts
    private JTextField rowsInputField;
    private JTextField columnsInputField;
    // Buttons
    private JButton submitButton;
    // Panel to hold input form
    private JPanel rowsColoumnsFormPanel;

    // Construction
    public IconSizePicker() {
        super("Enter the Dimensions of your Icon");

        // Setup
        setUpGUI();
    }

    public void setUpGUI() {
        // Labels
        rows = new JLabel("Number of Rows: ");
        columns = new JLabel("Number of Columns: ");
        // Text Fields
        rowsInputField = new JTextField(10);
        columnsInputField = new JTextField(10);
        // Buttons with Handler
        submitButton = new JButton("Create Bit Map");
        submitButton.addActionListener((ActionListener) this);

        // Create Panel and fill it
        rowsColoumnsFormPanel = new JPanel();
        rowsColoumnsFormPanel.setLayout(new GridLayout(3,2));
        rowsColoumnsFormPanel.add(rows);
        rowsColoumnsFormPanel.add(rowsInputField);
        rowsColoumnsFormPanel.add(columns);
        rowsColoumnsFormPanel.add(columnsInputField);
        rowsColoumnsFormPanel.add(submitButton);


        //create the layout and add the major elements
        setLayout(new BorderLayout());

        add(rowsColoumnsFormPanel, BorderLayout.CENTER);
        //set the size of the window to be 400 pixels wide
        //and 300 pixels tall
        setSize(350, 150);

        //kill the program when the user clicks the 'x'
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //set the location on the screen where the window will appear
        setLocation(100, 100);

        //make the window visible
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int rows = Integer.parseInt(rowsInputField.getText());
        int cols = Integer.parseInt(columnsInputField.getText());
        if (rows <= 0 || cols <= 0) {
            System.out.println("Please enter valid numbers");
        } else {
            IconFrame frame = new IconFrame(rows, cols);
        }
    }
}