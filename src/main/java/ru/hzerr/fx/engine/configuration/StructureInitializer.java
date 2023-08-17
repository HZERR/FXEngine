package ru.hzerr.fx.engine.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import ru.hzerr.fx.engine.annotation.FXInitializer;
import ru.hzerr.fx.engine.core.Initializer;
import ru.hzerr.fx.engine.core.InitializationException;

import java.io.IOException;


@FXInitializer(value = "structure.initializer")
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
            throw new InitializationException(String.format("Невозможно создать директорию по пути '%s'", configuration.getApplicationDirectory().getLocation()), e);
        }
        try {
            configuration.getLoggingDirectory().create();
        } catch (IOException e) {
            throw new InitializationException(String.format("Невозможно создать директорию по пути '%s'", configuration.getLoggingDirectory().getLocation()), e);
        }
        try {
            configuration.getConfigurationDirectory().create();
        } catch (IOException e) {
            throw new InitializationException(String.format("Невозможно создать директорию по пути '%s'", configuration.getConfigurationDirectory().getLocation()), e);
        }
        try {
            configuration.getApplicationConfigurationFile().create();
        } catch (IOException e) {
            throw new InitializationException(String.format("Невозможно создать Файл по пути '%s'", configuration.getApplicationConfigurationFile().getLocation()), e);
        }
    }
}
