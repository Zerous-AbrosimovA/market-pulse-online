package academy.backend.market_pulse.model;

/**
 * Наивная попытка представить акцию с ценой на конкретный момент времени —
 * через наследование от {@link Stock}. Не взлетает: {@code getDividends(BigDecimal)}
 * из родителя никуда не делся, а перегрузка без аргумента — не переопределение,
 * и непонятно, какой из двух методов вызывать.
 *
 * @deprecated заменяется агрегацией — {@link Quote}. Удалить после рефакторинга.
 */
@Deprecated
public class StockSnapshot extends Stock {

    private final double price;

    public StockSnapshot(String ticker, String name, Currency currency,
                         String sector, double dividendYield, double price) {
        super(ticker, name, currency, sector, dividendYield);
        this.price = price;
    }

    // TODO: добавить метод для расчета дивидендов для текущей цены
}
