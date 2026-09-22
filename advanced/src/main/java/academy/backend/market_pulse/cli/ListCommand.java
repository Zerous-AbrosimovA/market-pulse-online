package academy.backend.market_pulse.cli;

import academy.backend.market_pulse.repository.InstrumentRepository;
import lombok.RequiredArgsConstructor;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

@RequiredArgsConstructor
@Command(name = "list", description = "Список инструментов")
public class ListCommand implements Callable<Integer> {

    @Option(names = "--type", description = "Фильтр по типу инструмента")
    private String type;

    private final InstrumentRepository repository;

    /**
     * See {@link academy.backend.market_pulse.filter.InstrumentFilter}!
     */
    @Override
    public Integer call() {
        // TODO: реализовать отбор по функциональным требованиям из Javadoc InstrumentFilter
        //  (ФТ1-ФТ7): реализовать InstrumentFilter под каждое правило (тип, тикер, валюта, цена),
        //  добавить недостающие @Option (--ticker, --currency, --price-op, --price) и вывести
        //  описания подходящих инструментов (паттерн Strategy).
        throw new UnsupportedOperationException("call для ListCommand");
    }
}
