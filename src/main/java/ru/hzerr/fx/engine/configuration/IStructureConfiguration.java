package ru.hzerr.fx.engine.configuration;

import org.springframework.stereotype.Component;
import ru.hzerr.file.BaseDirectory;
import ru.hzerr.file.BaseFile;
import ru.hzerr.file.HDirectory;
import ru.hzerr.file.HFile;

@Component
public interface IStructureConfiguration {
    BaseDirectory getApplicationDirectory();

    BaseDirectory getLoggingDirectory();

    BaseDirectory getConfigurationDirectory();
    BaseFile getApplicationConfigurationFile();
}
