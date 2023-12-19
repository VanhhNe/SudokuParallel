package ResearchComputation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class GenerateSudokuBoard {
    private static Random rand = new Random();
    private static int board[][];
    private static int size;
    private static int numBlock;
    private static int boardHide[][];

    static void generateBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                // Gen row 1, 2, 3 
                if (i == 0) {
                    board[i][j] = j + 1;
                } else if (i < numBlock) {
                    int temp = board[i - 1][j] + numBlock;
                    if (temp > size) {
                        board[i][j] = temp - size;    
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

    static void printArray2D(int[][] boardSudoku) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if ((j + 1) % numBlock == 0) {
                    System.out.printf("%-5d", boardSudoku[i][j]);
                } else {
                    System.out.printf("%-3d", boardSudoku[i][j]);
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
        for (int i = 0; i < size; i+=numBlock) {
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
        for (int j = 0; j < size; j+=numBlock) {
            int ranNum = rand.nextInt(numBlock);
            ithBlock = j / numBlock;
            swapCols(j, ithBlock * numBlock + ranNum);
        }
    }

    private static void swapCols(int c1, int c2) {
        int colTemp;
        for (int i = 0; i < size; i++) {
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

    static void hideCell(double rate) {
        boardHide = new int[size][size];
        // Coppy board to boardHine
        for (int k = 0; k < size; k++) {
            for (int l = 0; l < size; l++) {
                if (board[k][l] != 0) {
                    boardHide[k][l] = board[k][l];
                }
            }
        }

        // Hide Cell
        for (int i = 0; i < size * size * rate; i++) {
            int row = rand.nextInt(size);
            int col = rand.nextInt(size);
            boardHide[row][col] = 0;
        }
    }

    static void writeFile(String fileName) throws IOException{
        BufferedWriter ow = null;

        ow = new BufferedWriter(new FileWriter(fileName));
        ow.write(size + "");
        ow.newLine();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                ow.write(boardHide[row][col] + " ");
            }
            ow.newLine();        
        }
        ow.flush();
        ow.close();
    }

    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        System.out.print("Size Sudoku: ");
        size = input.nextInt();
        System.out.print("Choose level 1 - 3: ");
        int level = input.nextInt();
        input.close();

        numBlock = (int) Math.sqrt(size);
        board = new int[size][size];

        double rate;
        String temp;
        switch (level) {
            case 1:
                temp = "easy";
                System.out.println(temp);
                rate = 0.5;
                break;
            case 2:
                temp = "median";
                System.out.println(temp);
                rate = 0.6;
                break;
            default:
                temp = "hard";
                System.out.println(temp);
                rate = 0.75;
                break;
        }
        
        generateBoard();
        shuffleRow();
        shuffleCols();
        shuffleBlockRows();
        shuffleBlockCols();
        hideCell(rate);
        printArray2D(board);

        String fileName = "D:\\CTDL-TT\\finalexam\\SudokuParallel\\Test_Cases\\";
        fileName = String.format("%sgen_%dx%d_%s.txt", fileName, size, size, temp);
        System.out.println(fileName);
        
        writeFile(fileName);
        
    }
}
