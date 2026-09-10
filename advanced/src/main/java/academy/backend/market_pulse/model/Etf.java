package academy.backend.market_pulse.model;

/**
 * Представляет биржевой инвестиционный фонд (ETF).
 * ETF - это тип инвестиционного фонда или биржевого товара, который отслеживает
 * индекс, сектор, товар или другие активы, но торгуется как одна акция на фондовой бирже.
 */
public class Etf extends Instrument {

    private final String trackingIndex;

    public Etf(String ticker, String name, Currency currency, String trackingIndex) {
        super(ticker, name, currency);
        this.trackingIndex = trackingIndex;
    }

    public String getTrackingIndex() {
        return trackingIndex;
    }

    @Override
    public String getDescription() {
        return "ETF, индекс: " + trackingIndex;
    }
}
