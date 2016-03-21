/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2048;

import java.util.Scanner;

/**
 *
 * @author Joco
 */
public class BoardController {

    private Board board;

    public BoardController(Board board) {
        this.board = board;
        System.out.println(this.board);
        printBoard();
        move(input("Melyik irányba szeretnéd mozgatni? (BAL: B/4 | JOBB: J/6 | FEL: F/8 | LE: L/5)",
                "[b,j,f,l, 4, 6, 8, 5]"));
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new BoardController(new Board(4));
    }

    /**
     * meghívja a mozgat fügvényt a megfelelő iránnyal.
     *
     * @param input Az irány rövidítése
     */
    private void move(String input) {
        boolean moved = false;
        switch (input) {
            case "b":
            case "4":
                for (int i = 0; i < board.getSIZE(); i++) {
                    if (board.move(board.getRow(i, false))) {
                        moved = true;
                    }
                }
                break;
            case "j":
            case "6":
                for (int i = 0; i < board.getSIZE(); i++) {
                    if (board.move(board.getRow(i, true))) {
                        moved = true;
                    }
                }
                break;
            case "f":
            case "8":
                for (int i = 0; i < board.getSIZE(); i++) {
                    if (board.move(board.getColumn(i, false))) {
                        moved = true;
                    }

                }
                break;
            case "l":
            case "5":
                for (int i = 0; i < board.getSIZE(); i++) {
                    if (board.move(board.getColumn(i, true))) {
                        moved = true;
                    }
                }
                break;
        }
        if (moved) {
            printBoard(board.setVeletlenErtek());
            if (board.getEmptyCellsSize() < 1 && !board.isGameOver()) {
                System.out.println("GameOver");

                if (input("Szeretnéd újra kezdeni?(I/N))", "[i,n]").equals("n")) {
                    System.exit(0);
                }
                board = new Board(4);
                printBoard();

            } else {
                move(input("Melyik irányba szeretnéd mozgatni? (BAL: B/4 | JOBB: J/6 | FEL: F/8 | LE: L/5)",
                        "[b,j,f,l, 4, 6, 8, 5]"));
            }
        } else {
            move(input("Melyik irányba szeretnéd mozgatni? (BAL: B/4 | JOBB: J/6 | FEL: F/8 | LE: L/5)",
                    "[b,j,f,l, 4, 6, 8, 5]"));
        }
    }

    /**
     * kiir egy kerdest a konzolra es addig ismetli amig a megfelelo
     * karaktereket kapja valaszkent
     *
     * @param kerdes a kérdés ami ki lesz iratva
     * @param regex a valaszban elfogadhato karakterek
     * @return
     */
    private String input(String kerdes, String regex) {
        String next = "";
        do {
            System.out.print(kerdes);
            next = new Scanner(System.in).next().toLowerCase();
//            System.out.println();
        } while (!next.matches(regex));
        return next;
    }

    /**
     * kiírja a táblát
     */
    private void printBoard() {
        printBoard(new int[]{-1, -1});
    }

    private void printBoard(int[] newErtek) {
        System.out.println("\n+-----+-----+-----+-----+");
        for (int sorIndex = 0; sorIndex < board.getSIZE(); sorIndex++) {
            for (int oszlopIndex = 0; oszlopIndex < board.getSIZE(); oszlopIndex++) {
                System.out.print("|"
                        + (sorIndex == newErtek[0] && oszlopIndex == newErtek[1] ? formatValue(board.getBoard()[sorIndex][oszlopIndex], setColor(board.getBoard()[sorIndex][oszlopIndex], true))
                                : formatValue(board.getBoard()[sorIndex][oszlopIndex], setColor(board.getBoard()[sorIndex][oszlopIndex], false))));
                if (oszlopIndex == board.getSIZE() - 1) {
                    System.out.print("|");
                }
            }
            System.out.println("\n+-----+-----+-----+-----+");
        }
        System.out.println("Pontszám: " + board.getScore() + "\n");
        if (board.getHighestTitle() == 2048 && board.getScore() > 30000) {
            System.out.println("Nyertél! elérted a 2048-as értéket!");
            if (input("Szeretnéd folytatni?(I/N))", "[i,n]").equals("n")) {
                System.exit(0);
            }
        }

    }

    /**
     * Visszaadja a megformázott értéket a megfelelő szóközökkel
     *
     * @param ertek A formazando ertek
     * @param szinezettErtek A formazando ertek String, színes véltozaza
     * @return Visszaadja a megformázott értéket a megfelelő szóközökkel
     */
    private String formatValue(int ertek, String szinezettErtek) {
        int empty = (5 - Integer.toString(ertek).length()) / 2;
        String formattedValue = "";
        if (ertek == 0) {
            return "     ";
        }
        int i = 0;
        while (i < 5) {
            if (i < empty || i >= empty + Integer.toString(ertek).length()) {
                formattedValue += " ";
                i++;
            } else {
                formattedValue += szinezettErtek;
                i += Integer.toString(ertek).length();
            }
        }
        return formattedValue;
    }

    /**
     * Beallítja az a színt a kapott értéken forras:
     * http://cesarloachamin.github.io/2015/03/31/System-out-print-colors-and-unicode-characters/
     *
     * @param newErtek igaz ha az érték újonan generált -> zöld lesz a színe
     */
    private String setColor(int ertek, boolean newErtek) {
        String coloredText = Integer.toString(ertek);
        String RESET = "\u001B[0m";
        if (newErtek) {
            coloredText = "\u001B[32m" + ertek + RESET; //ZOLD
        }
        switch (ertek) {
            case 8:
            case 512:
                coloredText = "\u001B[35m" + ertek + RESET; //LILA
                break;
            case 16:
            case 1024:
                coloredText = "\u001B[36m" + ertek + RESET; //CIAN
                break;
            case 32:
            case 2048:
                coloredText = "\u001B[34m" + ertek + RESET; //KEK
                break;
            case 64:
            case 4096:
                coloredText = "\u001B[33m" + ertek + RESET; //SARGA
                break;
            case 128:
            case 8192:
                coloredText = "\u001B[31m" + ertek + RESET; //PIROS
        }
        return coloredText;
    }

}
