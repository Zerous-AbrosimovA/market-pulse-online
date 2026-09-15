package academy.backend.market_pulse.proxy;

import academy.backend.market_pulse.model.Instrument;
import academy.backend.market_pulse.repository.InMemoryInstrumentRepository;
import academy.backend.market_pulse.repository.InstrumentRepository;
import lombok.experimental.UtilityClass;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Iterator;

/**
 * Скрывает от клиента, как именно устроена прокси-обёртка над {@link InstrumentRepository},
 * которая замеряет время каждого вызова и логирует его. Клиент указывает только {@link Kind} —
 * какой из трёх механизмов использовать; про {@code InvocationHandler}, {@code Enhancer} и
 * ручное оборачивание каждого метода знает фабрика, а не он.
 */
@UtilityClass
public final class ProxyFactory {

    public enum Kind {
        STATIC, JDK_DYNAMIC, CGLIB
    }

    public static InstrumentRepository timingRepository(Kind kind) {
        return switch (kind) {
            case STATIC -> new TimingInstrumentRepository(new InMemoryInstrumentRepository());
            case JDK_DYNAMIC -> jdkDynamicProxy(new InMemoryInstrumentRepository());
            case CGLIB -> cglibProxy();
        };
    }

    private static InstrumentRepository jdkDynamicProxy(InstrumentRepository target) {
        return (InstrumentRepository) Proxy.newProxyInstance(
                InstrumentRepository.class.getClassLoader(),
                new Class<?>[]{InstrumentRepository.class},
                (Object proxy, Method method, Object[] args) -> {
                    long start = System.nanoTime();
                    Object result = method.invoke(target, args);
                    System.out.printf("%s() выполнен за %d нс%n", method.getName(), System.nanoTime() - start);
                    return result;
                });
    }

    private static InstrumentRepository cglibProxy() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(InMemoryInstrumentRepository.class);
        enhancer.setCallback((MethodInterceptor) (obj, method, args, methodProxy) -> {
            long start = System.nanoTime();
            Object result = methodProxy.invokeSuper(obj, args);
            System.out.printf("%s() выполнен за %d нс%n", method.getName(), System.nanoTime() - start);
            return result;
        });
        return (InstrumentRepository) enhancer.create();
    }

    /**
     * Самописный (статический) прокси.
     */
    private static final class TimingInstrumentRepository implements InstrumentRepository {

        private final InstrumentRepository target;

        private TimingInstrumentRepository(InstrumentRepository target) {
            this.target = target;
        }

        @Override
        public void add(Instrument instrument) {
            long start = System.nanoTime();
            target.add(instrument);
            System.out.printf("add() выполнен за %d нс%n", System.nanoTime() - start);
        }

        @Override
        public Instrument findByTicker(String ticker) {
            long start = System.nanoTime();
            Instrument result = target.findByTicker(ticker);
            System.out.printf("findByTicker() выполнен за %d нс%n", System.nanoTime() - start);
            return result;
        }

        @Override
        public Iterator<Instrument> iterator() {
            long start = System.nanoTime();
            Iterator<Instrument> result = target.iterator();
            System.out.printf("iterator() выполнен за %d нс%n", System.nanoTime() - start);
            return result;
        }
    }
}
