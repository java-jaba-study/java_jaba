import java.io.IOException;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.ToIntBiFunction;

public class Gyoople {

    private int weight = 0;

    public Gyoople(Integer weight) {
        this.weight = weight;

    }

    public Gyoople() {
        this.weight = 0;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
    private static void printWeight(Gyoople g) {
        System.out.println(g.getWeight());
    }

    public static void main(String... args) throws IOException {

        List<Gyoople> gyoopleList = new ArrayList<>();
        gyoopleList.add(new Gyoople(5));
        gyoopleList.add(new Gyoople(2));
        gyoopleList.add(new Gyoople(8));
        gyoopleList.add(new Gyoople(3));

        Comparator<Gyoople> c1 =
                (Gyoople a1, Gyoople a2) -> a1.getWeight().compareTo(a2.getWeight());

        Collections.sort(gyoopleList, c1);

        gyoopleList.forEach(System.out::println);
        gyoopleList.forEach(g->System.out.println(g.getWeight()));

        System.out.println("---------");
        gyoopleList.add(new Gyoople(5));
        gyoopleList.add(new Gyoople(2));
        gyoopleList.add(new Gyoople(8));
        gyoopleList.add(new Gyoople(3));


        ToIntBiFunction<Gyoople, Gyoople> c2 =
                (Gyoople a1, Gyoople a2) -> a1.getWeight().compareTo(a2.getWeight());

        Collections.sort(gyoopleList, (Gyoople g1, Gyoople g2)->c2.applyAsInt(g1, g2));
        gyoopleList.forEach(g->System.out.println(g.getWeight()));

        System.out.println("---------");
        gyoopleList.add(new Gyoople(5));
        gyoopleList.add(new Gyoople(2));
        gyoopleList.add(new Gyoople(8));
        gyoopleList.add(new Gyoople(3));

        BiFunction<Gyoople, Gyoople, Integer> c3 =
                (Gyoople a1, Gyoople a2) -> a1.getWeight().compareTo(a2.getWeight());

        Collections.sort(gyoopleList, (g1, g2)->c3.apply(g1,g2) );

        gyoopleList.forEach(g->System.out.println(g.getWeight()));
    }

}