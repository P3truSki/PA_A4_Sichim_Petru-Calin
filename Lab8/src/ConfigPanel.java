import javax.swing.*;

//compulsory
class ConfigPanel extends JPanel {
    final MainFrame frame;
    JSpinner rowsSpinner, colsSpinner;
    JButton drawBtn;

    public ConfigPanel(MainFrame frame) {
        this.frame = frame;
        init();
    }

    private void init() {
        add(new JLabel("Rows:"));
        rowsSpinner = new JSpinner(new SpinnerNumberModel(10, 2, 30, 1));
        add(rowsSpinner);

        add(new JLabel("Cols:"));
        colsSpinner = new JSpinner(new SpinnerNumberModel(10, 2, 30, 1));
        add(colsSpinner);

        drawBtn = new JButton("Draw Cells");
        drawBtn.addActionListener(e -> {
            int rows = (int) rowsSpinner.getValue();
            int cols = (int) colsSpinner.getValue();
            frame.canvas.initMaze(rows, cols);
        });
        add(drawBtn);
    }
}
