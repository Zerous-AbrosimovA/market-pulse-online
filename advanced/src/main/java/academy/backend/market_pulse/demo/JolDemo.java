package academy.backend.market_pulse.demo;

/**
 * Демонстрация для практики с JOL (семинар 1): точные размеры объектов и
 * сравнение shallow size двух двумерных массивов разной формы.
 */
public class JolDemo {

    public static void main(String[] args) {
        // Размер заголовка и layout пустого объекта
        // System.out.println(ClassLayout.parseClass(Object.class).toPrintable());

        // Layout нашего Stock
        // Stock stock = new Stock("SBER", "Сбербанк", Currency.RUB, "Financials", new BigDecimal("6.5"));
        // System.out.println(ClassLayout.parseInstance(stock).toPrintable());

        // Сравниваем размеры двух массивов
        // int[][] small = new int[10][1000];
        // int[][] large = new int[1000][10];

        // System.out.println("int[10][1000] shallow size:  "
        //         + ClassLayout.parseInstance(small).instanceSize());
        // System.out.println("int[1000][10] shallow size:  "
        //         + ClassLayout.parseInstance(large).instanceSize());

        // GraphLayout покажет полный граф — попробуйте сами!
        // TODO: студентам предлагается самостоятельно раскомментировать и
        // сравнить deep size обоих массивов через GraphLayout.parseInstance(...).toFootprint()
        // System.out.println(GraphLayout.parseInstance(small).toFootprint());
        // System.out.println(GraphLayout.parseInstance(large).toFootprint());

        // System.out.println(ClassLayout.parseClass(Example.class).toPrintable());
        // var instance = new Example(1, 2, new int[10], 3L, new Object());
        // System.out.println(ClassLayout.parseInstance(instance).toPrintable());
    }

    public static class Example {
        private final int a;
        private final long b;
        private final int[] array;
        private final Long bObject;
        private final Object reference;

        public Example(int a, long b, int[] array, Long bObject, Object reference) {
            this.a = a;
            this.b = b;
            this.array = array;
            this.bObject = bObject;
            this.reference = reference;
        }
    }
}
