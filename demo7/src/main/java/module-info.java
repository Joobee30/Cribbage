module com.example.cribbage {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.example.cribbage to javafx.fxml;
    exports com.example.cribbage;
}