3장 람다표현식 Lambda expressions


Lambda expressions

expressions와 statement의 차이는 무엇인가?

statement => lvalue
x = 2 + 3
expressions => rvalue
2 + 3

(parameters) -> expression
or (note the curly braces for statements)
(parameters) -> { statements; }



람다의 장점

Comparator<Apple> byWeight = new Comparator<Apple>() {
public int compare(Apple a1, Apple a2){
return a1.getWeight().compareTo(a2.getWeight());
}
};

Comparator<Apple> byWeight =
(Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight());


type deduction
Comparator<Apple> byWeight =
(a1, a2) -> a1.getWeight().compareTo(a2.getWeight());

(parameters) -> expression
(parameters) -> { statements; }

함수형 인터페이스

process( () -> System.out.println(“Hello World”) );

메소드 시그니처

void myMethod(int, float) - > "(IF)V"

Predicate<Apple> p = (Apple a) -> a.getWeight();
test의 메소드 시그니처는  (Ljava/lang/Object;)Z
apply의 시그니처는 (LApple;)Ljava/lang/Integer;



execute around pattern

자원 열고 실행하고 자원 수거하고

https://github.com/java-manning/modern-java/blob/master/ModernJavaInAction-master/src/main/java/modernjavainaction/chap03/ExecuteAround.java


public class ExecuteAround {

private static final String FILE = ExecuteAround.class.getResource("./data.txt").getFile();

public static void main(String... args) throws IOException {

    String oneLine = processFile((BufferedReader b) -> b.readLine());
    System.out.println(oneLine);

    String twoLines = processFile((BufferedReader b) -> b.readLine() + b.readLine());
    System.out.println(twoLines);
}


public static String processFile(BufferedReaderProcessor p) throws IOException {
try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
return p.process(br);
}
}

public interface BufferedReaderProcessor {

    String process(BufferedReader b) throws IOException;

}

}

람다에서는 당연히 지역변수 사용이 안된다.

