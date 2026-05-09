public class DaemonManager extends Thread {
    private Maze maze;
    private long timeLimitMs;
    private long startTime;

    public DaemonManager(Maze maze, long timeLimitMs) {
        this.maze = maze;
        this.timeLimitMs = timeLimitMs;
        this.setDaemon(true);
    }

    @Override
    public void run() {
        startTime = System.currentTimeMillis();

        while (maze.isGameRunning()) {
            long runningTime = System.currentTimeMillis() - startTime;

            // Limita de timp
            if (runningTime > timeLimitMs) {
                maze.forceStop("Time limit exceeded! (" + (timeLimitMs / 1000) + "s)");
                break;
            }

            maze.printMaze();
            System.out.println("Time: " + (runningTime / 1000) + "s | Commands: stop <name/all>, resume <name/all>, speed <name> <ms>");

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}