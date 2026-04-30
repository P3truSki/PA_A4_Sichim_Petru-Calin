// compulsory
class Robot extends Entity {
    public Robot(Maze maze, SharedMemory memory, String name) {
        super(maze, memory, name);
        // Initialize at a distinct random position
        int[] pos = maze.getEmptyRandomPosition();
        this.r = pos[0];
        this.c = pos[1];
    }

    @Override
    public void run() {
        while (maze.isGameRunning()) {
            int[] nextMove = getRandomMove();
            maze.moveRobot(this, nextMove[0], nextMove[1]);

            // Share info
            if (random.nextInt(10) > 5) {
                memory.shareInfo(name + " checked area around [" + r + "," + c + "]");
            }

            try {
                Thread.sleep(1000); // Robot movement speed
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
