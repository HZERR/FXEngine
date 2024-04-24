package ru.hzerr.fx.engine.configuration.application;

import ru.hzerr.file.BaseDirectory;
import ru.hzerr.file.BaseFile;
import ru.hzerr.fx.engine.core.InitializationException;
import ru.hzerr.fx.engine.core.interfaces.engine.IStructureConfiguration;

import java.io.IOException;

public class StructureInitializer implements Initializer {

    private IStructureConfiguration configuration;

    public StructureInitializer(IStructureConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void initialize() throws InitializationException {
        initializeDirectory(configuration.getProgramDirectory(), "program");
        initializeDirectory(configuration.getLogDirectory(), "log");
        initializeDirectory(configuration.getConfigDirectory(), "config");
        initializeFile(configuration.getSoftwareConfigurationFile(), "software configuration");
    }

    private void initializeDirectory(BaseDirectory directory, String purposeDirectory) throws InitializationException {
        try {
            directory.create();
        } catch (IOException | NullPointerException e) {
            throw new InitializationException(String.format("It is not possible to create a %s directory on the path '%s'", purposeDirectory, directory != null ? directory.getLocation() : null), e);
        }
    }

    private void initializeFile(BaseFile file, String purposeFile) throws InitializationException {
        try {
            file.create();
        } catch (IOException | NullPointerException e) {
            throw new InitializationException(String.format("It is not possible to create a %s file on the path '%s'",  purposeFile, file != null ? file.getLocation() : null), e);
        }
    }
}
