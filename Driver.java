import javax.swing.UIManager;

public class Driver {
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName() );
        } catch (Exception e) {
            e.printStackTrace();
        }
        IconSizePicker test = new IconSizePicker();
    }
}