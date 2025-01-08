import java.io.Serializable;
import java.util.Random;
import java.util.Scanner;

class Player implements Serializable{

    private static final Player player=new Player();

    private int marks;
    private boolean isBat;
    private int record;
    private int winCount;
    private int lossCount;

    // Constructor
    public Player() {
        this.marks = 0;
        this.isBat = false;
        this.record = 0;
        this.winCount = 0;
        this.lossCount = 0;
    }

    public static Player getPlayer(){
        return player;
    }

    // Getters and Setters
    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    public boolean isBat() {
        return isBat;
    }

    public void setBat(boolean isBat) {
        this.isBat = isBat;
    }

    public int getRecord() {
        return record;
    }

    public void setRecord(int record) {
        this.record = record;
    }

    public int getWinCount() {
        return winCount;
    }

    public int getLossCount() {
        return lossCount;
    }

    public void incrementWinCount() {
        this.winCount++;
    }

    public void incrementLossCount() {
        this.lossCount++;
    }

    public void toss() {
        RandomNumberGenerator rng = new RandomNumberGenerator();
        Scanner scanner = new Scanner(System.in);

        int playerChoice, computerChoice;

        while (true) {
            System.out.println("1 ROCK\n2 PAPER\n3 SCISSOR\n\nGiveYourChoice:");
            playerChoice = scanner.nextInt();
            computerChoice = rng.getRandomNumber(3);

            if (playerChoice == computerChoice) {
                System.out.println("Draw. Toss again.");
            } else if ((playerChoice == 1 && computerChoice == 3) ||
                       (playerChoice == 2 && computerChoice == 1) ||
                       (playerChoice == 3 && computerChoice == 2)) {
                System.out.println("You win the toss.");
                System.out.println("1 BAT\n0 BALL\nGiveYourChoice:");
                int choice = scanner.nextInt();
                this.isBat = (choice == 1);
                break;
            } else {
                System.out.println("You lose the toss.");
                this.isBat = rng.getRandomNumber(2) == 1;
                break;
            }
        }
    }

    public int bat(boolean isFirst, int target) {
        RandomNumberGenerator rng = new RandomNumberGenerator();
        Scanner scanner = new Scanner(System.in);

        System.out.println("You are batting.");
        marks = 0;

        while (true) {
            System.out.println("Enter your choice (1-6):");
            int inputInt = scanner.nextInt();
            int com = rng.getRandomNumber(6);

            if (com == inputInt) {
                System.out.println("comChoice:"+com+"\nWICKET\nScore: " + marks);
                record = Math.max(record, marks);
                if (!isFirst) {
                    if (target == marks) {
                        System.out.println("comChoice:"+com+"\nDRAW\nYou: " + marks + " Com: " + target);
                    } else if (target > marks) {
                        System.out.println("comChoice:"+com+"\nLOSS\nYou: " + marks + " Com: " + target);
                        incrementLossCount();
                    } else {
                        System.out.println("comChoice:"+com+"\nWIN\nYou: " + marks + " Com: " + target);
                        incrementWinCount();
                    }
                }
                break;
            } else if (1 <= inputInt && inputInt <= 6) {
                marks += inputInt;
                System.out.println("comChoice:"+com+"\nScore: " + marks);
                if (!isFirst && marks > target) {
                    System.out.println("comChoice:"+com+"\nWIN\nYou: " + marks + " Com: " + target);
                    incrementWinCount();
                    record = Math.max(record, marks);
                    break;
                }
            } else {
                System.out.println("MISSED BALL | Score: " + marks);
            }
        }
        return marks;
    }

    public int ball(boolean isFirst, int target) {
        RandomNumberGenerator rng = new RandomNumberGenerator();
        Scanner scanner = new Scanner(System.in);

        System.out.println("You are bowling.");
        marks = 0;

        while (true) {
            System.out.println("Enter your choice (1-6):");
            int inputInt = scanner.nextInt();
            int com = rng.getRandomNumber(6);

            if (com == inputInt) {
                System.out.println("WICKET\nScore: " + marks);
                record = Math.max(record, marks);
                if (!isFirst) {
                    if (target == marks) {
                        System.out.println("comChoice:"+com+"\nDRAW\nYou: " + target + " Com: " + marks);
                    } else if (target > marks) {
                        System.out.println("comChoice:"+com+"\nWIN\nYou: " + target + " Com: " + marks);
                        incrementWinCount();
                    } else {
                        System.out.println("comChoice:"+com+"\nLOSS\nYou: " + target + " Com: " + marks);
                        incrementLossCount();
                    }
                }
                break;
            } else if (1 <= inputInt && inputInt <= 6) {
                marks += com;
                System.out.println("comChoice:"+com+"\nScore: " + marks);
                if (!isFirst && marks > target) {
                    System.out.println("comChoice:"+com+"\nLOSS\nYou: " + target + " Com: " + marks);
                    incrementLossCount();
                    record = Math.max(record, marks);
                    break;
                }
            } else {
                System.out.println("MISSED BALL | Score: " + marks);
            }
        }
        return marks;
    }
}

class RandomNumberGenerator {
    private Random random;

    public RandomNumberGenerator() {
        random = new Random();
    }

    public int getRandomNumber(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be greater than 0");
        }
        return random.nextInt(n) + 1;
    }
}
//
public class Handmatch {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        while(true){
            System.out.println("HANDMATCH\nLet's toss.");

            Player player = Player.getPlayer();
            player.toss();

            int target;
            if (player.isBat()) {
                target = player.bat(true, 0);
                player.ball(false, target);
            } else {
                target = player.ball(true, 0);
                player.bat(false, target);
            }
            System.out.println("record:"+player.getRecord());
            System.out.println("winCount:"+player.getWinCount());
            System.out.println("LossCount:"+player.getLossCount());
            System.out.println("\nsave&quite:0 playAgain:1\n");

            choice = scanner.nextInt();
            if(choice==0){
                break;
            }

        }
        //System.out.println("Game Over");
    }
}