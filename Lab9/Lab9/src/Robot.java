import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

class Robot extends Entity {
    private boolean[][] visited;
    private Stack<int[]> pathStack = new Stack<>();

    public Robot(Maze maze, SharedMemory memory, String name) {
        super(maze, memory, name);
        visited = new boolean[maze.rows][maze.cols];
        int[] pos = maze.getEmptyRandomPosition();
        this.r = pos[0];
        this.c = pos[1];

        visited[r][c] = true;
        pathStack.push(new int[]{r, c});
    }

    @Override
    public void run() {
        while (maze.isGameRunning()) {
            try {
                applyDelay();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }

            if (!maze.isGameRunning()) break;

            int[] nextMove = getSystematicMove();
            maze.moveRobot(this, nextMove[0], nextMove[1]);
            visited[this.r][this.c] = true;

            if (random.nextInt(10) > 7) {
                memory.shareInfo(name + " a verificat zona [" + r + "," + c + "]");
            }
        }
    }

    private int[] getSystematicMove() {
        List<int[]> validMoves = new ArrayList<>();
        Cell current = maze.grid[r][c];

        if (!current.top && !visited[r - 1][c]) validMoves.add(new int[]{r - 1, c});
        if (!current.bottom && !visited[r + 1][c]) validMoves.add(new int[]{r + 1, c});
        if (!current.left && !visited[r][c - 1]) validMoves.add(new int[]{r, c - 1});
        if (!current.right && !visited[r][c + 1]) validMoves.add(new int[]{r, c + 1});

        if (!validMoves.isEmpty()) {
            int[] move = validMoves.get(random.nextInt(validMoves.size()));
            pathStack.push(move);
            return move;
        } else {
            // backtracking
            if (!pathStack.isEmpty()) {
                pathStack.pop();
                if (!pathStack.isEmpty()) {
                    return pathStack.peek();
                }
            }
        }
        //random in caz de
        return getRandomMove();
    }
}