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
        this.simulation = new ForestFireSimulation(50, 50, new Random());

        this.paintPanel = new PaintPanel(this.simulation);

        this.frame = new JFrame("Forest Fire Simulation");

        this.rowsField = new JTextField("50", 4);

        this.columnsField = new JTextField("50", 4);

        this.startPauseButton = new JButton("Pause");

        this.statusLabel = new JLabel("Running -  Weather: None");

        this.createMapButton = new JButton("Create New Map");

        this.weatherBox = new JComboBox<String>(
                new String[] {
                        "None",
                        "Rain",
                        "Heatwave",
                        "Lightning",
                        "Wind"
                });

        this.windDirectionBox = new JComboBox<String>(
                new String[] {
                        "North",
                        "East",
                        "South",
                        "West"
                });
        this.windDirectionBox.setEnabled(false);

        this.applyWeatherButton = new JButton("Apply Weather");

        this.timer = new Timer(
                500,
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        App.this.simulation.update();
                        App.this.paintPanel.repaint();
                    }
                });

        configureFrame();

        configureActions();
    }

    private void configureFrame() {
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.frame.setLayout(new BorderLayout(8, 8));

        JPanel mapControlPanel = new JPanel(new FlowLayout());

        mapControlPanel.add(new JLabel("Rows:"));
        mapControlPanel.add(this.rowsField);

        mapControlPanel.add(new JLabel("Columns:"));
        mapControlPanel.add(this.columnsField);

        mapControlPanel.add(this.createMapButton);
        mapControlPanel.add(this.startPauseButton);

        JPanel weatherControlPanel = new JPanel(new FlowLayout());

        weatherControlPanel.add(new JLabel("Weather:"));
        weatherControlPanel.add(this.weatherBox);
        weatherControlPanel.add(new JLabel("Direction:"));
        weatherControlPanel.add(this.windDirectionBox);
        weatherControlPanel.add(this.applyWeatherButton);

        JPanel controlPanel = new JPanel(new GridLayout(2, 1));
        controlPanel.add(mapControlPanel);
        controlPanel.add(weatherControlPanel);

        JScrollPane scrollPane = new JScrollPane(this.paintPanel);

        scrollPane.setPreferredSize(new Dimension(650, 650));

        this.frame.add(controlPanel, BorderLayout.NORTH);

        this.frame.add(scrollPane, BorderLayout.CENTER);

        this.frame.add(this.statusLabel, BorderLayout.SOUTH);

        this.frame.pack();
        this.frame.setLocationRelativeTo(null);
    }

    private void configureActions() {
        this.startPauseButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(
                            ActionEvent event) {

                        toggleSimulation();
                    }
                });

        this.createMapButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(
                            ActionEvent event) {

                        createNewMap();
                    }
                });

        this.weatherBox.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(
                            ActionEvent event) {

                        String selectedWeather = (String) App.this.weatherBox
                                .getSelectedItem();

                        App.this.windDirectionBox.setEnabled(
                                "Wind".equals(selectedWeather));
                    }
                });

        this.applyWeatherButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(
                            ActionEvent event) {

                        applySelectedWeather();
                    }
                });

        this.paintPanel.addMouseListener(
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(
                            MouseEvent event) {

                        igniteClickedCell(event);
                    }
                });
    }

    private void toggleSimulation() {
        if (this.timer.isRunning()) {
            this.timer.stop();
            this.startPauseButton.setText("Start");
            this.statusLabel.setText("Paused");
        } else {
            this.timer.start();
            this.startPauseButton.setText("Pause");
            this.statusLabel.setText("Running");
        }
    }

    private void createNewMap() {
        boolean timerWasRunning = this.timer.isRunning();

        this.timer.stop();

        try {
            int rows = Integer.parseInt(this.rowsField.getText().trim());

            int columns = Integer.parseInt(this.columnsField.getText().trim());

            if (rows > 100 || columns > 100) {
                throw new IllegalArgumentException(
                        "Rows and columns cannot exceed 100.");
            }

            ForestFireSimulation newSimulation = new ForestFireSimulation(rows, columns, new Random());

            this.simulation = newSimulation;

            this.paintPanel.setSimulation(newSimulation);

            this.weatherBox.setSelectedItem("None");

            this.frame.pack();

            this.statusLabel.setText(
                    "New map created: " + rows + " x " + columns);

        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(
                    this.frame,
                    "Rows and columns must be whole numbers.",
                    "Invalid input",
                    JOptionPane.ERROR_MESSAGE);

        } catch (IllegalArgumentException exception) {
            JOptionPane.showMessageDialog(
                    this.frame,
                    exception.getMessage(),
                    "Invalid map size",
                    JOptionPane.ERROR_MESSAGE);

        } finally {
            if (timerWasRunning) {
                this.timer.start();
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

        Grid.Position position = this.paintPanel.getGridPositionAt(event.getX(), event.getY());

        if (position == null) {
            return;
        }

        int row = position.getRow();
        int column = position.getColumn();

        Cell cell = this.simulation.getGrid().getCell(row, column);

        if (cell.isBurning()) {
            this.statusLabel.setText(
                    "This Cell is already burning.");

            return;
        }

        if (!cell.canBurn()) {
            this.statusLabel.setText(
                    "This Cell cannot burn.");

            return;
        }

        this.simulation.igniteCell(
                row,
                column,
                10);

        this.paintPanel.repaint();

        this.statusLabel.setText("Fire started at row " + row + ", column " + column);
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
        this.frame.setVisible(true);
        this.timer.start();
    }
}
