package academy.backend.market_pulse.demo;

import academy.backend.market_pulse.model.Currency;
import academy.backend.market_pulse.model.Instrument;
import academy.backend.market_pulse.model.Stock;
import academy.backend.market_pulse.util.InstrumentSortingUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Generics, bounded types и wildcard. Демонстрирует {@code ? super T}: компаратор для
 * {@link Instrument} принимается там, где ожидается компаратор для {@link Stock} —
 * {@code List<Stock>} не является подтипом {@code List<Instrument>}, но
 * {@code Comparator<? super Stock>} это позволяет.
 *
 * <p>{@code Comparator.comparing} — ещё один пример generics, но уже из стандартной библиотеки:
 * {@code <T, U extends Comparable<? super U>> Comparator<T> comparing(Function<? super T, ? extends U> keyExtractor)}
 * сам выводит {@code U} по типу, который возвращает key extractor, и требует от этого типа быть
 * {@code Comparable} — компилятор не даст передать метод, возвращающий несравнимый тип.
 */
public class GenericsDemo {

    public static void main(String[] args) {
        List<Stock> stocks = new ArrayList<>(List.of(
                new Stock("YNDX", "Яндекс", Currency.RUB, "Технологии", BigDecimal.ZERO),
                new Stock("SBER", "Сбер", Currency.RUB, "Финансы", new BigDecimal("6.5")),
                new Stock("GAZP", "Газпром", Currency.RUB, "Энергетика", new BigDecimal("8.0"))
        ));

        // sortWith(List<T>, Comparator<? super T>) принимает Comparator<Instrument> для List<Stock> —
        // с сигнатурой sortWith(List<T>, Comparator<T>) этот вызов не скомпилировался бы.
        // U выводится как String — у String есть Comparable<String>.
        InstrumentSortingUtils.sortWith(stocks, Comparator.comparing(Instrument::getTicker));
        System.out.println("По тикеру (U = String): " + tickers(stocks));

        // Тот же comparing(), но U выводится уже как BigDecimal — ничего в сигнатуре sortWith
        // не фиксирует U заранее, каждый вызов подставляет свой тип по key extractor'у.
        InstrumentSortingUtils.sortWith(stocks, Comparator.comparing(Stock::getDividendYield));
        System.out.println("По дивидендной доходности (U = BigDecimal): " + tickers(stocks));

        List<Stock> top = InstrumentSortingUtils.topByTicker(stocks, 2);
        System.out.println("topByTicker(2): " + tickers(top));
    }

    private static List<String> tickers(List<Stock> stocks) {
        return stocks.stream().map(Stock::getTicker).toList();
    }
}
