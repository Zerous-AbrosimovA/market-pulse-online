package academy.backend.market_pulse.cli;

import java.math.BigDecimal;
import java.util.concurrent.Callable;

import academy.backend.market_pulse.dictionary.InstrumentType;
import academy.backend.market_pulse.dictionary.sort.SortField;
import academy.backend.market_pulse.dictionary.sort.SortOrder;
import academy.backend.market_pulse.filter.FilterFactory;
import academy.backend.market_pulse.filter.InstrumentFilter;
import academy.backend.market_pulse.filter.PriceFilter;
import academy.backend.market_pulse.model.Currency;
import academy.backend.market_pulse.model.Instrument;
import academy.backend.market_pulse.repository.InstrumentRepository;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

// TODO №1: отрефакторить InstrumentFilter
@Command(name = "list", description = "Список инструментов")
public class ListCommand implements Callable<Integer> {

    @Option(names = "--type", description = "Фильтр по типу инструмента")
    private InstrumentType type;

    @Option(names = "--ticker", description = "Фильтр по подстроке в тикере")
    private String ticker;

    @Option(names = "--currency", description = "Фильтр по валюте инструмента")
    private Currency currency;

    @Option(names = "--price-op", description = "Оператор сравнения цены: GE, LE или EQ")
    private PriceFilter.Operator priceOperator;

    @Option(names = "--price", description = "Пороговое значение цены (дивидендная доходность акции)")
    private BigDecimal price;

    // TODO №2: сортировка по --sort-by/--order пока ни на что не влияет — реализовать через
    @Option(names = "--sort-by", description = "Поле сортировки вывода: TICKER, CURRENCY или PRICE")
    private SortField sortBy;

    @Option(names = "--order", description = "Порядок сортировки: ASC (по умолчанию) или DESC")
    private SortOrder order = SortOrder.ASC;

    private final InstrumentRepository repository;

    public ListCommand(InstrumentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Integer call() {
        InstrumentFilter filter = FilterFactory.create(type, ticker, currency, priceOperator, price);
        for (Instrument instrument : repository) {
            if (filter.matches(instrument)) {
                System.out.println(instrument.getDescription());
            }
        }
        return 0;
    }
}
