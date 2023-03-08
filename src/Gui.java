import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.*;

public class Gui {

    private TagController controller;

    public Gui(TagController cnt) {

        controller = cnt;

        Display display = new Display();
        Shell shell = new Shell(display, SWT.SHELL_TRIM & (~SWT.RESIZE));
        shell.setText("Hello world");

        shell.open();
        shell.setSize(1280,720);


        Button b1 = new Button(shell, SWT.PUSH);

        b1.setLocation(540, 600);
        b1.setSize(200, 50);
        b1.setText("Test");


        while(!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();

    }

}
