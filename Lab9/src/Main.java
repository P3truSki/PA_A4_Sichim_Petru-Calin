import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("START");
        Maze maze = new Maze(4, 4);
        SharedMemory memory = new SharedMemory();
        Bunny bunny = new Bunny(maze, memory, "B Bunny");
        maze.setBunny(bunny);
        List<Robot> robots = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Robot robot = new Robot(maze, memory, "R Robot-" + (i + 1));
            robots.add(robot);
            maze.addRobot(robot);
        }
        Thread bunnyThread = new Thread(bunny);
        bunnyThread.start();

        for (Robot r : robots) {
            new Thread(r).start();
        }
        while (maze.isGameRunning()) {
            maze.printMaze();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        maze.printMaze();
        System.out.println("DONE");
        System.out.println("Result: " + maze.getWinnerMessage());
        System.exit(0);
    }
}

class SharedMemory {
    public synchronized void shareInfo(String info) {
        System.out.println("[Shared Memory] " + info);
    }
}

