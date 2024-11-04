앞선 장에서는 동작을 파라미터화 하여 요구사항에 유연하게 대응하는 코드를 구현할 수 있음을 확인했다.

# 람다란 무엇인가?
### 람다 표현식은 메서드로 전달할 수 있는 익명 함수를 단순화한 것
## 람다의 특징
- 익명
	보통의 메서드와 달리 이름이 없으므로 익명이라 표한한다. 구현해야 할 코드에 대한 걱정거리가 줄어든다.
- 함수
	람다는 메서드처럼 특정 클래스에 종속되지 않으므로 함수라고 부른다. 하지만 메서드처럼 파라미터 리스트, 바디, 반환 형식, 가능한 예외 리스트를 포함한다.
- 전달 
	람다 표현식을 메서드 인수로 전달하거나 변수로 저장할 수 있다.
- 간결성 
	익명 클래스처럼 많은 자질구레한 코드를 구현할 필요가 없다.

람다의 장점을 아래의 코드 예시로 확인할 수 있다.
```java
        class Apple {
            private int id;
            private String color;
            private Integer weight;

            public Apple(int id, String color, int weight) {
                this.id = id;
                this.color = color;
                this.weight = weight;
            }
            public int getId() {
                return id;
            }
            public String getColor() {
                return color;
            }
            public Integer getWeight() {
                return weight;
            }
        }
```
테스트를 위해 위와 같은 Apple 클래스를 만들었다.
아래 코드는 Comparator를 익명클래스로 구현한 코드이다.
불필요한 보일러 플레이트 코드가 많은 것을 확인할 수 있다.
```java
        Comparator<Apple> byWeight = new Comparator<Apple>() {
            public int compare(Apple a1, Apple a2) {
                return a1.getWeight().compareTo(a2.getWeight());
            }
        };
```
아래 코드는 Comparator를 람다함수로 구현한 코드이다.
위 코드와 동작은 동일하나 코드의 간결성이 확실히 늘어난 것을 확인할 수 있다.
```java
        Comparator<Apple> byWeightWithLambda = (Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight());
```

# 어디에, 어떻게 람다를 사용할까?
익명인 람다함수를 어디에 사용할 것인가? 앞서 설명한 Comparator와 같은 함수형 인터페이스라는 문맥에서 람다표현식을 사용할 수 있다.
## 함수형 인터페이스
함수형 인터페이스는 정확히 하나의 추상 메서드를 지정하는 인터페이스이다. 
지금까지 살펴본 함수형 인터페이스에는 Comparator, Runnable, Predicate 등이 있다.
람다 표현식으로 함수형 인터페이스의 추상 메서드 구현을 직접 전달할 수 있으므로 전체 표현식을 함수형 인터페이스의 인스턴스로 취급할 수 있다.
아래에서 Runnable에서의 사용 예시를 보겠다.
```java
    public static void main(String[] args) {
        Runnable r1 = () -> System.out.println("Hello World 1");
        Runnable r2 = new Runnable() {
            public void run() {
                System.out.println("Hello World 2");
            }
        };

        process(r1);
        process(r2);
        process(() -> System.out.println("Hello World 3"));
    }
    public static void process(Runnable r) {
        r.run();
    }
```

## 함수 디스크립터
람다표현식의 시그니처를 서술하는 메서드를 함수 디스크립터라고 부른다.
여러가지 종류의 함수형 인터페이스를 통해서, 이러한 함수 디스크립터를 정의할수 있다.
또한, 사용자 정의 함수형 인터페이스를 사용하면, 함수 디스크립터를 완전히 자유롭게 생성할 수 있다.
그렇다면 람다함수를 매개변수로 받는 메서드는 어떻게 람다 표현식을 검사하는가? 에 대한 의문이 남는다.
또한, 람다표현식은 함수형인터페이스를 인수로 받는 메서드에만 사용할 수 있다.

