package it.polimi.group11.controller;

import java.util.Scanner;

import it.polimi.group11.model.GameFirstReleaseTest;

public class MainSystem {
    private GameFirstReleaseTest gameFirstReleaseTest;

    public void newGame(){
        gameFirstReleaseTest = new GameFirstReleaseTest(setPlayersNumber());
    }

    public static void main(String[] args){
        MainSystem system = new MainSystem();
        system.newGame();
    }

    private int setPlayersNumber(){
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter a number of players: ");
        int playersNumber = reader.nextInt();
        if(playersNumber < 2 && playersNumber > 4){
            System.out.println("error: the number of players is invalid, it must be from 2 to 4");
            setPlayersNumber();
        }
        reader.close();
        return playersNumber;
    }
}