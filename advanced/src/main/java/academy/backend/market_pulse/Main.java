package academy.backend.market_pulse;

import academy.backend.market_pulse.model.Bond;
import academy.backend.market_pulse.model.Currency;
import academy.backend.market_pulse.model.Etf;
import academy.backend.market_pulse.model.Stock;

public class Main {

    public static void main(String[] args) {
        Stock sber = new Stock("SBER", "Сбербанк", Currency.RUB, "Financials", 6.5);
        Bond ofz = new Bond("SU26238RMFS4", "ОФЗ-26238", Currency.RUB, 7.1, 2035);
        Etf tmos = new Etf("TMOS", "Тинькофф iMOEX", Currency.RUB, "MOEX");

        // TODO №1: вывести описание каждого инструмента

        // TODO №2: достаточно ли double для хранения дивидендов?
        var sberStockPrice = 13.65;
        var dividends = sberStockPrice * sber.getDividendYield() / 100;
        System.out.println("Дивиденды SBER: " + dividends); // NOTICE: ожидается 88.725

        // TODO №3: обсудить сравнение числовых типов

        // TODO №4: посчитать дивиденды для каждого инструмента?

        // TODO №5: построить Quote для sber и ofz, вывести котировки и дивиденды по ним.
    }
}