## 람다 활용하기(그런데 이제 동작파라미터화를 곁들인)
### 실행 어라운도 패턴
자원을 열고 작업을 수행하고 닫는 코드들의 경우 초기화 작업과 마무리 작업이 거의 유사하다는 특징을 가진다.
실제 자원을 처리하는 코드를 설정과 정리 두 과정이 둘러싸는 형태를 갖게 된다.
따라서 실제 작업을 동작파라미터화 하여서 코드를 전달하는 형태로 작성할 수 있다. 
여기서 작성할 로직이 간단하다면 람다를 쓰면된다.
기존 코드는 아래와 같다.
```java
    public String processFile() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
            return br.readLine();
        }
    }
```
위코드는 한 줄을 읽는 코드이다.
두 줄을 읽는 코드를 위해 매번 새로운 함수를 만드는 것보다는 두 줄을 읽는다는 동작을 파라미터화 하여 전달하는 것이 여러가지 요구사항에 대처하기 좋은 코드일 것이다.

### 1단계 : 동작 파라미터화를 기억하라.
설정부와 정리부는 재사용하고 실제 작업에 해당하는 부분만 다른 동작을 수행하도록 명령할 수 있다면 좋을 것이다.
즉, precessFile의 동작을 파라미터화한다. 람다를 통해 전달할 것이므로 최종적으로 호출하는 형태는 아래와 같을 것이다.
```java
String result1 = processFile((BufferedReader br) -> br.readLine());
```
위 코드가 동작하게 하기 위해 processFile 함수가 람다함수를 받을 수 있도록 변경해야한다.
그 말인 즉슨, processFile의 매개변수로 함수형인터페이스가 들어오도록 해야한다.

### 2단계 : 함수형 인터페이스를 이용해서 동작 전달
```java
@FunctionalInterface
public interface BufferedReaderProcessor<T,R> {
    R process(T reader) throws IOException;
}
```
위와 같이 함수형 인터페이스를 직접 구현하였다.
@FunctionalInterface 어노테이션은 생략 가능하나, 가독성 및 유지보수 측면에서 남기는 것이 좋다.
```java
public String processFile(BufferedReaderProcessor<BufferedReader, String> b) throws IOException {
    ... ...
}
```
위와 같이 함수형 인터페이스를 받도록 하여서 람다함수를 인자로 넣을 수 있게 변경하였다.

### 3단계 : 동작 실행
```java
public static String processFile(BufferedReaderProcessor<BufferedReader, String> b) throws IOException {
	try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
    	return b.process(br);
	}
}
```
이제 함수형인터페이스의 구현체의 인스턴스가 전달된 것과 동일한 형태로 동작한다.
해당 구현체는 함수형인터페이스의 하나뿐인 추상메서드를 람다함수로 override하여 구현한 것과 같다.
따라서 b.process()의 동작은 processFile()의 인자로 들어가는 람다함수인 것이다..!

### 4단계 : 람다 전달
```java
String oneLine = processFile((BufferedReader br) -> br.readLine());
```
```java
String twoLine = processFile((BufferedReader br) -> br.readLine() + br.readLine());
```
보시다 싶이 유연하다. 어떤 동작을 할 지 간편하게 전달이 가능하다.


# 함수형 인터페이스의 사용
## Predicate
```java
	public static <T>List<T> filter(List<T> list, Predicate<T> p) {
        List<T> results = new ArrayList<T>();
        for (T t : list) {
            if (p.test(t)) {
                results.add(t);
            }
        }
        return results;
    }
    
    public static void main(String[] args) {
        Predicate<String> nonEmptyStringPredicate = (String s) -> !s.isEmpty();
        List<String> list = Arrays.asList(new String[]{"ads", "", ""});
        List<String> nonEmpty = filter(list, nonEmptyStringPredicate);
        list.forEach(System.out::println);
        nonEmpty.forEach(System.out::println);
    }
```
Predicate 함수형 인터페이스의 사용 예시, and나 or같은 default 메서드도 사용할 수 있다.(참고로 함수형 인터페이스에는 default메서드가 들어갈수도 있다. 제한이 있는 것은 추상메서드의 개수이다.)

## Consumer
```java
    public static <T> void forEach(List<T> list, Consumer<T> consumer) {
        for (T t : list) {
            consumer.accept(t);
        }
    }
    public static void main(String[] args) {
        forEach(Arrays.asList(1,2,3,4,5), (Integer x) -> System.out.println(x));
    }
```

## Function
```java
    public static <T, R> List<R> map(List<T> list, Function<T, R> f) {
        List<R> result = new ArrayList<>();
        for (T t : list) {
            result.add(f.apply(t));
        }
        return result;
    }
    public static void main(String[] args) {
        List<Integer> l = map(
                Arrays.asList("lambdas", "in", "action"),
                (String s) -> s.length()
        );
        System.out.println(l);
    }
```

