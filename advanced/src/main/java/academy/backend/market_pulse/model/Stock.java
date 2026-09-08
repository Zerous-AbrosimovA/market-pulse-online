package academy.backend.market_pulse.model;

/**
 * Представляет акцию на фондовом рынке.
 * Класс описывает основные характеристики инструмента, в
 * ключая сектор экономики и доходность по дивидендам.
 */
public class Stock extends Instrument {

    private final String sector;

    // TODO: точно ли тут достаточно double?
    private final double dividendYield;

    public Stock(String ticker, String name, Currency currency,
                 String sector, double dividendYield) {
        super(ticker, name, currency);
        this.sector = sector;
        this.dividendYield = dividendYield;
    }

    public String getSector() {
        return sector;
    }

    public double getDividendYield() {
        return dividendYield;
    }
}
