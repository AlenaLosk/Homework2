package ru.example.homework2;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import static ru.example.homework2.Figure.O;
import static ru.example.homework2.Figure.X;
import static ru.example.homework2.Statistic.writeWinResultToFile;

public class TicTacToe extends Application {
    private static final String GAME_NAME = "Крестики-нолики";
    static Player[] players = new Player[2];
    private int whoIsNextPlayer = 0;
    private final int size = 3;
    private final Figure3T[][] cells = new Figure3T[size][size];
    private final Logic3T logic = new Logic3T(cells);

    private Figure3T buildRectangle(int x, int y, int size) {
        Figure3T rect = new Figure3T();
        rect.setX(x * size);
        rect.setY(y * size);
        rect.setHeight(size);
        rect.setWidth(size);
        rect.setFill(Color.WHITE);
        rect.setStroke(Color.BLACK);
        return rect;
    }

    private Group buildMarkO(double x, double y, int size) {
        Group group = new Group();
        int radius = size / 2;
        Circle circle = new Circle(x + radius, y + radius, radius - 10);
        circle.setStroke(Color.BLACK);
        circle.setFill(Color.WHITE);
        group.getChildren().add(circle);
        return group;
    }

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(GAME_NAME);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showServiceInfo(String message) {
        showInfo(message + System.lineSeparator()
                + "Начните новую Игру, нажав кнопку \'Играть\'!" + System.lineSeparator()
                + "Для выхода закройте текущее окно.");
    }

    private void askPlayerName(Player player) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(GAME_NAME);
        dialog.setHeaderText(String.format("Как зовут игрока, который играет %sами?", player.getFigureName()));
        dialog.setContentText(String.format("Введите имя игрока, играющего %sами", player.getFigureName()));
        dialog.showAndWait().ifPresentOrElse(result -> {
            player.setName(result);
            if (player.getFigure().equals(X)) {
                players[0] = player;
            } else {
                players[1] = player;
            }
        }, () -> askPlayerName(player));
    }

    private boolean checkState() {
        boolean gap = this.logic.hasGap();
        if (!gap) {
            this.showServiceInfo("Все поля заполнены!");
        }
        return gap;
    }

    private void checkWinner() {
        String statisticFile = "Статистика_выигрышей.txt";
        if (this.logic.isWinnerX()) {
            players[0].setWin(true);
            writeWinResultToFile(statisticFile, players[0]);
            whoIsNextPlayer = 0;
            this.showServiceInfo(String.format("Победили Крестики и игрок %s за %d действия(й)!",
                    players[0].getName(), players[0].getActions()));
        } else if (this.logic.isWinnerO()) {
            players[1].setWin(true);
            writeWinResultToFile(statisticFile, players[1]);
            whoIsNextPlayer = 0;
            this.showServiceInfo(String.format("Победили Нолики и игрок %s за %d действия(й)!",
                    players[1].getName(), players[1].getActions()));
        }
    }

    private Group buildMarkX(double x, double y, int size) {
        Group group = new Group();
        group.getChildren().addAll(
                new Line(
                        x + 10, y + 10,
                        x + size - 10, y + size - 10
                ),
                new Line(
                        x + size - 10, y + 10,
                        x + 10, y + size - 10
                )
        );
        return group;
    }

    private boolean isCellEmpty(Figure3T rect) {
        boolean result = false;
        if (rect.hasMarkO()) {
            showInfo("В этой ячейке уже есть нолик!");
        } else if (rect.hasMarkX()) {
            showInfo("В этой ячейке уже есть крестик!");
        } else {
            result = true;
        }
        return result;
    }

    private boolean checkPlayer(Player player) {
        if ((whoIsNextPlayer == 0 && player.getFigure().equals(X))
                || (whoIsNextPlayer == 1 && player.getFigure().equals(O))) {
            return true;
        } else {
            showInfo("Сейчас ход другого игрока!");
            return false;
        }
    }

    private EventHandler<MouseEvent> buildMouseEvent(Group panel) {
        return event -> {
            Figure3T rect = (Figure3T) event.getTarget();
            int currentActions;
            if (this.checkState() && !players[0].isWin() && !players[1].isWin()) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    if (isCellEmpty(rect) && checkPlayer(players[0])) {
                        rect.take(true);
                        panel.getChildren().add(
                                this.buildMarkX(rect.getX(), rect.getY(), 50));
                        whoIsNextPlayer++;
                        currentActions = players[0].getActions();
                        players[0].setActions(currentActions + 1);
                    }
                } else {
                    if (isCellEmpty(rect) && checkPlayer(players[1])) {
                        rect.take(false);
                        panel.getChildren().add(
                                this.buildMarkO(rect.getX(), rect.getY(), 50));
                        whoIsNextPlayer--;
                        currentActions = players[1].getActions();
                        players[1].setActions(currentActions + 1);
                    }
                }
                this.checkWinner();
                this.checkState();
            }
        };
    }

    private Group buildGrid() {
        Group panel = new Group();
        for (int y = 0; y != this.size; y++) {
            for (int x = 0; x != this.size; x++) {
                Figure3T rect = this.buildRectangle(x, y, 50);
                this.cells[y][x] = rect;
                panel.getChildren().add(rect);
                rect.setOnMouseClicked(this.buildMouseEvent(panel));
            }
        }
        return panel;
    }

    @Override
    public void start(Stage stage) {
        BorderPane border = new BorderPane();
        HBox control = new HBox();
        control.setPrefHeight(40);
        control.setSpacing(10.0);
        control.setAlignment(Pos.BASELINE_CENTER);
        Button start = new Button("Играть");
        start.setOnMouseClicked(
                event -> {
                    border.setCenter(this.buildGrid());
                    this.askPlayerName(new Player(X));
                    this.askPlayerName(new Player(O));
                }
        );
        control.getChildren().addAll(start);
        border.setBottom(control);
        border.setCenter(this.buildGrid());
        stage.setScene(new Scene(border, 300, 300));
        stage.setTitle(GAME_NAME);
        stage.setResizable(false);
        stage.show();
        this.showServiceInfo("");
    }
}
