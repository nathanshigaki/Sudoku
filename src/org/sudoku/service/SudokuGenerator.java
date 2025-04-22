package org.sudoku.service;

import java.util.Random;
import java.util.HashSet;
import java.util.Set;

public class SudokuGenerator {
    private final int[][] board;
    private final boolean[][] fixed;
    public Random random;

    public SudokuGenerator() {
        board = new int[9][9];
        fixed = new boolean[9][9];
        random = new Random();
    }

    public void generate() {
        solve(0, 0);
        createPuzzle();
    }

    private boolean solve(int row, int col) {
        if (col == 9) {
            col = 0;
            row++;
            if (row == 9) {
                return true;
            }
        }

        if (board[row][col] != 0) {
            return solve(row, col + 1);
        }

        int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        shuffleArray(numbers);

        for (int num : numbers) {
            if (isValid(row, col, num)) {
                board[row][col] = num;
                if (solve(row, col + 1)) {
                    return true;
                }
                board[row][col] = 0;
            }
        }

        return false;
    }

    private void createPuzzle() {
        int fixedCount = 30 + random.nextInt(11);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                fixed[i][j] = false;
            }
        }

        Set<String> selected = new HashSet<>();
        while (selected.size() < fixedCount) {
            int row = random.nextInt(9);
            int col = random.nextInt(9);
            String key = row + "," + col;
            if (!selected.contains(key)) {
                selected.add(key);
                fixed[row][col] = true;
            }
        }
    }

    private boolean isValid(int row, int col, int num) {
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == num) {
                return false;
            }
        }

        for (int i = 0; i < 9; i++) {
            if (board[i][col] == num) {
                return false;
            }
        }

        int boxRow = row - row % 3;
        int boxCol = col - col % 3;
        for (int i = boxRow; i < boxRow + 3; i++) {
            for (int j = boxCol; j < boxCol + 3; j++) {
                if (board[i][j] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    private void shuffleArray(int[] array) {
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sb.append(j + "," + i + ";" + board[j][i] + "," + fixed[j][i] + " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public String toGameConfigString() {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                sb.append(x + "," + y + ";" + board[x][y] + "," + fixed[x][y]);
                if (x < 8 || y < 8) {
                    sb.append(" ");
                }
            }
        }
        return sb.toString();
    }
}