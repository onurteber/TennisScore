package com.tennis.nr3;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

    public static final String PLAYER1 = "A";
    public static final String PLAYER2 = "B";

    public static void main(String args[]) {
        Scanner input = new Scanner(System.in);
        System.out.println("Input file name:");
        String inputFileName = input.nextLine();
        System.out.println("Output file name:");
        String outputFileName = input.nextLine();
        calculate(inputFileName, outputFileName);
    }

    public static void calculate(String inputFileName, String outputFileName){
        try {
            Game game = new Game();
            File myObj = new File(checkExtension(inputFileName));
            StringBuilder sb = new StringBuilder();
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                boolean isValidRow = true;
                String data = myReader.nextLine();
                boolean isNewGame = true;
                for (char player : data.toCharArray()) {
                    if(String.valueOf(player).equals(PLAYER1) || String.valueOf(player).equals(PLAYER2)){
                        game.updateScore(String.valueOf(player), isNewGame);
                        isNewGame = false;
                    } else {
                        isValidRow = false;
                        break;
                    }
                }
                if(isValidRow)
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

    public static String checkExtension(String name) {
        String file = name.contains(".txt") ? name : name.concat(".txt");
        return "files/".concat(file);
    }
}
