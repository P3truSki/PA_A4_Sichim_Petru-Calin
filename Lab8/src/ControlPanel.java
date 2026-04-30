import javax.swing.*;

//compulsory
class ControlPanel extends JPanel {
    final MainFrame frame;
    JButton createBtn, resetBtn, exitBtn;

    //homework
    JButton validateBtn, exportBtn, saveBtn, loadBtn;

    public ControlPanel(MainFrame frame) {
        this.frame = frame;
        initCompulsory();
        initHomework();
    }

    //compulsory
    private void initCompulsory() {
        createBtn = new JButton("Create");
        createBtn.addActionListener(e -> frame.canvas.createRandomMaze());
        add(createBtn);

        resetBtn = new JButton("Reset");
        resetBtn.addActionListener(e -> frame.canvas.resetMaze());
        add(resetBtn);

        exitBtn = new JButton("Exit");
        exitBtn.addActionListener(e -> System.exit(0));
        add(exitBtn);
    }

    //homework
    private void initHomework() {
        validateBtn = new JButton("Validate");
        validateBtn.addActionListener(e -> frame.canvas.validateMaze());
        add(validateBtn);

        exportBtn = new JButton("Export PNG");
        exportBtn.addActionListener(e -> frame.canvas.exportPNG());
        add(exportBtn);

        saveBtn = new JButton("Save");
        saveBtn.addActionListener(e -> frame.canvas.saveMaze());
        add(saveBtn);

        loadBtn = new JButton("Load");
        loadBtn.addActionListener(e -> frame.canvas.loadMaze());
        add(loadBtn);
    }
}
