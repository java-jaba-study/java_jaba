import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class GyooConsumer {

    public static <T> void forEach(List<T> list, Consumer<T> consumer) {
        for (T t : list) {
            consumer.accept(t);
        }
    }
    public static void main(String... args) throws IOException {
        forEach(
                Arrays.asList(1,2,3,4,5),
                (Integer i) -> System.out.println(i)
                );

    }
}
