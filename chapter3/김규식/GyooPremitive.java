import java.io.IOException;
import java.util.function.IntPredicate;

public class GyooPremitive {
    public static void main(String... args) throws IOException {

        IntPredicate evenNumbers = (i)-> i % 2 == 0;
        System.out.println(evenNumbers.test(1000));
        IntPredicate oddNumbers = (i)-> i % 2 == 1;
        System.out.println(oddNumbers.test(1000));
    }
}
