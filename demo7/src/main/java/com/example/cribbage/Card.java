package com.example.cribbage;

import javafx.scene.image.*;

public class Card {
    private final int suit;
    private final int value;
    private final int truevalue;

    private ImageView imageView;
    private Image activeImage;
    private Image frontImage;
    private Image backImage;
    private boolean front = false;

    public Card(int suit, int value) {
        this.suit = suit;
        this.value = value;
        if (value > 10)
            truevalue = 10;
        else
            truevalue = value;

        String fileName = "";
        switch (suit) {
            case 0:
                fileName = "card-" + "hearts-" + value + ".png";
                break;
            case 1:
                fileName = "card-" + "spades-" + value + ".png";
                break;
            case 2:
                fileName = "card-" + "clubs-" + value + ".png";
                break;
            case 3:
                fileName = "card-" + "diamonds-" + value + ".png";
                break;
        }

        this.frontImage = new Image(
                "file:src/main/resources/com/example/cribbage/Playing Cards/" + fileName);
        this.backImage = new Image("file:src/main/resources/com/example/cribbage/Playing Cards/card-back2.png");

        this.activeImage = this.backImage;

        this.imageView = new ImageView(this.frontImage);
    }

    public Card() {
        this.suit = 0;
        this.value = 0;
        this.truevalue = 0;
    }

    public int getSuit() {
        return this.suit;
    }

    public int getValue() {
        return this.value;
    }

    public int getTrueValue() {
        return this.truevalue;
    }
    public ImageView getImageView(){return this.imageView;}

    public Image getImage() {
        return activeImage;
    }

    public Image getfrImage() {
        return frontImage;
    }

    public void setFrontImage(Image activeImage) {
        this.frontImage = activeImage;
    }

    public void turnCard() {
        if (front) {
            this.activeImage = backImage;
            front = !front;
        } else {
            this.activeImage = frontImage;
            front = !front;
        }
    }

    public String toString(){
        String name = "";
        if(value > 10 || value == 1)
            switch (value) {
                case 1:
                    name += "Ace";
                    break;
                case 11:
                    name += "Jack";
                    break;
                case 12:
                    name += "Queen";
                    break;
                case 13:
                    name += "King";
                    break;
            }
        else
            name += ""+value;

        switch (suit) {
            case 0:
                name += " of Hearts";
                break;
            case 1:
                name += " of Spades";
                break;
            case 2:
                name += " of Clubs";
                break;
            case 3:
                name += " of Diamonds";
                break;
            }
            
        return name;
    }

}
