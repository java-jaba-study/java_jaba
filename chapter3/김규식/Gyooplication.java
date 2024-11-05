import java.util.function.Function;
import java.util.function.Supplier;

public class Gyooplication {
    public static void main(String[] args) {

        Supplier<Gyoople> c1 = Gyoople::new;
        Supplier<Gyoople> s1 = ()-> new Gyoople();

        //Supplier<Gyoople> c2 = (i)->new Gyoople(i);

        Function<Integer, Gyoople> c2 = Gyoople::new;
        Function<Integer, Gyoople> s2 = (i) -> new Gyoople(i);

    }
}
