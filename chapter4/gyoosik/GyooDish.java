import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;


public class GyooDish {

    private final String name;
    private final boolean vegetarian;
    private final int calories;
    private final Type type;

    public GyooDish(String name, boolean vegetarian, int calories, Type type) {
        this.name = name;
        this.vegetarian = vegetarian;
        this.calories = calories;
        this.type = type;
    }

    public String getName() {
        return name;
    }
    public boolean isVegetarian() {
        return vegetarian;
    }
    public int getCalories() {
        return calories;
    }
    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return name;
    }

    public enum Type { MEAT, FISH, OTHER }

    public static void main(String[] args) {
        List<GyooDish> menu = Arrays.asList(
                new GyooDish("pork", false, 800, GyooDish.Type.MEAT),
                new GyooDish("beef", false, 700, GyooDish.Type.MEAT),
                new GyooDish("chicken", false, 400, GyooDish.Type.MEAT),
                new GyooDish("french fries", true, 530, GyooDish.Type.OTHER),
                new GyooDish("rice", true, 350, GyooDish.Type.OTHER),
                new GyooDish("season fruit", true, 120, GyooDish.Type.OTHER),
                new GyooDish("pizza", true, 550, GyooDish.Type.OTHER),
                new GyooDish("prawns", false, 300, GyooDish.Type.FISH),
                new GyooDish("salmon", false, 450, GyooDish.Type.FISH) );

        List<String> threeHigh = menu.stream().
                filter((GyooDish d)->d.getCalories() > 300).
                map(GyooDish::getName).
                limit(4).
                collect(toList());

        threeHigh.forEach(System.out::println);
    }

}
