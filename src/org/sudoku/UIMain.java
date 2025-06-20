package org.sudoku;

import org.sudoku.service.SudokuGenerator;
import org.sudoku.ui.custom.screen.MainScreen;

import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class UIMain {

    public static void main(String[] args) {
        SudokuGenerator generator = new SudokuGenerator();
        generator.generate();
        String gameConfigString = generator.toGameConfigString();
        final var gameConfig = Stream.of(gameConfigString.split(" "))
                .collect(toMap(k -> k.split(";")[0], v -> v.split(";")[1]));
        var mainScreen = new MainScreen(gameConfig);
        mainScreen.buildMainScreen();
    }
}
