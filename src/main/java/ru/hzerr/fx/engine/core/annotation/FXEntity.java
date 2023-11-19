package ru.hzerr.fx.engine.core.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Аннотация должна использоваться на каждом контроллере. Она дает системе понять, где искать конкретные css, fxml и bundle файлы
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface FXEntity {

    /**
     * @return путь до fxml файла, за контролем которого отвечает данный контроллер
     */
    String fxml();

    /**
     * У каждого fxml свой отдельный .conf файл. Возвращает путь до данного файла
     * @return путь до .conf файла или языкового пакета, который должен быть загружен НА КОНКРЕТНОМ fxml
     */
    String internationalization();

    /**
     * Путь, до css файла относительно папки с темой
     * @return относительный путь до css файла или файла темы, которая должна быть загружена на данном fxml
     */
    String theme();
}
