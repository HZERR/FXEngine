package ru.hzerr.fx.engine.core.path;

public enum NullSafeResolveLocationOptions {
    /**
     * Установить сепаратор в начале, если другой location равен null
     */
    INSERT_START,
    /**
     * Установить сепаратор в конце, если другой location равен null
     */
    INSERT_END,
    /**
     * Установить сепаратор в начале и в конце, если другой location равен null
     */
    INSERT_EVERYWHERE,
    /**
     * Удалить сепаратор в начале, если другой location равен null
     */
    REMOVE_START,
    /**
     * Удалить сепаратор в конце, если другой location равен null
     */
    REMOVE_END,
    /**
     * Удалить сепаратор в начале и в конце, если другой location равен null
     */
    REMOVE_EVERYWHERE,
    /**
     * Бросить исключение, если другой location равен null
     */
    THROW_ILLEGAL_ARGUMENT_EXCEPTION,
    /**
     * Ничего не делать, если другой location равен null
     */
    DEFAULT
}
