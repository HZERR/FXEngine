package ru.hzerr.fx.engine.core;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.hzerr.fx.engine.configuration.application.IReadOnlyApplicationConfiguration;
import ru.hzerr.fx.engine.configuration.application.IReadOnlyClassLoaderProvider;
import ru.hzerr.fx.engine.configuration.application.IResourceStructureConfiguration;
import ru.hzerr.fx.engine.configuration.application.IStructureConfiguration;
import ru.hzerr.fx.engine.configuration.logging.IReadOnlyLoggingConfiguration;
import ru.hzerr.fx.engine.core.context.AutomaticExtendedAnnotationConfigApplicationContextProvider;
import ru.hzerr.fx.engine.core.context.ExtendedAnnotationConfigApplicationContextProvider;
import ru.hzerr.fx.engine.core.context.IExtendedAnnotationConfigApplicationContext;
import ru.hzerr.fx.engine.core.entity.IControllerManagedRepository;
import ru.hzerr.fx.engine.core.language.EntityLocalizationMetaData;
import ru.hzerr.fx.engine.core.language.LoggingLocalizationMetaData;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Этот класс обеспечивает основную точку входа для приложения JavaFX.
 * Он инициализирует среду JavaFX, устанавливает контекст приложения и вызывает методы {@link #onInit()} и {@link #onStart(Stage)}.
 * Он также добавляет hook выключения, чтобы обеспечить правильное закрытие приложения при выходе из JVM.
 * Необходимо заимплементить интерфейсы и пометить наследующие их классы аннотацией @Registered:<br>
 * 1) {@link IStructureConfiguration}<br>
 * 2) {@link IResourceStructureConfiguration}<br>
 * 3) {@link IReadOnlyApplicationConfiguration}<br>
 * 4) {@link IReadOnlyLoggingConfiguration}<br>
 * Опционально:<br>
 * 5) {@link IReadOnlyClassLoaderProvider}<br>
 * * Необходимо создать структуры папок ресурсов:<br>
 * 1) FXML: fxml/main.fxml, где папка "fxml" - корневая папка всех fxml<br>
 * 2) Application localization: language/ru-RU/main.json, language/en-EN/main.json, где папка "language" - корневая папка всех локализаций, а папки "ru-RU" и "en-EN" - папки конкретной локализации<br>
 * 3) Theme: theme/dark/main.css, theme/white/main.css, где папка "theme" - корневая папка всех тем, а папки "white" и "dark" - папки конкретной темы<br>
 * Опционально:<br>
 * 4) Application logging localization: language/logging/ru-RU/main.json, language/logging/en-EN/main.json, где папка "language/logging" - корневая папка всех локализаций отладки, а папки "ru-RU" и "en-EN" - папки конкретной локализации отладки<br>
 * Создать классы имплементирующие интерфейс {@link EntityLocalizationMetaData} в том кол-ве, сколько и локализаций. Не забудьте добавить их в контекст, повесив аннотацию @Registered. Таким образом вы дадите FXEngine связь Locale -> Location<br>
 * Опционально также и для {@link LoggingLocalizationMetaData}
 * @author HZERR
 */

// TODO: Разделить 1 контекст на несколько
public abstract class FXEngine extends Application {

    /**
     * Контекст приложения.
     */
    protected static IExtendedAnnotationConfigApplicationContext context;

    /**
     * Флаг, указывающий, было ли закрыто приложение.
     */
    private final AtomicBoolean closed = new AtomicBoolean(false);

