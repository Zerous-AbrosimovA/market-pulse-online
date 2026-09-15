package academy.backend.market_pulse.proxy;

import academy.backend.market_pulse.model.Currency;
import academy.backend.market_pulse.model.Stock;
import academy.backend.market_pulse.repository.InstrumentRepository;

import java.math.BigDecimal;

// TODO: показать и обсудить типы проксирования в Java
public class ProxyDemo {

    public static void main(String[] args) {
        InstrumentRepository proxy = ProxyFactory.timingRepository(ProxyFactory.Kind.STATIC);

        proxy.add(new Stock("SBER", "Сбербанк", Currency.RUB, "Financials", new BigDecimal("6.5")));
        proxy.findByTicker("SBER");
    }
}
