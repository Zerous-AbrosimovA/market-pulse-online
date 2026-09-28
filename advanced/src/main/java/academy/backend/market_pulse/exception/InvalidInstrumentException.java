package academy.backend.market_pulse.exception;

/**
 * Инструмент создан, но не проходит бизнес-валидацию {@code InstrumentValidator} — бросается
 * после создания сущности в фабрике, а не на входных параметрах команды.
 * TODO №1: требует реализации и применения!
 * TODO №2: добавить билдер для сбора ошибок валидации
 */
public class InvalidInstrumentException extends RuntimeException {
}
