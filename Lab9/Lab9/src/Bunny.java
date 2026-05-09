class Bunny extends Entity {
    public Bunny(Maze maze, SharedMemory memory, String name) {
        super(maze, memory, name);
        this.r = random.nextInt(maze.rows);
        this.c = random.nextInt(maze.cols);
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

            int[] nextMove = getRandomMove();
            maze.moveBunny(this, nextMove[0], nextMove[1]);
        }
    }
}