module com.example.cafeteria {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.cafeteria to javafx.fxml;
    exports com.example.cafeteria;
}