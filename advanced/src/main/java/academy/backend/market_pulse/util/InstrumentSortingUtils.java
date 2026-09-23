package academy.backend.market_pulse.util;

import academy.backend.market_pulse.dictionary.sort.SortField;
import academy.backend.market_pulse.dictionary.sort.SortOrder;
import academy.backend.market_pulse.model.Instrument;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Обобщённые утилиты для сортировки списков инструментов.
 */
@UtilityClass
public final class InstrumentSortingUtils {

    /**
     * Строит компаратор для сортировки вывода {@code list} по полю и порядку из CLI. Три вызова
     * {@code Comparator.comparing} ниже выводят три разных {@code U}: {@code String} для тикера,
     * {@code Currency} (enum — сам {@code Comparable}) для валюты, {@code BigDecimal} для цены
     * (дивидендной доходности акции).
     */
    public static Comparator<Instrument> comparator(SortField field, SortOrder order) {
        // TODO: реализовать сортировку согласно бизнес-логике
        throw new UnsupportedOperationException();
    }

    /**
     * Возвращает первые {@code n} элементов, отсортированных по тикеру.
     * Bounded type parameter {@code <T extends Instrument>}: внутри generic-кода доступен
     * метод {@link Instrument#getTicker()}, при этом метод остаётся применимым к любому
     * подтипу {@link Instrument}, а не только к самому базовому классу.
     */
    public static <T extends Instrument> List<T> topByTicker(List<T> items, int n) {
        List<T> sorted = new ArrayList<>(items);
        sorted.sort(Comparator.comparing(Instrument::getTicker));
        return sorted.subList(0, Math.min(n, sorted.size()));
    }

    /**
     * Сортирует список переданным компаратором. {@code Comparator<? super T>} — правило PECS,
     * случай «consumer»: компаратор для более общего типа (например, {@code Comparator<Instrument>})
     * годится и для списка подтипа (например, {@code List<Stock>}), потому что он лишь потребляет
     * элементы для сравнения, а не производит их.
     */
    public static <T> void sortWith(List<T> items, Comparator<? super T> comparator) {
        items.sort(comparator);
    }
}
