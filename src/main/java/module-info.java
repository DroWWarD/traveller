module demo.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;


    opens demo.demo to javafx.fxml;
    exports demo.demo;
}