import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class GyooPredicate {

    public static <T> List<T> filter(List<T> list, Predicate<T> p) {
        List<T> results = new ArrayList<>();
        for (T s : list) {
            if (p.test(s)) {
                results.add(s);
            }
        }
        return results;
    }

    public static void main(String... args) throws IOException {
        List<String> listOfStrings = new ArrayList<String>();

        listOfStrings.add("abc");
        listOfStrings.add("");
        listOfStrings.add("123");
        listOfStrings.add("");

        Predicate<String> nonEmptyStringPredicate = (String s) -> !s.isEmpty();
        List<String> nonEmpty = filter(listOfStrings, nonEmptyStringPredicate);

        for (String s : nonEmpty) {
            System.out.println(s);
        }


    }
}
