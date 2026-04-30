import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// compulsory
public abstract class Entity implements Runnable {
    String name;
    int r, c;
    Maze maze;
    SharedMemory memory;
    Random random = new Random();

    public Entity(Maze maze, SharedMemory memory, String name) {
        this.maze = maze;
        this.memory = memory;
        this.name = name;
    }

    // random valid fara pertei
    protected int[] getRandomMove() {
        List<int[]> validMoves = new ArrayList<>();
        Cell current = maze.grid[r][c];

        if (!current.top) validMoves.add(new int[]{r - 1, c});
        if (!current.bottom) validMoves.add(new int[]{r + 1, c});
        if (!current.left) validMoves.add(new int[]{r, c - 1});
        if (!current.right) validMoves.add(new int[]{r, c + 1});

        if (validMoves.isEmpty()) return new int[]{r, c}; // fail-safe

        return validMoves.get(random.nextInt(validMoves.size()));
    }
}