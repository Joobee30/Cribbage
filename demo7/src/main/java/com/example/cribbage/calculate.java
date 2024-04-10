package com.example.cribbage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class calculate {
    private Card[] cardList;
    private int total;
    private String score = "";

    public calculate() {
    }

    public calculate(Card[] cardList) {
        this.cardList = cardList;
        int doubles = doubles(cardList);
        int run = run(cardList);
        int flush = flush(cardList);
        int nob = nob(cardList);
        int fifteen = fifteen(cardList);

        this.score = "Doubles: " + doubles + " Runs: " + run + " Flushes: " + flush + " Nobs: " + nob + " Fifteens: " + fifteen;
        this.total = doubles + run + flush + nob + fifteen;
    }

    public void setCardList(Card[] cardList) {
        this.cardList = cardList;
        int doubles = doubles(cardList);
        int run = run(cardList);
        int flush = flush(cardList);
        int nob = nob(cardList);
        int fifteen = fifteen(cardList);

        this.score = "Doubles: " + doubles + " Runs: " + run + " Flushes: " + flush + " Nobs: " + nob + " Fifteens: " + fifteen;
        this.total = doubles + run + flush + nob + fifteen;
    }

    public int getTotal() {
        return total;
    }

    public Card[] getCardList() {
        return cardList;
    }

    public static void quickSort(Card arr[], int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end);

            quickSort(arr, begin, partitionIndex - 1);
            quickSort(arr, partitionIndex + 1, end);
        }
    }

    private static int partition(Card arr[], int begin, int end) {
        Card pivot = arr[end];
        int i = (begin - 1);

        for (int j = begin; j < end; j++) {
            if (arr[j].getValue() <= pivot.getValue()) {
                i++;

                Card swapTemp = arr[i];
                arr[i] = arr[j];
                arr[j] = swapTemp;
            }
        }

        Card swapTemp = arr[i + 1];
        arr[i + 1] = arr[end];
        arr[end] = swapTemp;

        return i + 1;
    }

    public static Card[] copy(Card[] cardList) {
        Card[] clone = new Card[5];
        for (int i = 0; i < cardList.length; i++) {
            clone[i] = cardList[i];
        }
        return clone;
    }

    public int doubles(Card[] cardList) {
        int counter = 0;
        int total = 0;
        List<Card> CardsUsed = new ArrayList<>(Arrays.asList(cardList));
        ArrayList<Integer> num = new ArrayList<Integer>();

        while (CardsUsed.size() > 1) {
            for (int i = 1; i < CardsUsed.size(); i++) {
                if (CardsUsed.get(0).getValue() == CardsUsed.get(i).getValue()) {
                    counter++;
                    num.add(i);
                }
            }

            if (counter == 1) {
                total += 2;
            }
            if (counter == 2) {
                total += 6;
            }
            if (counter == 3) {
                total += 12;
            }
            counter = 0;

            for (int i = num.size() - 1; i > 0; i--) {
                CardsUsed.remove((int) num.get(i));
            }
            CardsUsed.remove(0);
            num.clear();
        }

        return total;

    }

    public int run(Card[] cardList) {
        int counter = 0;
        Card[] clone = copy(cardList);
        quickSort(clone, 0, 4);

        if (clone[2].getValue() == clone[3].getValue() - 1) {
            counter++;
            if (clone[3].getValue() == clone[4].getValue() - 1) {
                counter++;
            }
        }
        if (clone[2].getValue() == clone[1].getValue() + 1) {
            counter++;
            if (clone[1].getValue() == clone[0].getValue() + 1) {
                counter++;
            }
        }

        if (counter < 2)
            return 0;
        else
            return counter + 1;
    }

    public int flush(Card[] cardList) {
        int placeholder = 0;
        for (int i = 1; i < cardList.length; i++) {
            if (cardList[0].getSuit() == cardList[i].getSuit())
                placeholder++;
        }
        if (placeholder == 2 || placeholder == 1) {
            return 0;
        } else if (placeholder == 3) {
            return 4;
        } else if (placeholder == 4) {
            return 5;
        } else {
            placeholder = 0;
            for (int i = 2; i < cardList.length; i++) {
                if (cardList[1].getSuit() == cardList[i].getSuit())
                    placeholder++;
            }
        }
        if (placeholder == 3)
            return 4;
        else
            return 0;
    }

    public int nob(Card[] cardList) {
        if (cardList[4].getValue() == 11) {
            for (int i = 0; i < cardList.length - 1; i++) {
                if (cardList[i].getSuit() == cardList[4].getSuit()) {
                    return 1;
                }
            }
        } else
            for (int i = 0; i < cardList.length - 1; i++) {
                if (cardList[i].getValue() == 11 && cardList[i].getSuit() == cardList[4].getSuit())
                    return 1;
            }

        return 0;
    }

    public int fifteen(Card[] cardList) {
        int counter = 0;
        List<Card> Cards1 = new ArrayList<Card>(Arrays.asList(cardList));
        List<Card> Cards2 = new ArrayList<Card>(Arrays.asList(cardList));

        while (Cards1.size() > 1) {
            for (int i = 1; i < Cards1.size(); i++) {
                if ((Cards1.get(0).getTrueValue() + Cards1.get(i).getTrueValue()) == 15)
                    counter += 2;
                Cards2.remove(Cards1.get(0));
                Cards2.remove(Cards1.get(i));
                if ((Cards2.get(0).getTrueValue() + Cards2.get(1).getTrueValue() + Cards2.get(2).getTrueValue()) == 15)
                    counter += 2;
                Cards2 = new ArrayList<Card>(Arrays.asList(cardList));
            }
            Cards1.remove(0);
        }
        for (int i = 0; i < Cards1.size(); i++) {
            Cards2.remove(i);
            if ((Cards2.get(0).getTrueValue() + Cards2.get(1).getTrueValue() + Cards2.get(2).getTrueValue()
                    + Cards2.get(3).getTrueValue()) == 15)
                counter += 2;
            Cards2 = Arrays.asList(cardList);
        }
        if ((Cards2.get(0).getTrueValue() + Cards2.get(1).getTrueValue() + Cards2.get(2).getTrueValue()
                + Cards2.get(3).getTrueValue() + Cards2.get(4).getTrueValue()) == 15)
            counter += 2;

        return counter;
    }

    public String toString(){
        return score;
    }

}
