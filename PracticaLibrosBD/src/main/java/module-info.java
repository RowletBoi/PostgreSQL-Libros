module org.example.practicalibrosbd {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.example.practicalibrosbd.connection to javafx.fxml;
    opens org.example.practicalibrosbd.controller to javafx.fxml;
    opens org.example.practicalibrosbd.model to javafx.fxml;
    opens org.example.practicalibrosbd to javafx.fxml;
    exports org.example.practicalibrosbd;
    exports org.example.practicalibrosbd.model;
    exports org.example.practicalibrosbd.controller;
}