/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cellular2;

import java.awt.BorderLayout;
import javax.swing.JFrame;

/**
 *
 * @author Calvin Ashmore
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        new Main().runMain();
    }

    private void runMain() {
        JFrame frame = new JFrame("Cellular 2");

        frame.setLayout(new BorderLayout());
        frame.add(new MainPanel());
        /*
        panel = new CellularPanel();
        frame.add(panel, BorderLayout.CENTER);

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

        frame.add(startStopButton, BorderLayout.SOUTH);
         */
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    /*
    grid = new WellGrid(new SimpleWellProducer(25));
    iterator = new GridIterator(grid);
    currentData = iterator.createInitialData(new SimpleRandomFiller_d());
    nextData = new GridData();
    renderer = new SimpleRenderer(grid);
    panel.image = renderer.render(currentData);
    panel.repaint();
    op = new DiffuserOperation_d(.1f);*/
    }
    /*    JButton startStopButton;
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

    private void increment() {
    iterator.iterateData(op, nextData, currentData);
    panel.image = renderer.render(currentData);
    panel.repaint();

    // swap
    GridData temp = nextData;
    nextData = currentData;
    currentData = temp;
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
    }*/
}