## 기본형 특화
제네릭 파라미터는 참조형만 사용할 수 있기 때문에 함수형 인터페이스에서는 무조건 boxing이 일어나게 된다. 따라서 성능적인 손실이 있다.  그래서 기본형에 특화된 함수형 인터페이스도 존재한다.

## 예외, 람다 함수형 인터페이스 간의 관계
함수형 인터페이스는 확인된 예외를 던지는 동작을 허용하지 않는다.
따라서 함수형 인터페이스를 직접 정의하거나, 람다함수내에서 try/catch로 감싸야 한다.

# 형식 검사, 형식 추론, 제약
컴파일러가 람다 표현식의 형식을 어떻게 확인하는지에 대한 내용이다.
## 형식검사
람다의 type은 어떻게 결정되는가?

1. 람다를 매개변수로 두는 메서드의 정의를 확인한다.
2. 대상형식을 확인한다.
3. 대상형식의 인터페이스의 추상메서드를 확인한다.
4. 대상형식의 추상메서드를 찾는다.
5. 추상메서드에서 확인할 수 있는 함수 디스크립터와 람다표현식을 비교한다.

컴파일러는 위 순서대로 람다 표현식의 타입을 결정한다.
추가적으로 람다표현식이 만약에 예외를 던질 수 있는 상황이라면, 추상 메서드도 같은 예외를 던질 수 있도록 throws를 선언해야 한다.

## 같은 람다, 다른 함수형 인터페이스
람다와 주변 그 주변 맥락을 통해 람다 표현식의 타입은 결정된다.
그렇기에 같은 람다라 하더라고 다른 함수형 인터페이스에 적용될 수 있는데, 그 예시는 아래와 같다.
```java
Comparator<Apple> c1 = (Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight());
BiFunction<Apple> c2 = (Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight());
```

## 형식 추론
자바 컴파일러는 람다 표현식이 사용된 문맥을 이용해서 람다 표현식과 관련된 함수형 인터페이스를 추론한다. 즉, 대상 형식을 이용해서 함수 디스크립터를 알 수 있으므로 컴파일러는 람다의 시그니처도 추론할 수 있다. 그 예시는 아래와 같다.
```java
List<Apple> greenApples = filter(inventory, apple -> GREEN.equals(apple.getColor()));
Comparator<Apple>  c = (a1, a2) -> a1.getWeight().compareTo(a2.getWeight());
```

## 지역 변수 사용
람다 표현식에서는 외부의 변수를 활용할 수 있다. 이러한 기능을 람다 캡쳐링이라 부른다.
```java
int portNumber = 1337;
Runnable r = () -> System.out.println(portNumber);
```
그러나 이렇게 했을 때 변수 사용에 약간의 제약이 있다. 해당 지역 변수는 final을 작성하지 않더라도 final처럼 동작하게 된다.
```java
int portNumber = 1337;
Runnable r = () -> System.out.println(portNumber);
portNumber = 31337;
```
위 코드는 컴파일이 되지 않는다.
지역변수에 대한 제약이 필요한 이유는 인스턴스 변수와 지역 변수는 태생이 다르기 때문이다. 인스턴스 변수는 힙에 저장되는 반면 지역 변수는 스택에 위치한다. 원래 변수에 접근하는 것을 허용하는 것이 안니라 자유 지역 변수의 복사본을 제공한다. 따라서 복사본의 값이 바뀌지 않아야 하므로 지역 변수에는 한 번만 값을 할당해야 한다는 제약이 생긴 것이다.

# 메서드 참조
메서드 참조를 이용하면 기존의 메서드 정의를 재활용해서 람다처럼 전달할 수 있다. 아래는 그 예시이다.
```java
        inventory.sort((String fruit1, String fruit2) -> Integer.compare(fruit1.length(), fruit2.length()));
```
위 코드를 아래와 같이 변경할 수 있다.
```java
        inventory.sort(Comparator.comparing(String::length));
```

## 요약
메서드 참조는 특정 메서드만을 호출하는 람다의 축약형이라고 생각할 수 있다.메서드 참조를 통해 이미 구현된 메서드로 람다표현식을 만들 수 있다. 이때 명시적으로 메서드명을 참조함으로써 가독성을 높일 수 있다.

