module com.example.proectcafe {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.proectcafe to javafx.fxml;
    exports com.example.proectcafe;
}