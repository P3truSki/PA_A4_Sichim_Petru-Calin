import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("START");
        Maze maze = new Maze(5, 5);
        SharedMemory memory = new SharedMemory();

        Bunny bunny = new Bunny(maze, memory, "B Bunny");
        maze.setBunny(bunny);

        List<Robot> robots = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Robot robot = new Robot(maze, memory, "Robot-" + (i + 1));
            robots.add(robot);
            maze.addRobot(robot);
        }

        new Thread(bunny).start();
        for (Robot r : robots) {
            new Thread(r).start();
        }

        // 60 sec time limit
        DaemonManager daemon = new DaemonManager(maze, 60000);
        daemon.start();

        Scanner scanner = new Scanner(System.in);
        while (maze.isGameRunning()) {
            try {
                if (System.in.available() > 0) {
                    String cmd = scanner.nextLine().toLowerCase().trim();
                    String[] parts = cmd.split(" ");

                    if (parts.length >= 2) {
                        String action = parts[0];
                        String target = parts[1];

                        if (action.equals("stop")) {
                            if (target.equals("all")) {
                                bunny.pause();
                                robots.forEach(Robot::pause);
                            } else if (target.equals("bunny")) bunny.pause();
                            else robots.stream().filter(r -> r.name.toLowerCase().contains(target)).forEach(Robot::pause);
                        }
                        else if (action.equals("resume")) {
                            if (target.equals("all")) {
                                bunny.resumeEntity();
                                robots.forEach(Robot::resumeEntity);
                            } else if (target.equals("bunny")) bunny.resumeEntity();
                            else robots.stream().filter(r -> r.name.toLowerCase().contains(target)).forEach(Robot::resumeEntity);
                        }
                        else if (action.equals("speed") && parts.length == 3) {
                            try {
                                int newSpeed = Integer.parseInt(parts[2]);
                                if (target.equals("all")) {
                                    bunny.setSpeed(newSpeed);
                                    robots.forEach(r -> r.setSpeed(newSpeed));
                                } else if (target.equals("bunny")) bunny.setSpeed(newSpeed);
                                else robots.stream().filter(r -> r.name.toLowerCase().contains(target)).forEach(r -> r.setSpeed(newSpeed));
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid speed number.");
                            }
                        }
                    }
                }
                Thread.sleep(100);

            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }

        System.out.println("DONE");
        System.out.println("Result: " + maze.getWinnerMessage());
        System.exit(0); // Terminam fortat pt daemon
    }
}