예를 들어 Apple::getWeight라고 한다면 Apple 클래스의 getWeight를 참조한다는 것을 알 수 있다. 실제로 메서드를 호출하는 것이 아니기 때문에 괄호는 필요없다.
생성자 참조도 가능함 
```
        Function<Integer, String> intToString = String::valueOf;
        System.out.println(intToString.apply(123)); // 출력: "123" 
        
        String str = "Hello, World!";
        Supplier<Integer> stringLength = str::length;
        System.out.println(stringLength.get()); // 출력: 13
        

        Function<String, Integer> stringLength = String::length;
        System.out.println(stringLength.apply("Java")); // 출력: 4
        
        
        Supplier<List<String>> listSupplier = ArrayList::new;
        List<String> list = listSupplier.get();
        System.out.println(list); // 출력: []
```


# 람다 표현식을 조합할 수 있는 유용한 메서드 
자바8 API의 함수형 인터페이스는 다양한 유틸리티 메서드를 포함한다. 예를 들어 Comparator, Function, Predicate 같은 함수형 인터페이스는 람다 표현식을 조합할 수 있도록 유틸리티 메서드를 제공한다. 이러한 기능들은 default 메서드를 통해서 제공된다.

## Comparator 조합
```java
Comparator<Apple> c = Comparator.comparing(Apple::getWeight);
```
```java
Comparator<Apple> c = Comparator.comparing(Apple::getWeight).reversed();
```
거꾸로 뒤집기
```java
Comparator<Apple> c = Comparator.comparing(Apple::getWeight)
	.reversed()
    .thenComparing(Apple::getCountry));
```
무게가 같은 경우 국가별로 정렬하도록 함.

위 처럼 조합하여 정렬이 가능함.

## Predicate 조합

복잡한 프레디케이트를 만들 수 있도록 negate, and, or 메서드를 제공한다.
예를 들어 '빨간색이 아닌 사과"처럼 특정 프레디케이트를 반전 시킬 때 negate 메서드를 사용할 수 있다.
```java
Predicate<Apple> notRedApple = redApple.negate() // 기존 프레디케이트 객체 redApple의 결과를 반전시킨 객체를 만든다.
```
또한 and 메서드를 이용해서 빨간색이면서 무거운 사과를 선택하도록 두 람다를 조합할 수 있다. 그뿐만 아니라 or을 이용해서 "빨간색이면서 무거운 사과 또는 그냥 녹생 사과" 등 다양한 조건을 만들 수 있다.
```java
Predicate<Apple> RedHeavyOrGreenApple = 
  redApple.and(apple -> apple.getWeight > 150)
          .or(apple -> GREEN.equals(a.getColor()))
```


## Function 조합
compose와 andThen을 통해서 함수를 조합하여 사용할 수 있다.
Function 인터페이스는 Function 인터페이스를 반환하는 andThen, compose 두 가지 디폴트 메서드를 제공한다.
andThen 메서드는 주어진 함수를 먼저 적용한 결과를 다른 함수의 입력으로 전달하는 함수를 반환한다.
```java
Function<Integer, Integer> f = x -> x + 1;
Function<Integer, Integer> g = x -> x * 2;
Function<Integer, Integer> h = f.andThen(g); //g(f(x))
int result = h.apply(1); // 4를 반환
```
compose 메서드는 인수로 주어진 함수를 먼저 실행한 다음 그 결과를 외부 함수의 인수로 제공한다.
즉 f.andThen(g) 대신 compose를 사용하면 g(f(x))가 아니라 f(g(x))가 된다.
```java
Function<Integer, Integer> f = x -> x + 1;
Function<Integer, Integer> g = x -> x * 2;
Function<Integer, Integer> h = f.compose(g); //f(g(x))
int result = h.apply(1); // 4를 반환
```
여러 유틸리티 메서드를 조합해서 다양한 변환 파이프라인을 만들수 있다.
헤더를 추가(addHeader)한 다음에, 철자 검사(checkSpelling)를 하고, 마지막에 푸터를 추가(addFooter) 할 수도 있다.
```java
Function<String, String> addHeader = Letter::addHeader;
Function<String, String> transFormationPipeline = addHeader
    .andThen(Letter::checkSpelling)
    .andThen(Letter::addFooter);
```
        
    