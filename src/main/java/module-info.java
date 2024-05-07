module at.ac.fhcampuswien.fhmdb {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.jfoenix;
    requires org.apache.httpcomponents.httpclient;
    requires org.apache.httpcomponents.httpcore;
    requires com.fasterxml.jackson.databind;

    opens at.ac.fhcampuswien.fhmdb.control to javafx.fxml;
    opens at.ac.fhcampuswien.fhmdb.models to com.fasterxml.jackson.databind;
    opens at.ac.fhcampuswien.fhmdb to javafx.fxml;
    exports at.ac.fhcampuswien.fhmdb.control to javafx.fxml;
    exports at.ac.fhcampuswien.fhmdb;

}