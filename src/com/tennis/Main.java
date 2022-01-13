package com.tennis;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

    public static final String PLAYER1 = "A";
    public static final String PLAYER2 = "B";

    public static void main(String args[]) {
        try {
            Game game = new Game();
            Scanner input = new Scanner(System.in);
            System.out.println("Input file name:");
            String inputFileName = input.nextLine();
            System.out.println("Output file name:");
            String outputFileName = input.nextLine();
            File myObj = new File(checkExtension(inputFileName));
            StringBuilder sb = new StringBuilder();
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                boolean isNewGame = true;
                for (char player : data.toCharArray()) {
                    game.updateScore(String.valueOf(player), isNewGame);
                    isNewGame = false;
                }
                sb = game.displayScore();
            }
            Files.write(Paths.get(checkExtension(outputFileName)), sb.toString().getBytes());
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String checkExtension(String name) {
        String file = name.contains(".txt") ? name : name.concat(".txt");
        return "files/".concat(file);
    }
}
