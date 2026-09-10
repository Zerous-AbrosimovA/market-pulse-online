package academy.backend.market_pulse;

import academy.backend.market_pulse.model.Bond;
import academy.backend.market_pulse.model.Currency;
import academy.backend.market_pulse.model.Etf;
import academy.backend.market_pulse.model.Quote;
import academy.backend.market_pulse.model.Stock;
import academy.backend.market_pulse.model.StockSnapshot;

import java.math.BigDecimal;

// https://developer.tbank.ru/invest/api
public class Main {

    public static void main(String[] args) {
        Stock tbank = new Stock("SBER", "Сбербанк", Currency.RUB, "Financials", 6.5);
        Bond ofz = new Bond("SU26238RMFS4", "ОФЗ-26238", Currency.RUB, 7.1, 2035);
        Etf tmos = new Etf("TMOS", "Тинькофф iMOEX", Currency.RUB, "MOEX");

        // TODO №1: вывести описание каждого инструмента

        // TODO №2: достаточно ли double для хранения дивидендов?
        var sberStockPrice = new BigDecimal("13.65");
        var sberDividends = new BigDecimal("6.5");
        var dividends = sberStockPrice.multiply(sberDividends)
                .setScale(10, BigDecimal.ROUND_HALF_UP)
                .divide(new BigDecimal("100"));
        var dividends1 = new BigDecimal("0.887251");
        System.out.println("Дивиденды SBER: " + dividends); // NOTICE: ожидается 0.88725
        System.out.println(dividends1.compareTo(dividends));

        var snapshot = new StockSnapshot("SBER", "Сбербанк", Currency.RUB, "Financials", 6.5, 13.65);
        snapshot.getDividends(BigDecimal.TWO);

        var quote = new Quote(tbank, sberStockPrice);
        quote.getDividends();

        // TODO №3: обсудить сравнение числовых типов

        // TODO №4: посчитать дивиденды для каждого инструмента?

        // TODO №5: построить Quote для tbank и ofz, вывести котировки и дивиденды по ним.
    }
}
