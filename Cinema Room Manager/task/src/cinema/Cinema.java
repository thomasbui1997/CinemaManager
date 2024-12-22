package cinema;
import javax.swing.text.html.Option;
import java.util.OptionalInt;
import java.util.Scanner;

public class Cinema {

    static char[][] availableSeats;
    static int numRows;
    static int numCols;
    static int numTicketsBought;
    static int currentProfit;
    static int maxProfit;

    public static void main(String[] args) {
        // ------ Initialize Cinema -------
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows: ");
        System.out.print("> ");
        int rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row: ");
        System.out.print("> ");
        int cols = scanner.nextInt();
        availableSeats = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                availableSeats[i][j] = 'S';
            }
        }
        numRows = rows;
        numCols = cols;
        numTicketsBought = 0;
        currentProfit = 0;
        maxProfit = getMaxProfit(numRows, numCols);

        // ------ Display menu -------
        boolean exit = false;
        do {
            System.out.println("1. Show the seats");
            System.out.println("2. Buy a ticket");
            System.out.println("3. Statistics");
            System.out.println("0. Exit");
            System.out.print("> ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1: displaySeats(); break;
                case 2: buySeat(scanner); break;
                case 3: showStats(); break;
                case 0: exit = true; break;
                default: System.out.println("Invalid choice"); break;
            }
        } while (!exit);
    }


    /**
     * Takes the row and col of seat and outputs ticket price. Reserves seat.
     * @param scanner   Scanner object - true if a seat is being picked, otherwise return all seats.
     * */
    private static void buySeat(Scanner scanner) {
        boolean validTicket = false;
        int rowNum = -1;
        int colNum = -1;

        do {
            System.out.println("Enter a row number: ");
            System.out.print("> ");
            rowNum = scanner.nextInt();
            System.out.println("Enter a seat number in that row: ");
            System.out.print("> ");
            colNum = scanner.nextInt();

            if (rowNum <= 0 || rowNum > numRows || colNum <= 0 || colNum > numCols) {
                System.out.println("Wrong input!");
            } else if (availableSeats[rowNum-1][colNum-1] == 'B') {
                System.out.println("That ticket has already been purchased!");
            } else {
                validTicket = true;
            }
        } while (!validTicket);

        availableSeats[rowNum-1][colNum-1] = 'B';
        numTicketsBought ++;
        int ticketPrice = calculateTicketPrice(rowNum);
        currentProfit += ticketPrice;
        System.out.printf("\nTicket price: $%d \n\n", ticketPrice);
    }

    /**
     * Prints a 2-d matrix of seats
     * */
    private static void displaySeats() {
        System.out.printf("Cinema: \n  ");
        for (int col = 1; col <= numCols; col++) {
            System.out.printf("%d ", col);
        }
        System.out.println();
        for (int row = 1; row <= numRows; row++) {
            System.out.printf("%d ", row);
            for (int col = 1; col <= numCols; col++) {
                System.out.printf("%c ", availableSeats[row-1][col-1]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public static int calculateTicketPrice(int row) {
        if (numRows * numCols <= 60) {
            return 10;
        }
        if (row <= numRows / 2) {
            return 10;
        } else {
            return 8;
        }
    }

    public static int getMaxProfit(int numRows, int numCols) {
        if (numRows * numCols <= 60) {
            return numRows * numCols * 10;
        }
        int frontSeats = numRows / 2 * numCols;
        int backSeats = numRows * numCols - frontSeats;
        return frontSeats * 10 + backSeats * 8;
    }

    private static void showStats() {
        System.out.printf("\nNumber of purchased tickets: %d\n", numTicketsBought);
        System.out.printf("Percentage: %.2f%%\n", ((double) numTicketsBought)/(numRows * numCols)*100);
        System.out.printf("Current income: $%d\n", currentProfit);
        System.out.printf("Total income: $%d\n\n", maxProfit);
    }
}