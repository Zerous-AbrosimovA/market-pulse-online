package academy.backend.market_pulse.demo;

import academy.backend.market_pulse.dictionary.InstrumentType;
import academy.backend.market_pulse.model.Currency;
import academy.backend.market_pulse.model.Quote;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Gatherer;
import java.util.stream.Gatherers;

/**
 * @author Abrosimov Artem
 */
public class StreamExercisesImpl implements StreamExercises {

    @Override
    public List<String> tickersInCurrency(List<Quote> quotes, Currency currency) {
        return quotes.stream()
                .filter(quote -> quote.getInstrument().getCurrency().equals(currency))
                .map(quote -> quote.getInstrument().getTicker())
                .toList();
    }

    @Override
    public List<Quote> topByPrice(List<Quote> quotes, int n) {
        return quotes.stream()
                .sorted(Comparator.comparing(Quote::getPrice).reversed())
                .limit(n)
                .toList();
    }

    @Override
    public Set<Currency> distinctCurrencies(List<Quote> quotes) {
        return quotes.stream()
                .map(quote ->
                        quote.getInstrument().getCurrency())
                .collect(Collectors.toSet());
    }

    @Override
    public boolean hasAnyLoser(List<Quote> quotes) {
        return quotes.stream()
                .anyMatch(quote -> quote.getChangePercent().compareTo(BigDecimal.ZERO) < 0);
    }

    @Override
    public long countByType(List<Quote> quotes, InstrumentType instrumentType) {
        return quotes.stream()
                .filter(quote -> quote.getInstrument().getType().equals(instrumentType))
                .count();
    }

    @Override
    public BigDecimal totalPrice(List<Quote> quotes) {
        return quotes.stream().reduce(
                BigDecimal.ZERO,
                (acc, currentQuotes) -> acc.add(currentQuotes.getPrice()),
                BigDecimal::add
        );
    }

    @Override
    public double averageChangePercent(List<Quote> quotes) {
        return quotes.stream()
                .mapToDouble(quote -> quote.getChangePercent().doubleValue())
                .average()
                .orElse(0.0);
    }

    @Override
    public Map<InstrumentType, Long> countByTypeGrouped(List<Quote> quotes) {
        return quotes.stream()
                .collect(Collectors.groupingBy(quote -> quote.getInstrument().getType(),
                        Collectors.counting())
                );
    }

    @Override
    public Map<InstrumentType, Double> averageChangeByType(List<Quote> quotes) {
        return quotes.stream()
                .collect(Collectors.groupingBy(quote -> quote.getInstrument().getType(),
                        Collectors.averagingDouble(quote -> quote.getChangePercent().doubleValue()))
                );
    }

    @Override
    public Map<Boolean, List<Quote>> partitionByGain(List<Quote> quotes) {
        return quotes.stream()
                .collect(Collectors.partitioningBy(
                        quote -> quote.getChangePercent().compareTo(BigDecimal.ZERO) > 0)
                );
    }

    @Override
    public Map<String, Quote> latestByTicker(List<Quote> quotes) {
        return quotes.stream()
                .collect(Collectors.toMap(
                        quote -> quote.getInstrument().getTicker(),
                        quote -> quote,
                        (quotes1, quotes2) ->
                                quotes1.getPrice().compareTo(quotes2.getPrice()) > 0 ?  quotes1 : quotes2)
                );
    }

    @Override
    public String joinTickers(List<Quote> quotes) {
        return quotes.stream()
                .map(quote -> quote.getInstrument().getTicker())
                .collect(Collectors.joining(",", "[", "]"));
    }

    @Override
    public Map<Currency, String> tickersJoinedByCurrency(List<Quote> quotes) {
        return quotes.stream()
                .collect(
                        Collectors.groupingBy(quote -> quote.getInstrument().getCurrency(),
                                Collectors.mapping(quote -> quote.getInstrument().getTicker(),
                                        Collectors.joining(",")
                                )
                        )
                );
    }

    @Override
    public List<Quote> findByTickers(List<Quote> quotes, List<String> tickers) {
        return tickers.stream()
                .map(ticker -> quotes.stream()
                        .filter(quote -> quote.getInstrument().getTicker().equals(ticker))
                        .findFirst())
                .flatMap(Optional::stream)
                .toList();
    }

    @Override
    public List<BigDecimal> movingAveragePrice(List<Quote> quotes, int windowSize) {
        return quotes.stream().map(Quote::getPrice)
                .gather(Gatherers.windowSliding(windowSize))
                .map(quotes1 ->
                        (quotes1.stream()
                                .reduce(BigDecimal.ZERO, BigDecimal::add))
                                .divide(BigDecimal.valueOf(quotes1.size()), 2, RoundingMode.HALF_UP))
                .toList();
    }

    @Override
    public int longestLossStreak(List<Quote> quotes) {
        class State {
            int currentStreak = 0;
            int longestStreak = 0;
        }
        return quotes.stream().gather(Gatherer.ofSequential(
                State::new,
                Gatherer.Integrator.ofGreedy(
                        (state, element, downstream) -> {
                            if (element.getChangePercent().compareTo(BigDecimal.ZERO) < 0) {
                                state.currentStreak++;
                                state.longestStreak = Math.max(state.currentStreak, state.longestStreak);
                            } else {
                                state.currentStreak = 0;
                            }
                            return !downstream.isRejecting();
                        }
                ),
                (State state, Gatherer.Downstream<? super Integer> downstream) -> downstream.push(state.longestStreak)
        )).findFirst().orElse(0);
    }
}
