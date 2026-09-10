package academy.backend.market_pulse.model;

import java.math.BigDecimal;

public class Quote {

    private final Stock stock;
    private final BigDecimal price;

    public Quote(Stock stock, BigDecimal price) {
        if (price.signum() <= 0) {
            throw new IllegalArgumentException("Цена должна быть положительной");
        }
        this.stock = stock;
        this.price = price;
    }

    public BigDecimal getDividends() {
        return stock.getDividends(price);
    }
}
