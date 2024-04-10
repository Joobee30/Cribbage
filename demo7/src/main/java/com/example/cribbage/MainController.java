package com.example.cribbage;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import java.util.ResourceBundle;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.*;
import static com.example.cribbage.Main.scene;



public class MainController implements Initializable {
    public static Stage stage;

    public static AnchorPane root;
    public Label playerPts = new Label("Player: " + 0+" points");
    public Label botPts = new Label("Bot: " + 0+" points");
    public Label info = new Label("");
    Player realPlayer = new Player(false, 0);
    Player botPlayer = new Player(false, 0);
    // Code the playing phase
    Hand realPlayerHand = new Hand();
    Hand botPlayerHand = new Hand();
    Hand cribHand = new Hand();
    Hand memory = new Hand();
    Hand memory2 = new Hand();
    Hand memory2Clone = new Hand();
    Hand memory3 = new Hand();
    Hand memory3Clone = new Hand();
    Card cribCard = new Card();
    ArrayList<ImageView> botHandIV = new ArrayList<ImageView>();
    int total = 0;
    calculate botHandpoints = new calculate();
    calculate realHandpoints = new calculate();
    calculate cribHandpoints = new calculate();
    public Label totalLbl = new Label("Total: "+total);
    ArrayList<Card> tempCribCards = new ArrayList<Card>();

    Button helpBtn = new Button("Help");
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    int pointsToWin = 10;




    @Override
    public void initialize(URL location, ResourceBundle resources){
        /*volumeSlider.setValue(mediaPlayer.getVolume() * 100);
        volumeSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                mediaPlayer.setVolume(volumeSlider.getValue() / 100);
            }
        });*/

    }
    @FXML
    public void onClickPlay(ActionEvent event) throws IOException {
        Deck deck = new Deck();
        deck.shuffle();
        /*realPlayer = new Player(false, 0);
        botPlayer = new Player(false, 0);*/
        root = FXMLLoader.load(getClass().getResource("play.fxml"));
        root.setId("pane");

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        //        scene = new Scene(root, scene.getWidth(), scene.getHeight());
        stage.getScene().setRoot(root);
        scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());

        stage.setScene(scene);
        stage.show();

