import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// compulsory
public class Maze {
    int rows, cols;
    int exitR, exitC;
    Bunny bunny;
    List<Robot> robots = new ArrayList<>();
    Cell[][] grid;

    private volatile boolean gameRunning = true;
    private String winnerMessage = "Win!";

    public Maze(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.exitR = rows - 1;
        this.exitC = cols - 1;
        generateMazeWithWalls();
    }

    private void generateMazeWithWalls() {
        grid = new Cell[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = new Cell(i, j);
            }
        }
        boolean[][] visited = new boolean[rows][cols];
        carvePassagesFrom(0, 0, visited);
    }

    private void carvePassagesFrom(int r, int c, boolean[][] visited) {
        visited[r][c] = true;

        int[] dirs = {0, 1, 2, 3};

        Random rand = new Random();
        for (int i = dirs.length - 1; i > 0; i--) {
            int index = rand.nextInt(i + 1);
            int temp = dirs[index];
            dirs[index] = dirs[i];
            dirs[i] = temp;
        }

        for (int dir : dirs) {
            int nr = r, nc = c;
            if (dir == 0) nr = r - 1;
            else if (dir == 1) nc = c + 1;
            else if (dir == 2) nr = r + 1;
            else if (dir == 3) nc = c - 1;
            if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && !visited[nr][nc]) {
                if (dir == 0) {
                    grid[r][c].top = false;
                    grid[nr][nc].bottom = false;
                } else if (dir == 1) {
                    grid[r][c].right = false;
                    grid[nr][nc].left = false;
                } else if (dir == 2) {
                    grid[r][c].bottom = false;
                    grid[nr][nc].top = false;
                } else if (dir == 3) {
                    grid[r][c].left = false;
                    grid[nr][nc].right = false;
                }
                carvePassagesFrom(nr, nc, visited);
            }
        }
    }

    public void setBunny(Bunny bunny) {
        this.bunny = bunny;
    }

    public void addRobot(Robot robot) {
        this.robots.add(robot);
    }

    public boolean isGameRunning() {
        return gameRunning;
    }

    public String getWinnerMessage() {
        return winnerMessage;
    }

    public synchronized int[] getEmptyRandomPosition() {
        Random rand = new Random();
        int r, c;
        boolean occupied;
        do {
            occupied = false;
            r = rand.nextInt(rows);
            c = rand.nextInt(cols);
            for (Robot robot : robots) {
                if (robot.r == r && robot.c == c) occupied = true;
            }
        } while (occupied);
        return new int[]{r, c};
    }

    public synchronized void moveBunny(Bunny b, int newR, int newC) {
        if (!gameRunning) return;
        if (newR < 0 || newR >= rows || newC < 0 || newC >= cols) return;

        b.r = newR;
        b.c = newC;

        if (b.r == exitR && b.c == exitC) {
            gameRunning = false;
            winnerMessage = "The Bunny found the exit and escaped!";
        }

        for (Robot r : robots) {
            if (b.r == r.r && b.c == r.c) {
                gameRunning = false;
                winnerMessage = "Oh no! The Bunny ran directly into a Robot!";
            }
        }
    }

    public synchronized void moveRobot(Robot r, int newR, int newC) {
        if (!gameRunning) return;
        if (newR < 0 || newR >= rows || newC < 0 || newC >= cols) return;

        for (Robot otherRobot : robots) {
            if (otherRobot != r && otherRobot.r == newR && otherRobot.c == newC) {
                return;
            }
        }

        r.r = newR;
        r.c = newC;

        if (r.r == bunny.r && r.c == bunny.c) {
            gameRunning = false;
            winnerMessage = r.name + " caught the bunny!";
        }
    }

    public synchronized void printMaze() {
        System.out.println("\n\n\n\n\n\n\n\n");
        System.out.println("=========");

        int vizRows = rows * 2 + 1;
        int vizCols = cols * 2 + 1;
        char[][] viz = new char[vizRows][vizCols];

        for (int i = 0; i < vizRows; i++) {
            for (int j = 0; j < vizCols; j++) {
                viz[i][j] = ' ';
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int r = i * 2 + 1;
                int c = j * 2 + 1;

                if (bunny != null && bunny.r == i && bunny.c == j) {
                    viz[r][c] = 'B';
                } else if (isRobotAt(i, j)) {
                    viz[r][c] = 'R';
                } else if (i == exitR && j == exitC) {
                    viz[r][c] = 'E';
                } else {
                    viz[r][c] = 'o';
                }
                if (grid[i][j].top) viz[r - 1][c] = '-';
                if (grid[i][j].bottom) viz[r + 1][c] = '-';
                if (grid[i][j].left) viz[r][c - 1] = '|';
                if (grid[i][j].right) viz[r][c + 1] = '|';
            }
        }
        for (int i = 0; i < vizRows; i++) {
            for (int j = 0; j < vizCols; j++) {
                System.out.print(viz[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println("==============");
    }

    private boolean isRobotAt(int r, int c) {
        for (Robot robot : robots) {
            if (robot.r == r && robot.c == c) return true;
        }
        return false;
    }

    public synchronized void forceStop(String reason) {
        if (gameRunning) {
            this.gameRunning = false;
            this.winnerMessage = reason;
        }
    }
}
