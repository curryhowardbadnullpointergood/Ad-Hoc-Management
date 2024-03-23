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

    opens soton.ac.uk.seg to javafx.fxml;
    exports soton.ac.uk.seg;
    exports soton.ac.uk.seg.scenes.login;
    opens soton.ac.uk.seg.scenes.login to javafx.fxml;
    exports soton.ac.uk.seg.scenes.dashboard;
    opens soton.ac.uk.seg.scenes.dashboard to javafx.fxml;

}