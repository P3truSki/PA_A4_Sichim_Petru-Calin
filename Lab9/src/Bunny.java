class Bunny extends Entity {
    public Bunny(Maze maze, SharedMemory memory, String name) {
        super(maze, memory, name);
        this.r = random.nextInt(maze.rows);
        this.c = random.nextInt(maze.cols);
    }

    @Override
    public void run() {
        while (maze.isGameRunning()) {
            int[] nextMove = getRandomMove();
            maze.moveBunny(this, nextMove[0], nextMove[1]);
            try {
                Thread.sleep(1000); // BUNNY MOVE
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
