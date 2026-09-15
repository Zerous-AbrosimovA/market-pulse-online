package academy.backend.market_pulse.repository;

import academy.backend.market_pulse.model.Instrument;

public interface InstrumentRepository extends Iterable<Instrument> {

    void add(Instrument instrument);

    Instrument findByTicker(String ticker);
}
