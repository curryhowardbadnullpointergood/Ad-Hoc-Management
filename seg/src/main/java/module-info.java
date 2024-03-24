module soton.ac.uk.seg {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;

    opens soton.ac.uk.seg to javafx.fxml;
    exports soton.ac.uk.seg;
    opens soton.ac.uk.seg.scenes to javafx.fxml, javafx.graphics;
    exports soton.ac.uk.seg.scenes;


}