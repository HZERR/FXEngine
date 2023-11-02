package ru.hzerr.fx.engine.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import ru.hzerr.fx.engine.annotation.RegisteredAs;
import ru.hzerr.fx.engine.configuration.interfaces.IStructureConfiguration;
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
            configuration.getProgramDirectory().create();
        } catch (IOException e) {
            throw new InitializationException(String.format("It is not possible to create a directory on the path '%s'", configuration.getProgramDirectory().getLocation()), e);
        }
        try {
            configuration.getLogDirectory().create();
        } catch (IOException e) {
            throw new InitializationException(String.format("It is not possible to create a directory on the path '%s'", configuration.getLogDirectory().getLocation()), e);
        }
        try {
            configuration.getConfigDirectory().create();
        } catch (IOException e) {
            throw new InitializationException(String.format("It is not possible to create a directory on the path '%s'", configuration.getConfigDirectory().getLocation()), e);
        }
        try {
            configuration.getSoftwareConfigurationFile().create();
        } catch (IOException e) {
            throw new InitializationException(String.format("It is not possible to create a file on the path '%s'", configuration.getSoftwareConfigurationFile().getLocation()), e);
        }
    }
}
