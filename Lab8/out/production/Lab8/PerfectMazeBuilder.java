import javax.swing.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PerfectMazeBuilder {
    private Cell[][] grid;
    private int rows;
    private int cols;
    private JPanel drawingPanel;
    private int animationDelayMs;
    private boolean[][] visited;

    public PerfectMazeBuilder(Cell[][] grid, JPanel drawingPanel, int animationDelayMs) {
        this.grid = grid;
        this.rows = grid.length;
        this.cols = grid[0].length;
        this.drawingPanel = drawingPanel;
        this.animationDelayMs = animationDelayMs;
        this.visited = new boolean[rows][cols];
    }

    public void generateAnimated() {
        new Thread(() -> {

            carvePassagesFrom(0, 0);
            //CAND E GATA RECURSIVITATEA SE OPRESTE
            SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(drawingPanel, "Maze Generation Complete!")
            );
        }).start();
    }

    // Reverse DFS algorithm
    private void carvePassagesFrom(int r, int c) {
        visited[r][c] = true;

        Integer[] directions = {0, 1, 2, 3};
        List<Integer> dirList = Arrays.asList(directions);
        Collections.shuffle(dirList);

        for (int dir : dirList) {
            int nextR = r;
            int nextC = c;

            switch (dir) {
                case 0: nextR = r - 1; break;
                case 1: nextC = c + 1; break;
                case 2: nextR = r + 1; break;
                case 3: nextC = c - 1; break;
            }

            if (nextR >= 0 && nextR < rows && nextC >= 0 && nextC < cols && !visited[nextR][nextC]) {

                if (dir == 0) {
                    grid[r][c].top = false; grid[nextR][nextC].bottom = false;
                }
                else if (dir == 1) {
                    grid[r][c].right = false; grid[nextR][nextC].left = false;
                }
                else if (dir == 2) {
                    grid[r][c].bottom = false; grid[nextR][nextC].top = false;
                }
                else if (dir == 3) {
                    grid[r][c].left = false; grid[nextR][nextC].right = false;
                }

                SwingUtilities.invokeLater(() -> drawingPanel.repaint());

                try {
                    Thread.sleep(animationDelayMs);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                carvePassagesFrom(nextR, nextC);
            }
        }
    }
}