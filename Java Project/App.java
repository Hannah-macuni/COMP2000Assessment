import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.SwingUtilities;

public class App {
    private JFrame frame;
    private ForestFireSimulation simulation;
    private PaintPanel paintPanel;
    private Timer timer;

    private JTextField rowsField;
    private JTextField columnsField;
    private JButton startPauseButton;
    private JButton createMapButton;
    private JButton applyWeatherButton;
    private JLabel statusLabel;
    private JComboBox<String> weatherBox;
    private JComboBox<String> windDirectionBox;

    public App() {
        simulation = new ForestFireSimulation(50, 50, new Random());

        paintPanel = new PaintPanel(simulation);

        frame = new JFrame("Forest Fire Simulation");

        rowsField = new JTextField("50", 4);

        columnsField = new JTextField("50", 4);

        startPauseButton = new JButton("Pause");

        statusLabel = new JLabel("Running -  Weather: None");

        createMapButton = new JButton("Create New Map");

        weatherBox = new JComboBox<String>(
                new String[] {
                        "None",
                        "Rain",
                        "Heatwave",
                        "Lightning",
                        "Wind"
                });

        windDirectionBox = new JComboBox<String>(
                new String[] {
                        "North",
                        "East",
                        "South",
                        "West"
                });
        windDirectionBox.setEnabled(false);

        applyWeatherButton = new JButton("Apply Weather");

        timer = new Timer(
                500,
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        simulation.update();
                        paintPanel.repaint();
                    }
                });

        configureFrame();

        configureActions();
    }

    private void configureFrame() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLayout(new BorderLayout(8, 8));

        JPanel mapControlPanel = new JPanel(new FlowLayout());

        mapControlPanel.add(new JLabel("Rows:"));
        mapControlPanel.add(rowsField);

        mapControlPanel.add(new JLabel("Columns:"));
        mapControlPanel.add(columnsField);

        mapControlPanel.add(createMapButton);
        mapControlPanel.add(startPauseButton);

        JPanel weatherControlPanel = new JPanel(new FlowLayout());

        weatherControlPanel.add(new JLabel("Weather:"));
        weatherControlPanel.add(weatherBox);
        weatherControlPanel.add(new JLabel("Direction:"));
        weatherControlPanel.add(windDirectionBox);
        weatherControlPanel.add(applyWeatherButton);

        JPanel controlPanel = new JPanel(new GridLayout(2, 1));
        controlPanel.add(mapControlPanel);
        controlPanel.add(weatherControlPanel);

        JScrollPane scrollPane = new JScrollPane(paintPanel);

        scrollPane.setPreferredSize(new Dimension(650, 650));

        frame.add(controlPanel, BorderLayout.NORTH);

        frame.add(scrollPane, BorderLayout.CENTER);

        frame.add(statusLabel, BorderLayout.SOUTH);

        frame.pack();
        frame.setLocationRelativeTo(null);
    }

    private void configureActions() {
        startPauseButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(
                            ActionEvent event) {

                        toggleSimulation();
                    }
                });

        createMapButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(
                            ActionEvent event) {

                        createNewMap();
                    }
                });

        weatherBox.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(
                            ActionEvent event) {

                        String selectedWeather = (String) weatherBox
                                .getSelectedItem();

                        windDirectionBox.setEnabled(
                                "Wind".equals(selectedWeather));
                    }
                });

        applyWeatherButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(
                            ActionEvent event) {

                        applySelectedWeather();
                    }
                });

        paintPanel.addMouseListener(
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(
                            MouseEvent event) {

                        igniteClickedCell(event);
                    }
                });
    }

    private void toggleSimulation() {
        if (timer.isRunning()) {
            timer.stop();
            startPauseButton.setText("Start");
            statusLabel.setText("Paused");
        } else {
            timer.start();
            startPauseButton.setText("Pause");
            statusLabel.setText("Running");
        }
    }

    private void createNewMap() {
        boolean timerWasRunning = timer.isRunning();

        timer.stop();

        try {
            int rows = Integer.parseInt(rowsField.getText().trim());

            int columns = Integer.parseInt(columnsField.getText().trim());

            if (rows > 100 || columns > 100) {
                throw new IllegalArgumentException(
                        "Rows and columns cannot exceed 100.");
            }

            ForestFireSimulation newSimulation = new ForestFireSimulation(rows, columns, new Random());

            simulation = newSimulation;

            paintPanel.setSimulation(newSimulation);

            weatherBox.setSelectedItem("None");

            frame.pack();

            statusLabel.setText(
                    "New map created: " + rows + " x " + columns);

        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(
                    frame,
                    "Rows and columns must be whole numbers.",
                    "Invalid input",
                    JOptionPane.ERROR_MESSAGE);

        } catch (IllegalArgumentException exception) {
            JOptionPane.showMessageDialog(
                    frame,
                    exception.getMessage(),
                    "Invalid map size",
                    JOptionPane.ERROR_MESSAGE);

        } finally {
            if (timerWasRunning) {
                timer.start();
            }
        }
    }

    private void applySelectedWeather() {
        String selectedWeather = (String) this.weatherBox.getSelectedItem();
        String selectedDirection = (String) this.windDirectionBox.getSelectedItem();
        String weatherDescription = selectedWeather;

        if ("Wind".equals(selectedWeather)) {
            this.simulation.getWeatherManager().createWeather(selectedWeather, selectedDirection);
            this.statusLabel.setText("Weather applied: Wind " + weatherDescription);
        } else {
            this.simulation.getWeatherManager().createWeather(selectedWeather);
            this.statusLabel.setText("Weather applied: " + weatherDescription);
        }
    }

    private void igniteClickedCell(
            MouseEvent event) {

        if (event.getButton() != MouseEvent.BUTTON1) {
            return;
        }

        Grid.Position position = paintPanel.getGridPositionAt(event.getX(), event.getY());

        if (position == null) {
            return;
        }

        int row = position.getRow();
        int column = position.getColumn();

        Cell cell = simulation.getGrid().getCell(row, column);

        if (cell.isBurning()) {
            statusLabel.setText(
                    "This Cell is already burning.");

            return;
        }

        if (!cell.canBurn()) {
            statusLabel.setText(
                    "This Cell cannot burn.");

            return;
        }

        simulation.igniteCell(
                row,
                column,
                10);

        paintPanel.repaint();

        statusLabel.setText("Fire started at row " + row + ", column " + column);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                        App app = new App();
                        app.showApp();
                    }
                });
    }

    private void showApp() {
        frame.setVisible(true);
        timer.start();
    }
}
