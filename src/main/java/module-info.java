module FXEngine {
    requires javafx.graphics;
    requires spring.core;
    requires CommonInstruments.JDK17;
    requires spring.beans;
    requires spring.context;
    requires ch.qos.logback.classic;
    requires org.slf4j;
    requires ch.qos.logback.core;
    requires org.jetbrains.annotations;
    requires typesafe.config;
    requires org.apache.commons.configuration2;
    requires jsr305;
    requires javafx.fxml;
    requires com.google.common;
    requires javafx.controls;
    requires com.google.errorprone.annotations;
    requires FXEngine.Bridge;
}