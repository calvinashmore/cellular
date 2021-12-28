/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cellular2;

import java.awt.BorderLayout;
import javax.swing.JApplet;

/**
 *
 * @author Calvin Ashmore
 */
public class MainApplet extends JApplet {

    @Override
    public void init() {
        //super.init();
        setLayout(new BorderLayout());
        add(new MainPanel(), BorderLayout.CENTER);
    }
}
