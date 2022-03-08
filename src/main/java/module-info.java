module ru.example.homework2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens ru.example.homework2 to javafx.fxml;
    exports ru.example.homework2;
}