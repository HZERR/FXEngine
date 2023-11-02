package ru.hzerr.fx.engine.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import ru.hzerr.fx.engine.annotation.RegisteredAs;
import ru.hzerr.fx.engine.core.InitializationException;
import ru.hzerr.fx.engine.interfaces.Initializer;

import java.io.IOException;

@RegisteredAs("structureInitializer")
public class StructureInitializer implements Initializer {

    private IStructureConfiguration configuration;

    @Autowired
    public StructureInitializer(IStructureConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void initialize() throws InitializationException {
        try {
            configuration.getApplicationDirectory().create();
        } catch (IOException e) {
            throw new InitializationException(String.format("It is not possible to create a directory on the path '%s'", configuration.getApplicationDirectory().getLocation()), e);
        }
        try {
            configuration.getLoggingDirectory().create();
        } catch (IOException e) {
            throw new InitializationException(String.format("It is not possible to create a directory on the path '%s'", configuration.getLoggingDirectory().getLocation()), e);
        }
        try {
            configuration.getConfigurationDirectory().create();
        } catch (IOException e) {
            throw new InitializationException(String.format("It is not possible to create a directory on the path '%s'", configuration.getConfigurationDirectory().getLocation()), e);
        }
        try {
            configuration.getApplicationConfigurationFile().create();
        } catch (IOException e) {
            throw new InitializationException(String.format("It is not possible to create a file on the path '%s'", configuration.getApplicationConfigurationFile().getLocation()), e);
        }
    }
}
