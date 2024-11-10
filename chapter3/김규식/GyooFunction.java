import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class GyooFunction {

    public static <T,R> List<R> map(List<T> list, Function<T,R> f) {

        List<R> res = new ArrayList<R>();
        for (T t : list) {
            res.add(f.apply(t));
        }
        return res;
    }

    public static void main(String... args) throws IOException {
        List<Integer> L = map(Arrays.asList("lamdas", "in", "action"), (String s)->s.length() );

        for(Integer i : L) {
            System.out.println(i);
        }
        System.out.println("-----");

        L.forEach(System.out::println);
    }
}
