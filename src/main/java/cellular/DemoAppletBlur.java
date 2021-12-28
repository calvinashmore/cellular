/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cellular;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import utils.Pair;
import utils.cellularautomata.CellularAutomata1d;
import utils.cellularautomata.CellularAutomata1d2;
import utils.cellularautomata.CellularAutomata1d2_cont;
import utils.cellularautomata.CellularAutomata1d_cont;
import utils.linear.Color;
import utils.linear.grid.Buffer_d;
import utils.linear.grid.ColorGrid;

/**
 *
 * @author Calvin Ashmore
 */
public class DemoAppletBlur extends JApplet {

    private static final int RESOLUTION = 500;
    private JLabel imageLabel;
    private JLabel captionLabel;
    private boolean isCalculating;
    private long calculationStart;
    private ScheduledExecutorService executor;
    private Thread calculateThread;
    private JComboBox algorithmsBox;
    private JComboBox inputsBox;
    private JComboBox colorsBox;
    private JComboBox blurBox;

    @Override
    public void init() {
        super.init();

        imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(RESOLUTION, RESOLUTION));
        captionLabel = new JLabel("Loading...");

        JPanel lowerPanel = new JPanel(new BorderLayout());
        lowerPanel.add(captionLabel, BorderLayout.WEST);

        JPanel controlsPanel = new JPanel();
        lowerPanel.add(controlsPanel, BorderLayout.EAST);

        // build controls

        algorithmsBox = new JComboBox(new String[]{
            "Discrete 1", "Discrete 2", "Continuous 1", "Continuous 2"
        });
        algorithmsBox.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent e) {
                colorsBox.setEnabled(algorithmsBox.getSelectedIndex() < 2);
            }
        });

        inputsBox = new JComboBox(new Integer[]{2, 3, 4, 5});
        colorsBox = new JComboBox(new Integer[]{2, 3, 4, 5});

        blurBox = new JComboBox(new Pair[]{
            new Pair(2.0, 4),
            new Pair(2.0, 1),
            new Pair(1.1, 5),
            new Pair(1.1, 2),
        });
        
        controlsPanel.add(blurBox);
        controlsPanel.add(algorithmsBox);
        controlsPanel.add(inputsBox);
        controlsPanel.add(colorsBox);

        inputsBox.setSelectedIndex(1);
        colorsBox.setSelectedIndex(1);

        algorithmsBox.setSelectedIndex(new Random().nextInt(4));

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(imageLabel, BorderLayout.CENTER);
        getContentPane().add(lowerPanel, BorderLayout.SOUTH);

        imageLabel.addMouseListener(new RefreshListener());

    }

    @Override
    public void start() {
        super.start();
        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(new Runnable() {

            public void run() {
                updateUI();
            }
        }, 0, 200, TimeUnit.MILLISECONDS);
    }

    @Override
    public void stop() {
        super.stop();
        executor.shutdown();

        // bad practice, I know
        if (isCalculating) {
            calculateThread.stop();
        }
    }

    private class RefreshListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            startCalculate();
        }
    }

    private void updateUI() {
        if (isCalculating) {

            long beenCalculatingFor = System.currentTimeMillis() - calculationStart;

            //if(beenCalculatingFor < 3000) {
            //    captionLabel.setText("Thinking...");
            //} else
            if (beenCalculatingFor < 10000) {
                int seconds = (int) (beenCalculatingFor / 1000);
                captionLabel.setText("Thinking... (" + seconds + ")");
            } else {
                // try to shut it down
                captionLabel.setText("I'm confused!");
                calculateThread.stop();
                isCalculating = false;
            }

        } else {
            captionLabel.setText("Click above to make an image");
        }
    }

    private void startCalculate() {

        // don't start calculating if already calculating.
        if (isCalculating) {
            return;
        }

        calculateThread =
                new Thread(new Runnable() {

            public void run() {
                doCalculate();
            }
        }, "Calculation Thread");
        calculateThread.start();
    }

    private void doCalculate() {
        isCalculating = true;
        calculationStart = System.currentTimeMillis();

        Buffer_d data = new Buffer_d(100, 100);

        int algorithm = algorithmsBox.getSelectedIndex();
        int inputs = (Integer) inputsBox.getSelectedItem();
        int colors = (Integer) colorsBox.getSelectedItem();
        if (algorithm == 0) {
            new CellularAutomata1d(data, inputs, colors);
        } else if (algorithm == 1) {
            new CellularAutomata1d2(data, inputs, colors);
        } else if (algorithm == 2) {
            new CellularAutomata1d_cont(data, inputs);
        } else if (algorithm == 3) {
            new CellularAutomata1d2_cont(data, inputs);
        }

        Buffer_d dataLarge1 = BlurTool.scaleBuffer(data, 500);
        Buffer_d dataLarge2 = new Buffer_d(500, 500);
        
        Pair<Double, Integer> blurSettings = (Pair<Double, Integer>) blurBox.getSelectedItem();
        Buffer_d out = new BlurTool(blurSettings.getLeft()).process(dataLarge1, dataLarge2, blurSettings.getRight());

        ColorGrid cg = flesh(out);
        ColorGrid cg1 = cg;
        //ArrayGrid<Color> cg0 = cg.rescale(RESOLUTION, RESOLUTION, GridScaleMethod.square);

        //ColorGrid cg1 = new ColorGrid(cg0);

        imageLabel.setIcon(new ImageIcon(cg1.makeImage()));

        isCalculating = false;
    }

    private ColorGrid flesh(Buffer_d data) {

        ColorGrid cg = new ColorGrid(data.getXRes(), data.getYRes());

        for (int x = 0; x < data.getXRes(); x++) {
            for (int y = 0; y < data.getYRes(); y++) {
                double d = 1 - data.getValue(x, y).val;
                cg.setValue(x, y, new Color(d, d, d));
            }
        }

        return cg;
    }
}
