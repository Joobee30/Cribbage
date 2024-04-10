package com.example.cribbage;

import java.util.ArrayList;

public class Hand {

    private ArrayList<Card> handArrayList;
    private Hand bestHand;

    public Hand() {
        this.handArrayList = new ArrayList<Card>();
    }

    public Hand(ArrayList<Card> handArrayList) {
        this.handArrayList = handArrayList;

    }

    public void addCard(Card c) {
        handArrayList.add(c);
    }

    public void discardCard(Card c) {
        handArrayList.remove(c);
    }

    public void discardCard(int i){
        handArrayList.remove(i);
    }

    public Card dealDiscardCard(int i) {
        Card temp = handArrayList.get(i);
        handArrayList.remove(i);
        return temp;
    }

    public Hand copy() {
        ArrayList<Card> clone = (ArrayList<Card>) handArrayList.clone();
        Hand handClone = new Hand(clone);
        return handClone;
    }

    public void clear(){
        handArrayList.clear();
    }

    public int size() {
        return handArrayList.size();
    }

    public Hand getBestHand() {
        return bestHand;
    }

    public Card getCard(int i) {
        return handArrayList.get(i);
    }


    public void calculate() {
        Deck restOfCards = new Deck();
        Card[] cardList = new Card[5];
        double average = 0;
        double bestAverage = 0;
        int n = 0;
        ArrayList<Card> cardList1 = (ArrayList<Card>) handArrayList.clone();
        ArrayList<Card> cardList2 = (ArrayList<Card>) handArrayList.clone();

        for (int i = 0; i < handArrayList.size(); i++) {
            restOfCards.removeCard(handArrayList.get(i));
        }

        while (cardList1.size() > 1) {
            for (int i = 1; i < cardList1.size(); i++) {
                cardList2.remove(cardList1.get(i));
                cardList2.remove(cardList1.get(0));

                for (int j = 0; j < restOfCards.size(); j++) {
                    cardList2.add(restOfCards.getCard(j));
                    cardList = cardList2.toArray(cardList);
                    calculate calc = new calculate(cardList);
                    cardList2.remove(restOfCards.getCard(j));
                    average += calc.getTotal();
                    n++;
                    System.out.println(calc.getTotal());
                }
                average /= 46;
                if (average > bestAverage) {
                    this.bestHand = new Hand((ArrayList<Card>)cardList2.clone());
                    bestAverage = average;
                }
                average = 0;
                cardList2 = (ArrayList<Card>) handArrayList.clone();
            }
            cardList1.remove(0);
        }
        
        System.out.println(bestHand.getCard(0).getSuit() + "" +
        bestHand.getCard(0).getValue() + " "
        + bestHand.getCard(1).getSuit() + "" + bestHand.getCard(1).getValue() + " "
        + bestHand.getCard(2).getSuit() + "" + bestHand.getCard(2).getValue() + " "
        + bestHand.getCard(3).getSuit() + "" + bestHand.getCard(3).getValue() + " " + n);
         
    }

    public String toString(){
        String temp = "";
        for(int i = 0; i < handArrayList.size() ; i++){
            temp += handArrayList.get(i) + ", ";
        }
        return temp.substring(0,temp.length()-2);
    }
}
