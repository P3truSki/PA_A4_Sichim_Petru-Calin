import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Entity implements Runnable {
    String name;
    int r, c;
    Maze maze;
    SharedMemory memory;
    Random random = new Random();

    // --- NOI VARIABILE PENTRU HOMEWORK ---
    protected int delayMs = 3000;
    protected volatile boolean paused = false;

    public Entity(Maze maze, SharedMemory memory, String name) {
        this.maze = maze;
        this.memory = memory;
        this.name = name;
    }


    public void setSpeed(int delay) { this.delayMs = delay; }
    public void pause() { this.paused = true; }
    public void resumeEntity() { this.paused = false; }

    protected void applyDelay() throws InterruptedException {
        while (paused && maze.isGameRunning()) {
            Thread.sleep(1000);
        }
        Thread.sleep(delayMs);
    }

    protected int[] getRandomMove() {
        List<int[]> validMoves = new ArrayList<>();
        Cell current = maze.grid[r][c];

        if (!current.top) validMoves.add(new int[]{r - 1, c});
        if (!current.bottom) validMoves.add(new int[]{r + 1, c});
        if (!current.left) validMoves.add(new int[]{r, c - 1});
        if (!current.right) validMoves.add(new int[]{r, c + 1});

        if (validMoves.isEmpty()) return new int[]{r, c};
        return validMoves.get(random.nextInt(validMoves.size()));
    }
}