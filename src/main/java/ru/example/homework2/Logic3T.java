package ru.example.homework2;

public class Logic3T {
    private final Figure3T[][] table;

    public Logic3T(Figure3T[][] table) {
        this.table = table;
    }

    public boolean isWinnerX() {
        if (table[0][0].hasMarkX() && table[1][1].hasMarkX() && table[2][2].hasMarkX()
                || (table[0][2].hasMarkX() && table[1][1].hasMarkX() && table[2][0].hasMarkX())) {
            return true;
        } else {
            for (int i = 0; i < table.length; i++) {
                if (table[i][0].hasMarkX() && table[i][1].hasMarkX() && table[i][2].hasMarkX()
                        || (table[0][i].hasMarkX() && table[1][i].hasMarkX() && table[2][i].hasMarkX())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isWinnerO() {
        if (table[0][0].hasMarkO() && table[1][1].hasMarkO() && table[2][2].hasMarkO()
                || (table[0][2].hasMarkO() && table[1][1].hasMarkO() && table[2][0].hasMarkO())) {
            return true;
        } else {
            for (int i = 0; i < table.length; i++) {
                if (table[i][0].hasMarkO() && table[i][1].hasMarkO() && table[i][2].hasMarkO()
                        || (table[0][i].hasMarkO() && table[1][i].hasMarkO() && table[2][i].hasMarkO())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasGap() {
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                if (!table[i][j].hasMarkO() && !table[i][j].hasMarkX()) {
                    return true;
                }
            }
        }
        return false;
    }
}
