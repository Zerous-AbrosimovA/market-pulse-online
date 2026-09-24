package academy.backend.market_pulse.repository;

import academy.backend.market_pulse.model.Instrument;

import java.util.Optional;

public interface InstrumentRepository extends Iterable<Instrument> {

    void add(Instrument instrument);

    Optional<Instrument> findByTicker(String ticker);
}
