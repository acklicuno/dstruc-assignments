public class Sudoku {
    public class SudokuSolver {

        private static final int size = 9;
    
        private static int[][] board = {
                {5, 3, 0, 0, 7, 0, 0, 0, 0},
                {6, 0, 0, 1, 9, 5, 0, 0, 0},
                {0, 9, 8, 0, 0, 0, 0, 6, 0},
                {8, 0, 0, 0, 6, 0, 0, 0, 3},
                {4, 0, 0, 8, 0, 3, 0, 0, 1},
                {7, 0, 0, 0, 2, 0, 0, 0, 6},
                {0, 6, 0, 0, 0, 0, 2, 8, 0},
                {0, 0, 0, 4, 1, 9, 0, 0, 5},
                {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };
    
        private static boolean solve(int row, int col) {
            if (row == size) return true;//When you reach the end the puzzle is solved
            if (col == size) return solve(row + 1, 0);//Move to next row
    
            if (board[row][col] != 0) return solve(row, col + 1);//move to empty cell
    
            for (int num = 1; num <= size; num++) {
                if (isValid(row, col, num)) {//try all numbers and place if you can
                    board[row][col] = num;
                    if (solve(row, col + 1)) return true;//Solve next cell
    
                    board[row][col] = 0;//back track if it doesnt solve
                }
            }
            return false;
        }
    
        //isValid method to check if you can place it at a given positiin 
        private static boolean isValid(int row, int col, int num) {
            // Check the  row
            for (int i = 0; i < size; i++) {
                if (board[row][i] == num) return false;
            }
            // Check the column
            for (int i = 0; i < size; i++) {
                if (board[i][col] == num) return false;
            }
    
            // Check the whole grid taht its in
            int startRow = row - row % 3;
            int startCol = col - col % 3;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[startRow + i][startCol + j] == num) return false;
                }
            }
    
            return true;
        }
    
        // Print
        private static void printBoard() {
            for (int row = 0; row < size; row++) {
                if (row % 3 == 0 && row != 0) {
                    System.out.println("-----------"); //Seperate each 3 x 3 grid rows with lines for organization
                }
                for (int col = 0; col < size; col++) {
                    if (col % 3 == 0 && col != 0) {
                        //seperate every three columns
                        System.out.print("|");
                    }
                    System.out.print(board[row][col] == 0 ? "." : board[row][col]); // represent empty spots with .
                }
                System.out.println();
            }
        }

        public static void main(String[] args) {
            System.out.println("Sudoku:");
            printBoard();
    
            if (solve(0, 0)) {
                System.out.println("\nSolved Sudoku:");
                printBoard();
            } else {
                System.out.println("\nNo solution exists.");
            }
        }
    }
    
    
}
