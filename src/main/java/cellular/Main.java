/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cellular;

import javax.swing.JFrame;

/**
 *
 * @author Calvin Ashmore
 */
public class Main {

    public static void main(String args[]) {

        JFrame frame = new JFrame("Cellular test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DemoAppletBlur applet = new DemoAppletBlur();

        frame.getContentPane().add(applet);
        applet.init();
        applet.start();

        frame.pack();
        frame.setVisible(true);
    }
}
