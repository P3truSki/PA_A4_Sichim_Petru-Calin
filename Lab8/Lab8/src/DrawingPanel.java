import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

class DrawingPanel extends JPanel {
    final MainFrame frame;
    Cell[][] grid;
    int rows = 0, cols = 0;
    int cellSize = 30;

    public DrawingPanel(MainFrame frame) {
        this.frame = frame;
        setBackground(Color.WHITE);
        initHomeworkMouseListener();
    }

    //compulsory
    public void initMaze(int r, int c) {
        this.rows = r;
        this.cols = c;
        grid = new Cell[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = new Cell(i, j);
            }
        }
        repaint();
    }

    public void createRandomMaze() {
        if (rows <= 0 || cols <= 0) return;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = new Cell(i, j);
            }
        }
        repaint();

        int animationSpeedMs = 1000;
        PerfectMazeBuilder builder = new PerfectMazeBuilder(grid, this, animationSpeedMs);
        builder.generateAnimated();
    }

    //compulsory
    public void resetMaze() {
        if (rows > 0 && cols > 0) {
            initMaze(rows, cols);
        }
    }

    //compulsory
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (grid == null) return;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(Color.BLACK);

        int offsetX = (getWidth() - cols * cellSize) / 2;
        int offsetY = (getHeight() - rows * cellSize) / 2;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int x = offsetX + j * cellSize;
                int y = offsetY + i * cellSize;

                g2d.setColor(Color.GRAY);
                g2d.fillRect(x, y, cellSize, cellSize);

                g2d.setColor(Color.BLACK);
                if (grid[i][j].top) g2d.drawLine(x, y, x + cellSize, y);
                if (grid[i][j].bottom) g2d.drawLine(x, y + cellSize, x + cellSize, y + cellSize);
                if (grid[i][j].left) g2d.drawLine(x, y, x, y + cellSize);
                if (grid[i][j].right) g2d.drawLine(x + cellSize, y, x + cellSize, y + cellSize);
            }
        }
    }

    //homework
    private void initHomeworkMouseListener() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (grid == null) return;
                int offsetX = (getWidth() - cols * cellSize) / 2;
                int offsetY = (getHeight() - rows * cellSize) / 2;

                int mx = e.getX() - offsetX;
                int my = e.getY() - offsetY;

                if (mx < 0 || my < 0 || mx >= cols * cellSize || my >= rows * cellSize) return;

                int c = mx / cellSize;
                int r = my / cellSize;

                int inX = mx % cellSize;
                int inY = my % cellSize;

                int distTop = inY;
                int distBottom = cellSize - inY;
                int distLeft = inX;
                int distRight = cellSize - inX;

                int min = Math.min(Math.min(distTop, distBottom), Math.min(distLeft, distRight));

                if (min == distTop) {
                    grid[r][c].top = !grid[r][c].top;
                    if (r > 0) grid[r - 1][c].bottom = grid[r][c].top;
                } else if (min == distBottom) {
                    grid[r][c].bottom = !grid[r][c].bottom;
                    if (r < rows - 1) grid[r + 1][c].top = grid[r][c].bottom;
                } else if (min == distLeft) {
                    grid[r][c].left = !grid[r][c].left;
                    if (c > 0) grid[r][c - 1].right = grid[r][c].left;
                } else if (min == distRight) {
                    grid[r][c].right = !grid[r][c].right;
                    if (c < cols - 1) grid[r][c + 1].left = grid[r][c].right;
                }
                repaint();
            }
        });
    }

    //homework
    public void validateMaze() {
        if (grid == null) return;

        boolean[][] visited = new boolean[rows][cols];
        Queue<Cell> queue = new LinkedList<>();
        Cell exitCell = grid[rows - 1][cols - 1];
        queue.add(exitCell);
        visited[rows - 1][cols - 1] = true;

        while (!queue.isEmpty()) {
            Cell curr = queue.poll();
            int r = curr.row;
            int c = curr.col;

            if (!curr.top && r > 0 && !visited[r - 1][c]) {
                visited[r - 1][c] = true;
                queue.add(grid[r - 1][c]);
            }
            if (!curr.bottom && r < rows - 1 && !visited[r + 1][c]) {
                visited[r + 1][c] = true;
                queue.add(grid[r + 1][c]);
            }
            if (!curr.left && c > 0 && !visited[r][c - 1]) {
                visited[r][c - 1] = true;
                queue.add(grid[r][c - 1]);
            }
            if (!curr.right && c < cols - 1 && !visited[r][c + 1]) {
                visited[r][c + 1] = true;
                queue.add(grid[r][c + 1]);
            }
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (!visited[i][j]) {
                    JOptionPane.showMessageDialog(this,
                            "Cell [" + i + "][" + j + "] is blocked");
                    return;
                }
            }
        }
        JOptionPane.showMessageDialog(this, "Maze is traversible");
    }

    //homework
    public void exportPNG() {
        if (grid == null) return;
        BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        paintComponent(g2d);
        g2d.dispose();
        try {
            ImageIO.write(image, "PNG", new File("maze.png"));
            JOptionPane.showMessageDialog(this, "Exported to maze.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //homework
    public void saveMaze() {
        if (grid == null) return;
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("maze.dat"))) {
            out.writeObject(grid);
            out.writeInt(rows);
            out.writeInt(cols);
            JOptionPane.showMessageDialog(this, "Maze saved to maze.dat");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //homework
    public void loadMaze() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("maze.dat"))) {
            grid = (Cell[][]) in.readObject();
            rows = in.readInt();
            cols = in.readInt();
            frame.configPanel.rowsSpinner.setValue(rows);
            frame.configPanel.colsSpinner.setValue(cols);
            repaint();
            JOptionPane.showMessageDialog(this, "Maze loaded successfully");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "No saved maze found or error loading.");
        }
    }
}
