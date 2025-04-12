package org.sudoku;

import org.sudoku.model.Board;
import org.sudoku.model.Space;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toMap;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static Board board;
    private final static int BOARD_LIMIT = 9;

    public static void main(String[] args) {
        final var positions = Stream.of(args)
                .collect(toMap(
                        k -> k.split(";")[0],
                        v -> v.split(";")[1]
                ));
        var option = -1;
        while (true){
            System.out.println("Selecione uma das opções a seguir");
            System.out.println("1 - Iniciar um novo Jogo");
            System.out.println("2 - Colocar um novo número");
            System.out.println("3 - Remover um número");
            System.out.println("4 - Visualizar jogo atual");
            System.out.println("5 - Verificar status do jogo");
            System.out.println("6 - limpar jogo");
            System.out.println("7 - Finalizar jogo");
            System.out.println("8 - Sair");

            option = scanner.nextInt();

            switch (option){
                case 1 -> startGame(positions);
                case 2 -> inputNumber();
                case 3 -> removeNumber();
                case 4 -> showCurrentGame();
                case 5 -> showGameStatus();
                case 6 -> clearGame();
                case 7 -> finishGame();
                case 8 -> System.exit(0);
                default -> System.out.println("Opção inválida, tente novamente.");
            }
        }
    }

    private static void startGame(final Map<String, String> positions) {
        if (nonNull(board)){
            System.out.println("O jogo já foi iniciado.");
            c
        }

        List<List<Space>> spaces = new ArrayList<>();
        for (int i = 0; i < BOARD_LIMIT; i++) {
            spaces.add(new ArrayList<>());
            for (int j = 0; j < BOARD_LIMIT; j++) {
                var positionConfig = positions.get("%s, %s".formatted(i,j));
                var expected = Integer.parseInt(positionConfig.split(";")[0]);
                var fixed = Boolean.parseBoolean(positionConfig.split(";")[1]);
                var currentSpace = new Space(expected,fixed);
                spaces.get(i).add(currentSpace);
            }
        }

        board = new Board(spaces);
        System.out.println("O jogo está pronto.");
    }

    private static void inputNumber() {
    }

    private static void removeNumber() {
    }

    private static void showCurrentGame() {
    }

    private static void showGameStatus() {
    }

    private static void clearGame() {
    }
    
    private static void finishGame() {
    }
}