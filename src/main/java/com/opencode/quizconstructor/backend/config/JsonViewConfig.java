package com.opencode.quizconstructor.backend.config;

/**
 * Набор интерфейсов-маркеров для настройки выдачи JSON.
 */
public final class JsonViewConfig {
    /**
     * Служит для маркировки полей вывода JSON "Только ID".
     */
    public interface OnlyId { }
    /**
     * Служит для маркировки полей вывода JSON "Только ID и наименовани".
     */
    public interface IdName extends OnlyId { }
    /**
     * Служит для маркировки полей вывода JSON "Весь объект".
     */
    public interface FullObject extends IdName { }

    /**
     * Служит для маркировки полей вывода JSON "Настраиваемый вывод".
     */
    public interface CustomView { }
}
