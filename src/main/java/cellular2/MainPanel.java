/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cellular2;

import cellular2.algorithm.GridData;
import cellular2.algorithm.GridIterator;
import cellular2.algorithm.GridOperation;
import cellular2.algorithm.WellGrid;
import cellular2.implementors.EntropyOperation_d;
import cellular2.implementors.InputFunction_9Rule;
import cellular2.implementors.PerturbedWellProducer;
import cellular2.implementors.SimpleIntegratorOperation_d;
import cellular2.implementors.SimpleRandomFiller_d;
import cellular2.implementors.TorusTopology;
import cellular2.renderer.BlurryRenderer_d;
import cellular2.renderer.GridRenderer;
import cellular2.renderer.SimpleRenderer_d;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Calvin Ashmore
 */
public class MainPanel extends JPanel {

    public MainPanel() {
        //JFrame frame = new JFrame("Cellular 2");

        setLayout(new BorderLayout());

        panel = new CellularPanel();
        add(panel, BorderLayout.CENTER);

        startStopButton = new JButton("Start");
        startStopButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (started) {
                    stopScheduler();
                } else {
                    startScheduler();
                }
            }
        });

        add(startStopButton, BorderLayout.SOUTH);


        grid = new WellGrid(new PerturbedWellProducer(100), new TorusTopology());
        iterator = new GridIterator(grid);
        currentData = iterator.createInitialData(new SimpleRandomFiller_d());
        nextData = new GridData();
        renderer = new BlurryRenderer_d(grid);//SimpleRenderer_d(grid);
        panel.image = renderer.render(currentData);
        panel.repaint();

        //op = new DiffuserOperation_d(.1f);

        //op = new SimpleIntegratorOperation_d(.05f, new InputFunction_3Rule(-.25, .5, -1.4, .5));
        op = new SimpleIntegratorOperation_d(.05f, new InputFunction_9Rule(0,
                5, 5, -1,
                17, -8, -19,
                2, -5, -5));
//                .7, -.5, .5,
//                -.5, .0, -.5,
//                .5, -.5, .7));
        op2 = new EntropyOperation_d();
    //op2 = new IdentityOperation();
    }
    JButton startStopButton;
    ScheduledFuture<?> future;
    boolean started = false;

    private void startScheduler() {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        future = executor.scheduleAtFixedRate(new Runnable() {

            public void run() {
                increment();
            }
        }, 0, 100L, TimeUnit.MILLISECONDS);
        started = true;
        startStopButton.setText("Stop");
    }

    private void stopScheduler() {
        future.cancel(false);
        started = false;
        startStopButton.setText("Start");
    }
    private WellGrid grid;
    private GridData currentData;
    private GridData nextData;
    private CellularPanel panel;
    private GridIterator iterator;
    private GridRenderer renderer;
    private GridOperation op;
    private GridOperation op2;

    private void increment() {
        iterator.iterateData(op, nextData, currentData);
        iterator.iterateData(op2, currentData, nextData);
        panel.image = renderer.render(nextData);
        panel.repaint();

    // swap
//        GridData temp = nextData;
//        nextData = currentData;
//        currentData = temp;
    }

    private class CellularPanel extends JPanel {

        BufferedImage image;

        public CellularPanel() {
            setPreferredSize(new Dimension(500, 500));
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            if (image != null) {
                g.drawImage(image, 0, 0, null);
            }
        }
    }
}
