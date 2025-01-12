module org.example.project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;


    opens org.example.project to javafx.fxml;
    exports org.example.project;
    exports org.example.project.Controller;
    opens org.example.project.Controller to javafx.fxml;
}