    @Override
    public final void init() throws Exception {
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
        context = createApplicationContext();
        onInit();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                stop();
            } catch (IOException e) {
                throw new IllegalClosedException(context.isActive() ?
                        context.getEngineLoggingLocalizationProvider().getLocalization().getConfiguration().getString("fxEngine.init.closedException") :
                        "An error occurred stopping the application", e);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IllegalClosedException(context.isActive() ?
                        context.getEngineLoggingLocalizationProvider().getLocalization().getConfiguration().getString("fxEngine.init.closedException") :
                        "An error occurred stopping the application", e);
            }
        }));
        context.getFXEngineLogProvider().getLogger().info("fxEngine.init.engineSuccessfullyInitialized");
    }

    @Override
    public final void start(Stage stage) throws Exception {
        Scene scene = onStart(stage);
        stage.setScene(scene);
        context.setStage(stage);
        context.getStage().show();
        context.getFXEngineLogProvider().getLogger().info("fxEngine.start.engineSuccessfullyStarted");
    }

    @Override
    public final void stop() throws IOException, InterruptedException {
        if (closed.compareAndSet(false, true)) {
            context.getBean(IControllerManagedRepository.class).destroyAll();

            onClose();
            context.close();
        }
    }

    /**
     * Создает контекст приложения.
     * Этот метод нужно переопределить, чтобы предоставить пользовательский контекст приложения.
     * Для реализации метода используйте вспомогательные методы и дергайте getContext():<br>
     * 1) {@linkplain #createAutomaticExtendedAnnotationConfigApplicationContextProvider(Class)}<br>
     * 2) {@linkplain #createExtendedAnnotationConfigApplicationContextProvider(String...)}<br>
     * @return настроенный контекст приложения
     * @throws Exception если возникает ошибка при создании контекста
     */
    protected abstract IExtendedAnnotationConfigApplicationContext createApplicationContext();

    /**
     * Этот метод вызывается во время инициализации.
     * Его можно переопределить для выполнения пользовательских задач инициализации.
     * Для более ранней инициализации рассматривайте класс {@link ru.hzerr.fx.engine.core.context.registrar.ExtendedAnnotationConfigApplicationContextSequentialRegistrar}
     * @throws Exception если во время инициализации возникает ошибка
     */
    protected void onInit() throws Exception {
    }

    /**
     * Этот метод вызывается для запуска приложения.
     * Его нужно переопределить, чтобы обеспечить собственную логику запуска.
     * Возвращаемую сцену не обязательно устанавливать на текущий stage приложения, Engine сделает это за вас
     * @param stage базовый stage приложения
     * @return сцена приложения
     * @throws Exception если возникает ошибка при запуске
     */
    protected abstract Scene onStart(Stage stage) throws Exception;

    /**
     * Этот метод вызывается во время завершения работы приложения.
     * Его можно переопределить для выполнения пользовательских задач очистки.
     * @throws IOException если возникает ошибка во время завершения работы
     */
    protected void onClose() throws IOException {
    }

    /**
     * Создает {@link IApplicationContextProvider} для создания ApplicationContext.
     * @param basePackages базовые пакеты для сканирования компонентов
     * @return поставщик контекста приложения
     */
    protected final IApplicationContextProvider<IExtendedAnnotationConfigApplicationContext> createExtendedAnnotationConfigApplicationContextProvider(String... basePackages) {
        return new ExtendedAnnotationConfigApplicationContextProvider(basePackages, true);
    }

    /**
     * Создает {@link IApplicationContextProvider} для создания ApplicationContext, путем получения корневой папки приложения из переданного класса запускаемого приложения.
     * Имейте в виду, если корневая папка - это "ru" или "com" и тп, движок просканирует все классы в точности и в библиотеках.
     * <p>
     * Используйте данный provider осторожно
     * @param startupApplicationClass класс запускаемого приложения
     * @return поставщик контекста приложения
     * @see #createExtendedAnnotationConfigApplicationContextProvider(String...)
     */
    protected final IApplicationContextProvider<IExtendedAnnotationConfigApplicationContext> createAutomaticExtendedAnnotationConfigApplicationContextProvider(Class<? extends FXEngine> startupApplicationClass) {
        return new AutomaticExtendedAnnotationConfigApplicationContextProvider(startupApplicationClass);
    }

    /**
     * Возвращает контекст приложения.
     * @return контекст приложения
     */
    public static IExtendedAnnotationConfigApplicationContext getContext() {
        return context;
    }

    /**
     * Возвращает контекст приложения указанного типа.
     * @param contextClass тип контекста
     * @return контекст приложения
     */
    public static <T extends IExtendedAnnotationConfigApplicationContext> T getContextAs(Class<T> contextClass) {
        return (T) context;
    }
}
