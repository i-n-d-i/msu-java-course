package home1;
import java.util.Random;
import java.util.Scanner;

class Game {
    private int num;
    private int steps;

    Game() {
        this.steps = 0;

        int min = 1;
        int max = 1000;
        int diff = max - min;
        Random random = new Random();
        int res = random.nextInt(diff + 1);
        this.num = res + min;
    }

    int getSteps() {
        return this.steps;
    }

    int checkNum(int number) {
        this.steps++;
        if (number < this.num) {
            System.out.print("The hidden number is greater. Try again: ");
            return 1;
        } else if (number > this.num) {
            System.out.print("The hidden number is less. Try again: ");
            return -1;
        } else {
            System.out.print("You guess the hidden number. Gongrats! \n");
            return 0;
        }
    }
}


public class Main {
    public static void main(String[] args) {
        Game guessNum = new Game();   // создаем число
        Scanner in = new Scanner(System.in);
        System.out.print("Try to guess hidden number from 1 to 1000: ");
        while (true) {
            int num = in.nextInt();
            int check = guessNum.checkNum(num);    // 1 - больше, -1 - меньше, 0 - угадали число
            if (check == 0) {
                break;
            }
        }
        in.close();
        System.out.print("You took " + guessNum.getSteps() + " steps to guess the hidden number\n");
    }
}
