package ru.example.homework2;

import static ru.example.homework2.Figure.X;
import static ru.example.homework2.Figure.O;

public class Player {
    private String name;

    private final Enum figure;

    private final String figureName;

    private int actions = 0;

    private boolean isWin = false;

    public Player(Enum figure) {
        this.figure = figure;
        if (figure == X) {
            this.figureName = "крестик";
        } else {
            this.figureName = "нолик";
        }
    }

    public int getActions() {
        return actions;
    }

    public void setActions(int actions) {
        this.actions = actions;
    }

    public boolean isWin() {
        return isWin;
    }

    public void setWin(boolean win) {
        isWin = win;
    }

    public String getFigureName() {
        return figureName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Enum getFigure() {
        return figure;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", figure=" + figure +
                ", actions=" + actions +
                ", isWin=" + isWin +
                '}';
    }
}
