import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class Application {
    public static void main(String[] args) {
        Comparator<Apple> c = Comparator.comparing(Apple::getWeight);

        List<Apple> listapple = new ArrayList<>();
        listapple.add(new Apple(1,Color.RED));
        listapple.add(new Apple(2,Color.RED));
        listapple.add(new Apple(3,Color.RED));
        listapple.add(new Apple(4,Color.GREEN));

        listapple.sort(Comparator.comparing(Apple::getWeight).reversed());
        listapple.sort(Comparator.comparing(Apple::getWeight).reversed().thenComparing(Apple::getColor));


        Predicate<Apple> redApple = a -> a.getColor() == Color.RED;
        Predicate<Apple> notRedApple = redApple.negate();
        Predicate<Apple> notRedAndHeavyApple = notRedApple.and( (apple) -> apple.getWeight() > 3);

        List<Apple> listnotredandhevyapple = filter(listapple, notRedAndHeavyApple);

        listnotredandhevyapple.forEach(System.out::println);





    }
    public static <T> List<T> filter(List<T> list, Predicate<T> p) {
        List<T> results = new ArrayList<>();
        for (T s : list) {
            if (p.test(s)) {
                results.add(s);
            }
        }
        return results;
    }
}
