package academy.backend.market_pulse.model;

/**
 * Класс, представляющий облигацию.
 * Облигация является финансовым инструментом, который характеризуется ставкой купона и годом погашения.
 */
public class Bond extends Instrument {

    // TODO: точно ли тут достаточно double?
    private final double couponRate;
    private final int maturityYear;

    public Bond(String ticker, String name, Currency currency,
                double couponRate, int maturityYear) {
        super(ticker, name, currency);
        this.couponRate = couponRate;
        this.maturityYear = maturityYear;
    }

    public double getCouponRate() {
        return couponRate;
    }

    public int getMaturityYear() {
        return maturityYear;
    }
}