        alert.setHeaderText("This is the part of the game that tells you who goes first and who will get the crib " +
                "hand points at the end.\n                  Whoever has the highest card gets the crib hand points and passes the cards.");
        alert.setTitle("Help for you!");
        helpBtn.relocate(root.getWidth()/500 * 447, root.getHeight()/500 * 5);
        helpBtn.setFont(new Font("Copperplate Gothic Bold", 24));
        helpBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                alert.showAndWait();
            }
        });
        root.getChildren().remove(helpBtn);
        root.getChildren().add(helpBtn);

        int multiplicator = (int) (stage.getScene().getWidth() - 200) / 52;
        int halfOfPage = (int) stage.getScene().getHeight() / 2;
        ImageView[] imageViews = new ImageView[52];

        // Imageviews of the turned cards by the player and the bot
        ImageView turnedCardPlayer = new ImageView();
        ImageView turnedCardBot = new ImageView();
        Label playerCardLabel = new Label("Your card");
        Label botCardLabel = new Label("Bot's card");
        playerCardLabel.setFont(new Font("Impact", 30));
        botCardLabel.setFont(new Font("Impact", 30));


        playerCardLabel.setTextAlignment(TextAlignment.CENTER);
        botCardLabel.setTextAlignment(TextAlignment.CENTER);


        HBox labelHbox = new HBox();
        labelHbox.setPadding(new Insets(50, 50, 400, 300));
        labelHbox.setSpacing(200);
        labelHbox.getChildren().addAll(playerCardLabel,botCardLabel);

        HBox ivHbox = new HBox();
        ivHbox.setPadding(new Insets(50, 50, 400, 300));
        ivHbox.setSpacing(200);
        ivHbox.getChildren().addAll(turnedCardPlayer, turnedCardBot);

        turnedCardPlayer.setX(stage.getScene().getWidth() / 2 - 200);
        turnedCardPlayer.setY(halfOfPage - 200);


        turnedCardBot.setX(stage.getScene().getWidth() / 2 + 200);
        turnedCardBot.setY(halfOfPage - 200);


        root.getChildren().addAll(labelHbox, ivHbox);

        // Creates and shows the 52 imageviews for the cards
        for (int i = 0; i < 52; i++) {
            ImageView imageview = imageViews[i];
            Card card = deck.getCard(i);
            imageViews[i] = new ImageView(card.getImage());
            // rowOfCards.getChildren().add(imageview);

            imageViews[i].setX((100 + (i * multiplicator)));
            imageViews[i].setY(halfOfPage);
            root.getChildren().add(imageViews[i]);

        }

        // Adds an event handler to all 52 imageviews which turns the card when they are
        // clicked

        for (int i = 0; i < 52; i++) {
            Card card = deck.getCard(i);
            ImageView imageView = imageViews[i];

            imageViews[i].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {

                    card.turnCard();
                    imageView.setImage(card.getImage());
                    event.consume();
                    imageView.setDisable(true);

                    // Makes it so clicking on the anchor pane scene will not turn any more cards
                    root.setDisable(true);
                    // rowOfCards.setDisable(true);
                    turnedCardPlayer.setImage(card.getImage());

                    int rand = (int) (52 * Math.random());
                    Card randomCard = deck.getCard(rand);

                    // If the random card is already turned, get new random factor, then turn the
                    // card and set the imageview with the same index to the active image
                    while (randomCard.getImage() == randomCard.getfrImage()) {
                        rand = (int) (52 * Math.random());
                    }
                    // Turn the random card
                    randomCard.turnCard();

                    imageViews[rand].setImage(randomCard.getImage());
                    turnedCardBot.setImage(randomCard.getImage());

                    if (card.getValue() > randomCard.getValue()) {
                        botPlayer.setCribBool();

                    } else {
                        realPlayer.setCribBool();
                        
                    }

                    Timeline fiveSecondsWonder = new Timeline(
                            new KeyFrame(Duration.seconds(3),
                                    new EventHandler<ActionEvent>() {

                                        @Override
                                        public void handle(ActionEvent event) {
                                            try {
                                                setupPhase();
                                            } catch (IOException e) {
                                                // TODO Auto-generated catch block
                                                System.out.println(e);
                                            }
                                        }
                                    }));
                    fiveSecondsWonder.setCycleCount(1);
                    fiveSecondsWonder.play();

                }
            });
        }
        ;

    }
    public void setupPhase() throws IOException{
        Deck deck = new Deck();
        deck.shuffle();
        total = 0;

        alert.setHeaderText("     In this part of the game you have to choose 2 cards to discard.\n" +
                "Try to choose the best cards to get the best point combos in the end!");

        botPlayerHand.clear();
        realPlayerHand.clear();
        cribHand.clear();

        if(realPlayer.getTurnBool())
            realPlayer.setTurnBool();
        else if(botPlayer.getTurnBool())
            botPlayer.setTurnBool();

        botPlayer.setCribBool();
        realPlayer.setCribBool();

        if(realPlayer.getCribBool())
            botPlayer.setTurnBool();
        else
            realPlayer.setTurnBool();

        if (realPlayer.getCribBool()) {
            for (int i = 0; i < 6; i++) {
                botPlayerHand.addCard(deck.dealCard());
                realPlayerHand.addCard(deck.dealCard());
            }

        } else {
            for (int i = 0; i < 6; i++) {
                realPlayerHand.addCard(deck.dealCard());
                botPlayerHand.addCard(deck.dealCard());
            }
        }

        cribCard = deck.dealCard();
        // Code the playing phase
        realPlayer.setHand(realPlayerHand);
        botPlayer.setHand(botPlayerHand);

        memory = realPlayerHand.copy();

        realPlayerHand.calculate();

        int randomNum = (int) (Math.random()/6);
        cribHand.addCard(botPlayerHand.dealDiscardCard(randomNum));
        randomNum = (int) (Math.random()/5);
        cribHand.addCard(botPlayerHand.dealDiscardCard(randomNum));
        
        playingPhase();
    }

    public void playingPhase() throws IOException {

        root = FXMLLoader.load(getClass().getResource("playPhase.fxml"));
        root.setId("pane");
        //        scene = new Scene(root, scene.getWidth(), scene.getHeight());
        stage.getScene().setRoot(root);
        scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();

        helpBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                alert.showAndWait();
            }
        });
        root.getChildren().remove(helpBtn);
        root.getChildren().add(helpBtn);

        info.setMaxWidth(Double.MAX_VALUE);
        AnchorPane.setLeftAnchor(info, 0.0);
        AnchorPane.setRightAnchor(info, 0.0);
        info.setAlignment(Pos.CENTER);
        info.setFont(new Font("Impact", 15));
        info.setText("Best Hand: " + realPlayerHand.getBestHand());
        info.setLayoutY(root.getHeight()/2-50);
        root.getChildren().add(info);

        playerPts.relocate(stage.getScene().getWidth()/100,stage.getScene().getHeight() / 100);
        playerPts.setText("Player: "+ realPlayer.getPoints()+" points");
        playerPts.setFont(new Font("Impact", 30));
        root.getChildren().add(playerPts);

        botPts.relocate(stage.getScene().getWidth()/100,stage.getScene().getHeight() / 100 * 5 );
        botPts.setText("Bot: "+ botPlayer.getPoints()+" points");
        botPts.setFont(new Font("Impact", 30));
        root.getChildren().add(botPts);

        totalLbl.relocate(stage.getScene().getWidth()/2,stage.getScene().getHeight() / 100);
        totalLbl.setFont(new Font("Impact", 30));
        totalLbl.setText("Total: "+total);
        root.getChildren().add(totalLbl);

        tempCribCards.clear();

        double multiplicator = stage.getScene().getWidth() / (realPlayerHand.size() + 2);
        double halfOfPage = stage.getScene().getHeight() / 2;
        double raisedUpCard = stage.getScene().getHeight()/ 2 -30;

        final EventHandler eventHandler = new EventHandler<MouseEvent> () {
            @Override
            public void handle(MouseEvent event) {
                final ImageView iv = (ImageView) (event.getTarget());
                for(int i = 0 ; i < memory.size() ; i++){
                    if(memory.getCard(i).getImageView() == iv) {
                            tempCribCards.add(memory.getCard(i));
                        memory.discardCard(i);
                    }
                }
                iv.setY(raisedUpCard);
                event.consume();

                if(memory.size() < 5){
                    for(int i = 0 ; i < realPlayerHand.size() ; i++){
                        ImageView temp = new ImageView(realPlayerHand.getCard(i).getfrImage());
                        temp.setX(realPlayerHand.getCard(i).getImageView().getX());
                        temp.setY(realPlayerHand.getCard(i).getImageView().getY());
                        root.getChildren().add(temp);
                    }

                    Button reset = new Button("Reset");
                    reset.setOnAction(new EventHandler<ActionEvent>() {
                        @Override public void handle(ActionEvent e) {
                            for(int i = 0 ; i < realPlayerHand.size() ; i++){
                                realPlayerHand.getCard(i).getImageView().setY(halfOfPage);
                            }
                            tempCribCards.clear();
                            memory = realPlayerHand.copy();
                            try {
                                playingPhase();
                            } catch (IOException e1) {
                                // TODO Auto-generated catch block
                            }
                        }
                    });
                    reset.setLayoutY((int)halfOfPage-90);
                    reset.setLayoutX((stage.getScene().getWidth()/2)+50);

                    Button next = new Button("Next");
                    next.setOnAction(new EventHandler<ActionEvent>() {
                        @Override public void handle(ActionEvent e) {
                            try {
                                for(int i = 0 ; i < tempCribCards.size() ; i++){
                                    cribHand.addCard(tempCribCards.get(i));
                                    realPlayerHand.discardCard(tempCribCards.get(i));
                                }

                                for(int i = 0 ; i < realPlayerHand.size() ; i++){
                                    Card temp = new Card(realPlayerHand.getCard(i).getSuit(),realPlayerHand.getCard(i).getValue());
                                    memory3.addCard(temp);
                                }
                                memory = realPlayerHand.copy();
                                memory2 = botPlayerHand.copy();
                                setupPhase2();
                            } catch (IOException e1) {
                                // TODO Auto-generated catch block
                            }
                        }
                    });
                    next.setLayoutY((int)halfOfPage-90);
                    next.setLayoutX((stage.getScene().getWidth()/2) - 50);

                    root.getChildren().add(reset);
                    root.getChildren().add(next);
                }
            }
        };

        for (int i = 0; i < realPlayerHand.size(); i++) {
            if(realPlayerHand.getCard(i).getImageView().getY() == raisedUpCard)
                realPlayerHand.getCard(i).getImageView().setY(raisedUpCard);
            else
                realPlayerHand.getCard(i).getImageView().setY(halfOfPage);
            realPlayerHand.getCard(i).getImageView().setX(multiplicator * (i+1) );
            realPlayerHand.getCard(i).getImageView().addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
            root.getChildren().add(realPlayerHand.getCard(i).getImageView());
        }

    }

    public void setupPhase2() throws IOException{
        double halfOfPage = stage.getScene().getHeight() / 4;
        double multiplicator = stage.getScene().getWidth() / (memory3.size()+2);

        alert.setHeaderText("In this part of the game, try to get the closest to 31 in total..." +
                "\n       your face cards have all the same value of 10.");

        Card[] temp = new Card[5];
        temp[4] = cribCard;
        for(int i = 0 ; i < temp.length-1 ; i++){
            temp[i] = memory2.getCard(i);
        }
        botHandpoints.setCardList(temp);
        for(int i = 0 ; i < temp.length-1 ; i++){
            temp[i] = memory3.getCard(i);
        }
        realHandpoints.setCardList(temp);
        for(int i = 0 ; i < temp.length-1 ; i++){
            temp[i] = cribHand.getCard(i);
        }
        cribHandpoints.setCardList(temp);

        memory2Clone = memory2.copy();
        memory3Clone = memory3.copy();

        final EventHandler eventHandler = new EventHandler<MouseEvent> () {
            @Override
            public void handle(MouseEvent event) {
                final ImageView iv = (ImageView) (event.getTarget());
                ImageView temp = new ImageView(iv.getImage());
                for(int i = 0 ; i < memory3.size() ; i++){
                    if(iv.getImage().equals(memory3.getCard(i).getImageView().getImage())){
                        total += memory3.getCard(i).getTrueValue();
                        totalLbl.setText("Total: "+total);
                        memory3.discardCard(i);
                    }
                }
                temp.setX(stage.getScene().getWidth() / 2);
                temp.setY((stage.getScene().getHeight() / 2)-40);
                root.getChildren().add(temp);
                root.getChildren().remove(iv);
                realPlayer.setTurnBool();
                botPlayer.setTurnBool();

                if(memory2.size()>0){
                    int counter2 = 0;
                    for(int i = 0 ; i < memory2.size() ; i++){
                        if(total + memory2.getCard(i).getTrueValue() > 31)
                            counter2++;
                    }
                    if(counter2 == memory2.size()){
                        if(total == 31) {
                            realPlayer.addPoints(2);
                            try {
                                checkEnd();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        else {
                            realPlayer.addPoints(1);
                            try {
                                checkEnd();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        playerPts.setText("Player: "+ realPlayer.getPoints()+" points");
                        try {
                            playingPhase2();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }else{
                        int randomNum = (int)Math.random()/memory2.size();
                        while(total + memory2.getCard(randomNum).getTrueValue()>31){
                            randomNum = (int)Math.random()/memory2.size();
                        }
                        memory2.getCard(randomNum).getImageView().relocate((stage.getScene().getWidth() / 2),(stage.getScene().getHeight() / 2)-40);
                        root.getChildren().add(memory2.getCard(randomNum).getImageView());
                        total += memory2.getCard(randomNum).getTrueValue();
                        totalLbl.setText("Total: "+total);
                        root.getChildren().remove(botHandIV.get(randomNum));
                        memory2.discardCard(randomNum);
                        botHandIV.remove(randomNum);
                        realPlayer.setTurnBool();
                        botPlayer.setTurnBool();

                        if(memory3.size()>0){
                            int counter = 0;
                            for(int i = 0 ; i < memory3.size() ; i++){
                                if(total + memory3.getCard(i).getTrueValue() > 31)
                                    counter++;
                            }
                            if(counter == memory3.size()){
                                Button go = new Button("Go");
                                for(int i = 0 ; i < memory3.size() ; i++){
                                    ImageView canceller = new ImageView(memory3.getCard(i).getfrImage());
                                    canceller.relocate(memory3.getCard(i).getImageView().getX(),memory3.getCard(i).getImageView().getY());
                                    root.getChildren().add(canceller);
                                }
                                go.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override public void handle(ActionEvent e) {
                                        if(total == 31) {
                                            botPlayer.addPoints(2);
                                            try {
                                                checkEnd();
                                            } catch (IOException ex) {
                                                throw new RuntimeException(ex);
                                            }
                                        }
                                        else {
                                            botPlayer.addPoints(1);
                                            try {
                                                checkEnd();
                                            } catch (IOException ex) {
                                                throw new RuntimeException(ex);
                                            }
                                        }
                                        botPts.setText("Bot: "+ botPlayer.getPoints()+" points");
                                        try {
                                            playingPhase2();
                                        } catch (IOException e1) {
                                            // TODO Auto-generated catch block
                                        }
                                    }
                                });
                                go.setLayoutY((int)halfOfPage-70);
                                go.setLayoutX((stage.getScene().getWidth()/2)+50);
                                root.getChildren().add(go);
                            }else{
                                for(int i = 0 ; i < memory3.size() ; i++){
                                    if(total + memory3.getCard(i).getTrueValue() > 31){
                                        ImageView photoCancel = new ImageView();
                                        photoCancel.relocate(memory3.getCard(i).getImageView().getX(),memory3.getCard(i).getImageView().getY());
                                    }
                                }
                            }
                        }
                        else{
                            if(total == 31) {
                                botPlayer.addPoints(2);
                                try {
                                    checkEnd();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            else {
                                botPlayer.addPoints(1);
                                try {
                                    checkEnd();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            try {
                                playingPhase2();
                            } catch (IOException e1) {
                                // TODO Auto-generated catch block
                            }
                        }
                    }
                }else{
                    if(total == 31) {
                        realPlayer.addPoints(2);
                        try {
                            checkEnd();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    else {
                        realPlayer.addPoints(1);
                        try {
                            checkEnd();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    playerPts.setText("Player: "+ realPlayer.getPoints()+" points");
                    if(memory2.size() == 0 && memory3.size() == 0) {
                        try {
                            scoreCounting(0);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }else{
                        try {
                            playingPhase2();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                event.consume();
            }
        };
        for(int i = 0 ; i < memory3.size(); i++){
            memory3.getCard(i).getImageView().addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
            memory3.getCard(i).getImageView().setX(multiplicator * (i+1));
            memory3.getCard(i).getImageView().setY(halfOfPage*3);
        }
        for(int i = 0 ; i < 4 ; i++){
            botHandIV.add(new ImageView(new Image("file:src/main/resources/com/example/cribbage/Playing Cards/card-back2.png")));
            botHandIV.get(i).relocate(multiplicator * (i+1),halfOfPage*1-40);
        }
        cribCard.getImageView().relocate((root.getWidth()/150)*5,halfOfPage*2);
        playingPhase2();
    }

    public void playingPhase2() throws IOException{
        root = FXMLLoader.load(getClass().getResource("playPhase.fxml"));
        root.setId("pane");
        //        scene = new Scene(root, scene.getWidth(), scene.getHeight());
        stage.getScene().setRoot(root);
        scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();

        helpBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                alert.showAndWait();
            }
        });
        root.getChildren().remove(helpBtn);
        root.getChildren().add(helpBtn);

        total = 0;

        totalLbl.setText("Total: "+total);
        root.getChildren().add(totalLbl);

        playerPts.setText("Player: "+ realPlayer.getPoints()+" points");
        botPts.setText("Bot: "+ botPlayer.getPoints()+" points");

        root.getChildren().add(playerPts);
        root.getChildren().add(botPts);

        root.getChildren().add(cribCard.getImageView());


        for(int i = 0 ; i < memory3.size() ; i++){
            root.getChildren().remove(memory.getCard(i).getImageView());
            root.getChildren().remove(realPlayerHand.getCard(i).getImageView());
            root.getChildren().add(memory3.getCard(i).getImageView());
        }
        for(int i = 0 ; i < botHandIV.size() ; i++){
            root.getChildren().add(botHandIV.get(i));
        }

        if(botPlayer.getTurnBool()&&memory2.size()>0){
            int randomNum = (int)Math.random()/memory2.size();
            memory2.getCard(randomNum).getImageView().relocate((stage.getScene().getWidth() / 2),(stage.getScene().getHeight() / 2)-40);
            root.getChildren().add(memory2.getCard(randomNum).getImageView());
            total += memory2.getCard(randomNum).getTrueValue();
            totalLbl.setText("Total: "+total);
            root.getChildren().remove(botHandIV.get(randomNum));
            memory2.discardCard(randomNum);
            botHandIV.remove(randomNum);
            if(memory3.size() == 0) {
                botPlayer.addPoints(1);
                checkEnd();
                botPts.setText("Bot: "+ botPlayer.getPoints()+" points");
                try {
                    playingPhase2();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            realPlayer.setTurnBool();
            botPlayer.setTurnBool();
        }else if(memory2.size() == 0 && memory3.size() == 0) {
            try {
                scoreCounting(0);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void scoreCounting(int i) throws IOException{
        root = FXMLLoader.load(getClass().getResource("playPhase.fxml"));
        root.setId("pane");
        //        scene = new Scene(root, scene.getWidth(), scene.getHeight());
        stage.getScene().setRoot(root);
        scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();

        alert.setHeaderText("Just score countin! Nothing to see here ;)");
        helpBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                alert.showAndWait();
            }
        });
        root.getChildren().remove(helpBtn);
        root.getChildren().add(helpBtn);

        playerPts.setText("Player: "+ realPlayer.getPoints()+" points");
        botPts.setText("Bot: "+ botPlayer.getPoints()+" points");

        root.getChildren().add(playerPts);
        root.getChildren().add(botPts);
        root.getChildren().add(cribCard.getImageView());

        double multiplicator = stage.getScene().getWidth() / 6;
        double halfOfPage = stage.getScene().getHeight() / 2;

        Button next = new Button("Next");
        next.relocate(root.getWidth()/2,halfOfPage - 90);
        next.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                if(realPlayer.getCribBool()){
                    switch(i){
                        case 0:
                            botPlayer.addPoints(botHandpoints.getTotal());
                            try {
                                checkEnd();
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                            break;
                        case 1:
                            realPlayer.addPoints(realHandpoints.getTotal());
                            try {
                                checkEnd();
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                            break;
                        case 2:
                            realPlayer.addPoints(cribHandpoints.getTotal());
                            try {
                                checkEnd();
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                            break;
                    }
                }
                else{
                    switch(i){
                        case 0:
                            realPlayer.addPoints(realHandpoints.getTotal());
                            try {
                                checkEnd();
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                            break;
                        case 1:
                            botPlayer.addPoints(botHandpoints.getTotal());
                            try {
                                checkEnd();
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                            break;
                        case 2:
                            botPlayer.addPoints(cribHandpoints.getTotal());
                            try {
                                checkEnd();
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                            break;
                    }
                }
                if(i<2)
                    try {
                        if (realPlayer.getPoints() >= pointsToWin || botPlayer.getPoints() >= pointsToWin) {

                        }
                        else{
                            scoreCounting(i + 1);
                        }
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                }else
                    try {
                        if (realPlayer.getPoints() >= pointsToWin || botPlayer.getPoints() >= pointsToWin){

                        }
                        else{
                            setupPhase();
                        }


                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
            }
        });

        root.getChildren().add(next);

        if(realPlayer.getCribBool()){
            switch(i){
                case 0:
                    for(int j = 0 ; j < memory2Clone.size() ; j++){
                        info.setText("The bot's hand score: "+botHandpoints);
                        ImageView temp = new ImageView(memory2Clone.getCard(j).getfrImage());
                        temp.relocate(multiplicator*(j+1),halfOfPage);
                        root.getChildren().add(temp);
                        root.getChildren().remove(info);
                        root.getChildren().add(info);
                    }
                    break;
                case 1:
                    for(int j = 0 ; j < memory3Clone.size() ; j++){
                        info.setText("Your hand's score: "+realHandpoints);
                        ImageView temp = new ImageView(memory3Clone.getCard(j).getfrImage());
                        temp.relocate(multiplicator*(j+1),halfOfPage);
                        root.getChildren().add(temp);
                        root.getChildren().remove(info);
                        root.getChildren().add(info);
                    }
                    break;
                case 2:

                    for(int j = 0 ; j < cribHand.size() ; j++){
                        info.setText("The crib hand given to you's score: "+cribHandpoints);
                        ImageView temp = new ImageView(cribHand.getCard(j).getfrImage());
                        temp.relocate(multiplicator*(j+1),halfOfPage);
                        root.getChildren().add(temp);
                        root.getChildren().remove(info);
                        root.getChildren().add(info);
                    }
                    break;
            }
        }
        else{
            switch(i){
                case 0:
                    for(int j = 0 ; j < memory3Clone.size() ; j++){
                        info.setText("Your hand's score: "+realHandpoints);
                        ImageView temp = new ImageView(memory3Clone.getCard(j).getfrImage());
                        temp.relocate(multiplicator*(j+1),halfOfPage);
                        root.getChildren().add(temp);
                        root.getChildren().remove(info);
                        root.getChildren().add(info);
                    }
                    break;
                case 1:
                    for(int j = 0 ; j < memory2Clone.size() ; j++){
                        info.setText("The bot's hand score: "+botHandpoints);
                        ImageView temp = new ImageView(memory2Clone.getCard(j).getfrImage());
                        temp.relocate(multiplicator*(j+1),halfOfPage);
                        root.getChildren().add(temp);
                        root.getChildren().remove(info);
                        root.getChildren().add(info);
                    }
                    break;
                case 2:

                    for(int j = 0 ; j < cribHand.size() ; j++){
                        info.setText("The crib hand given to the bot's score: "+cribHandpoints);
                        ImageView temp = new ImageView(cribHand.getCard(j).getfrImage());
                        temp.relocate(multiplicator*(j+1),halfOfPage);
                        root.getChildren().add(temp);
                        root.getChildren().remove(info);
                        root.getChildren().add(info);
                    }
                    break;
            }
        }
    }
    public void checkEnd() throws IOException {
        if(realPlayer.getPoints()>= pointsToWin)
            endPhase(1);
        if(botPlayer.getPoints()>=pointsToWin)
            endPhase(2);
    }
    public void endPhase(int i) throws IOException {
        root = FXMLLoader.load(getClass().getResource("endPhase.fxml"));
        root.setId("pane");
        //        scene = new Scene(root, scene.getWidth(), scene.getHeight());
        stage.getScene().setRoot(root);
        scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
        Label endWinner = new Label();
        endWinner.setFont(new Font("Impact",  120));

        AnchorPane.setLeftAnchor(endWinner, 100.0);
        AnchorPane.setRightAnchor(endWinner, 200.0);
        AnchorPane.setTopAnchor(endWinner, 100.0);
        AnchorPane.setBottomAnchor(endWinner, 100.0);

        if (i == 1){
            endWinner.setText("You Win!");
        }
        else{
            endWinner.setText("You Lose!");
        }
        root.getChildren().add(endWinner);


    }
    @FXML
    public void onClickTutorial(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("chart.fxml"));
        root.setId("pane");
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        //        scene = new Scene(root, scene.getWidth(), scene.getHeight());
        stage.getScene().setRoot(root);
        scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();

        Image chart = new Image("file:src/main/resources/com/example/cribbage/cribbageChart.png");
        ImageView iv = new ImageView(chart);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(iv);
        scrollPane.setPannable(true);
        root.getChildren().add(scrollPane);
        AnchorPane.setRightAnchor(scrollPane, 200.0);
    }


    @FXML
    public void onClickSettings(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("settings.fxml"));
        root.setId("pane");
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        //        scene = new Scene(root, scene.getWidth(), scene.getHeight());
        stage.getScene().setRoot(root);
        scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        stage.setScene(scene);

        stage.show();


    }
    @FXML
    public void onClickMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("interface.fxml"));
        root.setId("pane");
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        scene = new Scene(root, scene.getWidth(), scene.getHeight());
        stage.getScene().setRoot(root);
        scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        stage.setScene(scene);

        stage.show();
    }




}


