/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2048;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Joco
 */
public class Board {

    private final int[][] board;
    private final int size;
    private int score = 0;
//    private boolean gameOver = false;
    private int emptyCellsSize;
    private int highestTitle;

    public Board(int SIZE) {
        this.size = SIZE;
        board = new int[SIZE][SIZE];
        setVeletlenErtek();
        setVeletlenErtek();
    }

    /**
     * Visszaad egy listát ami tartalmazza az üres cellák pozicióját a táblában.
     */
    private List<int[]> getEmptyCells() {
        List<int[]> emptyCells = new ArrayList();
        for (int rowIndex = 0; rowIndex < size; rowIndex++) {
            for (int colIndex = 0; colIndex < size; colIndex++) {
                if (board[rowIndex][colIndex] == 0) {
                    emptyCells.add(new int[]{rowIndex, colIndex});
                }
            }
        }
        return emptyCells;
    }

    /**
     * Egy véletlenszerúen kiválasztott üres cellának(ha van ilyen) ad egy
     * értéket(2 vagy 4) Ha a cella az utolsó cella volt leelenőrzi, hogy van e
     * még lehetséges lépés
     */
    protected final int[] setVeletlenErtek() {
        List<int[]> emptyCells = getEmptyCells();
        emptyCellsSize = emptyCells.size();
        int[] newIndex = {-1, -1};
        if (emptyCellsSize > 0) {
            int random = (int) (Math.random() * emptyCells.size() - 1);
            newIndex = new int[]{emptyCells.get(random)[0], emptyCells.get(random)[1]};
            board[newIndex[0]][newIndex[1]] = generateRandomValue();
            updateScore(board[newIndex[0]][newIndex[1]]);
            emptyCellsSize--;

        }
        return newIndex;
    }

    /**
     * Visszaad egy értéket ami 2 vagy 4
     */
    private static int generateRandomValue() {
        return (int) (Math.random() * 100) + 1 > 80 ? 4 : 2;
    }

    /**
     * Frissíti a pont változó értékét a paraméterként kapott értékkel. Ha a
     * paraméter 2048 akkor kiírja, hogy Nyertél!
     */
    private void updateScore(int pont) {
        this.score += pont;
        if (highestTitle<pont) {
            highestTitle=pont;
        }
       
    }

    /**
     * Visszaad egy boolean értéket attól függően, hogy van e még lehetsges
     * lépés.
     */
    protected boolean isGameOver() {
        int rowIndex = 0, colIndex = 0;
        boolean gameOver = false;
        while (!gameOver && rowIndex < size) {
            while (!gameOver && colIndex < size) {
                if (colIndex < size - 1 && board[rowIndex][colIndex] == board[rowIndex][colIndex + 1]) {
                    gameOver = true;
                }
                if (rowIndex < size - 1 && board[rowIndex][colIndex] == board[rowIndex + 1][colIndex]) {
                    gameOver = true;
                }
                colIndex++;
            }
            rowIndex++;
            colIndex = 0;
        }
        return gameOver;
    }

    /**
     * Visszaad egy listát az adott sor pozicióival, ha szükséges akkor
     * visszafelé rendezi a listát.
     */
    protected List<int[]> getRow(int rowIndex, boolean reverse) {
        List<int[]> line = new ArrayList();
        for (int colIndex = 0; colIndex < size; colIndex++) {
            line.add(new int[]{rowIndex, colIndex});
        }
        if (reverse) {
            Collections.reverse(line);
        }
        return line;
    }

    /**
     * Visszaad egy listát az adott oszlop pozicióival, ha szükséges akkor
     * visszafelé rendezi a listát.
     */
    protected List<int[]> getColumn(int colIndex, boolean reverse) {
        List<int[]> line = new ArrayList();
        for (int rowIndex = 0; rowIndex < size; rowIndex++) {
            line.add(new int[]{rowIndex, colIndex});
        }
        if (reverse) {
            Collections.reverse(line);
        }
        return line;
    }

    /**
     * Elmozgatja a cellákat az adott irányba ha lehetséges
     *
     * @param line A tábla egy sora vagy oszlopa
     * @return true ha történt mozgatás
     */
    protected boolean move(List<int[]> line) {
        boolean moved = false;
        int index = 1;
        int comparedIndex = 0;
        while (index < 4) {
            if (board[line.get(comparedIndex)[0]][line.get(comparedIndex)[1]] == 0) {
                if (board[line.get(index)[0]][line.get(index)[1]] != 0) {
                    board[line.get(comparedIndex)[0]][line.get(comparedIndex)[1]] = board[line.get(index)[0]][line.get(index)[1]];
                    board[line.get(index)[0]][line.get(index)[1]] = 0;
                    moved = true;
                    index = comparedIndex + 1;
                } else {
                    index++;
                }
            } else if (board[line.get(comparedIndex)[0]][line.get(comparedIndex)[1]] == board[line.get(index)[0]][line.get(index)[1]]) {
                int newValue = board[line.get(index)[0]][line.get(index)[1]] * 2;
                board[line.get(index)[0]][line.get(index)[1]] = 0;
                board[line.get(comparedIndex)[0]][line.get(comparedIndex)[1]] = newValue;
                updateScore(newValue);
                index = ++comparedIndex + 1;
                moved = true;
            } else if (board[line.get(index)[0]][line.get(index)[1]] == 0) {
                index++;
            } else if (++comparedIndex != index) {
                board[line.get(comparedIndex)[0]][line.get(comparedIndex)[1]] = board[line.get(index)[0]][line.get(index)[1]];
                board[line.get(index)[0]][line.get(index)[1]] = 0;
                moved = true;
                index = comparedIndex + 1;
            } else {
                index = comparedIndex + 1;
            }
        }
        return moved;
    }

    /**
     *
     * @return Visszaadja a játék leírását.
     */
    @Override
    public String toString() {
        return "\t\t\t\t\t--=2048=--\n\n"
                + "A játék egy 4x4-es táblában zajlik ahol minden egyes cellának lehet egy értéke.\n"
                + "A cellákat lehet mozgatni jobbra, balra, fel illetve le. Ha két ugyan olyan értékű "
                + "cella ütközik összeolvadnak és az értékük összeadódik\n"
                + "Indulásnál 2 véletlenszerűen kiválasztott cella kap értéket (ami 2 vagy 4 lehet) "
                + "majd ezután a cellák minden sikeres mozgatásával geneálunk\n"
                + "az üres helyek egyikére egy újabb értéket(szintén 2 vagy 4)\n"
                + "Az értékek többnyire különböző színekkel vannak ellátva de egyes színek többször is előfordulnak.\n"
                + "Az újonan generált cella színe minden esetben zöld!\n"
                + "A cél hogy elérjük a 2048-as értéket de még ez után is lehet játszani\n"
                + "\n\t\t\t\t\tGL & HF :)\n\n";
    }

    //  Getters, Setters
    protected int getSIZE() {
        return size;
    }

    protected int getScore() {
        return score;
    }



    protected int[][] getBoard() {
        return board;
    }

    public int getHighestTitle() {
        return highestTitle;
    }


  

    public int getEmptyCellsSize() {
        return emptyCellsSize;
    }

}
