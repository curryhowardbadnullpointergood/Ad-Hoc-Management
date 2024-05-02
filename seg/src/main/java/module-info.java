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
    requires mail;
    requires activation;
    requires com.google.api.services.gmail;
    requires org.apache.commons.codec;
    requires com.google.api.client;
    requires com.google.api.client.json.gson;
    requires google.api.client;
    requires com.google.api.client.extensions.jetty.auth;
    requires com.google.api.client.auth;
    requires com.google.api.client.extensions.java6.auth;

    opens soton.ac.uk.seg to javafx.fxml;
    exports soton.ac.uk.seg;
    opens soton.ac.uk.seg.scenes to javafx.fxml, javafx.graphics;
    exports soton.ac.uk.seg.scenes;


}