

import java.util.Random;
import java.util.Scanner;

public class GenerateSudoku {
    static Random rand = new Random();
    static int board[][];
    static int n;
    static int numBlock;

    static void generateBoard() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // Gen row 1, 2, 3 
                if (i == 0) {
                    board[i][j] = j + 1;
                } else if (i < numBlock) {
                    int temp = board[i - 1][j] + numBlock;
                    if (temp > n) {
                        board[i][j] = temp - n;    
                    } else {
                        board[i][j] = temp;
                    }
                } 
                // Gen row 4 - n
                else {
                    if ((j + 1) % numBlock != 0) {
                        board[i][j] = board[i - numBlock][j + 1];
                    } else {
                        board[i][j] = board[i - numBlock][j - numBlock + 1];
                    }
                }
            }
        }
    }

    static void printArray2D() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if ((j + 1) % numBlock == 0) {
                    System.out.printf("%-5d", board[i][j]);
                } else {
                    System.out.printf("%-3d", board[i][j]);
                }
            }
            if ((i + 1) % numBlock == 0) {
                System.out.printf("%n%n");
            } else {
                System.out.printf("%n");

            }
        }
    }

    static void shuffleRow() {
        int ithBlock;
        for (int i = 0; i < n; i+=numBlock) {
            int ranNum = rand.nextInt(numBlock);
            ithBlock = i / numBlock;
            swapRows(i, ithBlock * numBlock + ranNum);
        }
    }

    private static void swapRows(int r1, int r2) {
        int rowTemp[] = board[r1];
        board[r1] = board[r2];
        board[r2] = rowTemp;
    }

    static void shuffleCols() {
        int ithBlock;
        for (int j = 0; j < n; j+=numBlock) {
            int ranNum = rand.nextInt(numBlock);
            ithBlock = j / numBlock;
            swapCols(j, ithBlock * numBlock + ranNum);
        }
    }

    private static void swapCols(int c1, int c2) {
        int colTemp;
        for (int i = 0; i < n; i++) {
            colTemp = board[i][c1];
            board[i][c1] = board[i][c2];
            board[i][c2] = colTemp;
        }
    }

    static void shuffleBlockRows() {
        for (int i = 0; i < numBlock; i++) {
            int ranNum = rand.nextInt(numBlock);
            swapBlockRows(i, ranNum);
        }   
    }

    private static void swapBlockRows(int br1, int br2) {
        for (int i = 0; i < numBlock; i++) {
            swapRows(br1 * numBlock + i, br2 * numBlock + i);
        }
    }

    static void shuffleBlockCols() {
        for (int i = 0; i < numBlock; i++) {
            int ranNum = rand.nextInt(numBlock);
            swapBlockCols(i, ranNum);
        }   
    }

    private static void swapBlockCols(int bc1, int bc2) {
        for (int i = 0; i < numBlock; i++) {
            swapCols(bc1 * numBlock + i, bc2 * numBlock + i);
        }
    }



    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Size Sudoku: ");
        n = input.nextInt();
        numBlock = (int) Math.sqrt(n);
        board = new int[n][n];

        System.out.println(2 / 3);
        
        generateBoard();
        shuffleRow();
        shuffleCols();
        shuffleBlockRows();
        shuffleBlockCols();
        printArray2D();
        // System.out.println(board[1]);
    }
}
