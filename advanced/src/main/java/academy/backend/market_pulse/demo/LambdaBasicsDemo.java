package academy.backend.market_pulse.demo;

import academy.backend.market_pulse.model.Currency;
import academy.backend.market_pulse.model.Instrument;
import academy.backend.market_pulse.model.Stock;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Три способа применить лямбду — до перехода к рефакторингу {@code ListCommand}: лямбда как
 * параметр метода, лямбда как результат метода и композиция функций. Пример нарочно не трогает
 * реальный CLI — только домен проекта.
 */
public class LambdaBasicsDemo {

    public static void main(String[] args) {
        List<Instrument> instruments = List.of(
                new Stock("SBER", "Сбер", Currency.RUB, "Финансы", new BigDecimal("6.5")),
                new Stock("AAPL", "Apple", Currency.USD, "Технологии", new BigDecimal("0.5")),
                new Stock("YNDX", "Яндекс", Currency.RUB, "Технологии", BigDecimal.ZERO)
        );

        // 1. Лямбда как параметр метода: printAll ничего не знает о том, как форматировать
        // инструмент — это решает вызывающий код, передавая конкретную реализацию Function.
        printAll(instruments, instrument -> instrument.getTicker() + ": " + instrument.getDescription());

        // 2. Метод, возвращающий лямбду: byCurrency создаёт готовый Predicate под конкретную
        // валюту, ничего не проверяя сам по себе — просто конструирует функцию.
        Predicate<Instrument> isRub = byCurrency(Currency.RUB);
        System.out.println("SBER в рублях? " + isRub.test(instruments.get(0)));

        // 3. Композиция: Predicate.and() строит новый Predicate из двух существующих, не меняя
        // ни один из них — исходный isRub можно использовать и дальше как есть.
        Predicate<Instrument> isRubStock = isRub.and(instrument -> instrument instanceof Stock);
        instruments.stream()
                .filter(isRubStock)
                .forEach(instrument -> System.out.println("RUB-акция: " + instrument.getTicker()));
    }

    private static void printAll(List<Instrument> instruments, Function<Instrument, String> formatter) {
        instruments.forEach(instrument -> System.out.println(formatter.apply(instrument)));
    }

    private static Predicate<Instrument> byCurrency(Currency currency) {
        return instrument -> instrument.getCurrency() == currency;
    }
}
