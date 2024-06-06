module com.example.av4luciano {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires org.json;
    requires java.logging;
    requires org.jetbrains.annotations;

    exports view;
    opens view to javafx.fxml;

    exports client;
    opens client to javafx.fxml;

    exports model;
    opens model to javafx.base;
}
