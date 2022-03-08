package ru.example.homework2;

import java.io.*;

public class Statistic {
    public static void writeWinResultToFile(String fileName, Player player) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(player.toString() + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
