package com.example.cribbage;

public class Player {

    private boolean cribBool;
    private boolean turnBool;

    private int points;

    private Hand hand;

    private Hand crib;

    public Player(){
        this.points =0;
    }
    public Player(boolean b, int points) {
        this.hand = new Hand();
        this.points = 0;
    }

    public int getPoints() {

        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void addPoints(int points){this.points += points;}

    public void setCribBool() {
        if (cribBool)
            cribBool = false;
        else
            cribBool = true;
    }
    public void setTurnBool(){
        if (turnBool)
            turnBool = false;
        else
            turnBool = true;
    }
    public boolean getCribBool(){
        return cribBool;
    }

    public boolean getTurnBool(){
        return turnBool;
    }
    public Hand getHand() {
        return this.hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }
}
