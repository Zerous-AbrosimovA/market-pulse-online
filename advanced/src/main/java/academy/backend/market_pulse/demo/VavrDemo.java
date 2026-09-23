package academy.backend.market_pulse.demo;

import academy.backend.market_pulse.dictionary.InstrumentType;
import academy.backend.market_pulse.factory.InstrumentFactories;
import academy.backend.market_pulse.model.Currency;
import academy.backend.market_pulse.model.Instrument;
import io.vavr.control.Either;
import io.vavr.control.Try;

/**
 * Vavr {@code Try} и {@code Either} — материал для самостоятельного знакомства, не разбирается
 * вживую. Не внедряется в проект — только демонстрация двух типов: альтернативы {@code try/catch}
 * и {@code null} для обработки ошибок.
 */
public class VavrDemo {

    public static void main(String[] args) {
        Try<Currency> currency = Try.of(() -> Currency.valueOf("RUB"));
        currency.onSuccess(value -> System.out.println("Валюта распознана: " + value))
                .onFailure(error -> System.out.println("Некорректная валюта: " + error.getMessage()));

        parseInstrument(InstrumentType.STOCK, "SBER", "Сбербанк", "RUB")
                .peek(instrument -> System.out.println("Создан: " + instrument.getDescription()))
                .peekLeft(error -> System.out.println("Ошибка: " + error));

        parseInstrument(InstrumentType.STOCK, "SBER", "Сбербанк", "XYZ")
                .peek(instrument -> System.out.println("Создан: " + instrument.getDescription()))
                .peekLeft(error -> System.out.println("Ошибка: " + error));
    }

    private static Either<String, Instrument> parseInstrument(
            InstrumentType type, String ticker, String name, String currencyStr) {
        try {
            Currency currency = Currency.valueOf(currencyStr.toUpperCase());
            return Either.right(InstrumentFactories.create(type, ticker, name, currency));
        } catch (IllegalArgumentException e) {
            return Either.left("Некорректный тип или валюта: " + e.getMessage());
        }
    }
}
