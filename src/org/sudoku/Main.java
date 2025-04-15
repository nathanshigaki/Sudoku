package org.sudoku;

import org.sudoku.model.Board;
import org.sudoku.model.Space;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toMap;
import static org.sudoku.util.BoardTemplate.BOARD_TEMPLATE;

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
            System.out.println("8 - Sair\n");

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
            System.out.println("O jogo já foi iniciado.\n");
            return;
        }

        List<List<Space>> spaces = new ArrayList<>();
        for (int i = 0; i < BOARD_LIMIT; i++) {
            spaces.add(new ArrayList<>());
            for (int j = 0; j < BOARD_LIMIT; j++) {
                var positionConfig = positions.get("%s,%s".formatted(i, j));
                var expected = Integer.parseInt(positionConfig.split(",")[0]);
                var fixed = Boolean.parseBoolean(positionConfig.split(",")[1]);
                var currentSpace = new Space(expected, fixed);
                spaces.get(i).add(currentSpace);
            }
        }

        board = new Board(spaces);
        System.out.println("O jogo está pronto para começar\n");
    }

    private static void inputNumber() {
        if (isNull(board)){
            System.out.println("O jogo não foi iniciado.\n");
            return;
        }
        //adiciona try catch
        System.out.println("Digite o número da coluna:");
        var col = runUntilGetValidNumber(0, 8);
        System.out.println("Digite o número da linha:");
        var row = runUntilGetValidNumber(0, 8);
        System.out.printf("Digite o número na posição [%s, %s]\n", col, row);
        var value = runUntilGetValidNumber(1, 9);

        if ((!board.changeValue(col, row, value))){
            System.out.printf("Posição [%s, %s] tem um valor fixo\n", col, row);
        }
    }

    private static int runUntilGetValidNumber(final int min, final int max){
        var current = scanner.nextInt();
        while (current < min || current > max){
            System.out.println("Número inválido, tente novamente.\n");
            System.out.printf("Digite um número entre %s e %s\n", min, max);
            current = scanner.nextInt();
        }
        return current;
    }

    private static void removeNumber() {
        if (isNull(board)){
            System.out.println("O jogo não foi iniciado.\n");
            return;
        }
        //adiciona try catch
        System.out.println("Digite o número da coluna:");
        var col = runUntilGetValidNumber(0, 8);
        System.out.println("Digite o número da linha:");
        var row = runUntilGetValidNumber(0, 8);
        if (!board.clearValue(col, row)){
            System.out.printf("Posição [%s, %s] tem um valor fixo\n", col, row);
        }
    }

    private static void showCurrentGame() {
        if (isNull(board)){
            System.out.println("O jogo não foi iniciado.\n");
            return;
        }

        var args = new Object[81];
        var agrPos = 0;
        for (int i = 0; i < BOARD_LIMIT; i++) {
            for (var col: board.getSpaces()){
                args[agrPos ++] = " " + ((isNull(col.get(i).getActual())) ? " " : col.get(i).getActual());
            }
        }
        System.out.printf((BOARD_TEMPLATE) + "%n", args);
    }

    private static void showGameStatus() {
        if (isNull(board)){
            System.out.println("O jogo não foi iniciado.\n");
            return;
        }

        System.out.printf("Jogo se encontra no status %s\n", board.getStatus().getLabel());

        if (board.hasErrors()) {
            System.out.println("Jogo contém erros.\n");
        } else {
            System.out.println("Jogo não tem erros.\n");
        }
    }

    private static void clearGame() {
        if (isNull(board)){
            System.out.println("O jogo não foi iniciado.\n");
            return;
        }

        System.out.println("Tem certeza em limpar todo o jogo?");
        var confirm = scanner.next().toLowerCase();
        while (!confirm.equalsIgnoreCase("sim") && !confirm.equalsIgnoreCase("não")){
            System.out.println("Digite 'sim' ou 'não'");
            confirm = scanner.next();
        }

        if(confirm.equalsIgnoreCase("sim")){
            board.reset();
        }
    }
    
    private static void finishGame() {
        if (isNull(board)){
            System.out.println("O jogo não foi iniciado.\n");
            return;
        }

        if (board.gameIsFinish()){
            System.out.println("Parabéns\n");
            showCurrentGame();
            board = null;
        } else if (board.hasErrors()){
            System.out.println("Seu jogo tem erros\n");
        } else {
            System.out.println("Tem espaços vazios\n");
        }
    }
}