package academy.backend.market_pulse.repository;

import academy.backend.market_pulse.model.Instrument;

/**
 * Хранилище инструментов. Выделено в интерфейс (в отличие от базового трека) — нужен для
 * JDK Dynamic Proxy и CGLIB, которым для работы требуется тип, отдельный от конкретной
 * реализации.
 */
public interface InstrumentRepository extends Iterable<Instrument> {

    void add(Instrument instrument);

    // NOTICE: что возвращает этот метод, если инструмент с таким тикером не найден? Сейчас
    // ответ не виден в сигнатуре — только в реализации и в документации, если её кто-то прочитал.
    Instrument findByTicker(String ticker);
}
