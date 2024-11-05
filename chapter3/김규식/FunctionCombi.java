import java.io.IOException;
import java.util.function.Function;

public class FunctionCombi {

    public static void main(String... args) throws IOException {

        Function<Integer, Integer> f = x -> x + 1;
        Function<Integer, Integer> g = x -> x * 2;
        Function<Integer, Integer> h = f.andThen(g);

        int result = f.apply(3);

        System.out.println(result);
    }
}
