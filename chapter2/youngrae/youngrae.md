동작 파라미터화란, 아직은 어떻게 실행할 것인지 결정하지 않은 코드 블록을 의미한다.
즉, 로직 자체의 파라미터화라고 생각할 수 있다.
이러한 동작 파라미터화가 필요한 이유는 변화하는 요구사항에 빠르게 대응할 수 있도록 하기 위함이다.

동작 파라미터화를 통해 아래와 같은 형태로 메서드를 구현할 수 있다.
- 리스트의 모든 요소에 대해서 '어떤 동작'을 수행할 것임.
- 리스트 관련 작업을 끝낸 다음에 '어떤 다른 동작'을 수행할 것å임.
- 에러가 발생하면 '정해진 어떤 다른 동작'을 수행할 것임.

아래는 실제로 변화하는 요구사항에 대응하는 예시를 보여줌으로써 동작 파라미터화의 이점에 대해 설명할 것이다.
# 변화하는 요구사항에 대응하기
## 첫 번째 시도 : 초록색 사과 필터링
```java
	enum Color { RED, GREEN }
    
    
    public static List<Apple> filterGreenApples(List<Apple> inventory) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (GREEN.equals(apple.getColor())) {
                result.add(apple);
            }
        }
        return result;
    }
```
inventory에 사과를 넣는 로직에서 초록색 사과만 필터링해서 넣도록 변경한 코드이다.
만약에 이 상황에 빨간색 사과도 필터링하고 싶다면, filterRedApples 메서드를 만드는 방법도 있지만, 더 좋은 방법은 색을 파라미터화 하는 것이다.

## 두 번째 시도 : 색을 파라미터화
```java
	enum Color { RED, GREEN }


    public static List<Apple> filterApplesColor(List<Apple> inventory, Color color) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (color.equals(apple.getColor())) {
                result.add(apple);
            }
        }
        return result;
    }
```
여기에서 만약에 추가적으로 무게를 통해서도 필터링하고 싶다면, 무게를 파라미터화해서 무게에 대한 필터링 조건을 넣을 수 있다. 이런 식으로 가능한 모든 속성으로 필터링을 하다보면 아래와 같은 코드가 나온다.
아래는 하나의 함수에서 무게로 필터링할 지 색깔로 필터링할 지 까지의 플래그까지 파라미터화하여 작성한 코드이다.

## 세 번째 시도 : 가능한 모든 속성으로 필터링
```java
  public static List<Apple> filterApples(List<Apple> inventory, Color color,
                                          int weigth, boolean flag) {
      List<Apple> result = new ArrayList<>(); 
      for (Apple apple : inventory) {
          if ((flag && color.equals(apple.getColor())) ||  // flag true면 색상 비교
              (!flag && apple.getWeight() > weigth)) {     // flag false면 무게 비교
              result.add(apple);
          }
      }
      return result;
  }
```
위 함수를 호출하여 사용하는 부분을 살펴보면 아래와 같다.

```java
List<Apple> greenApples = filterApples(inventory, GREEN, 0, true);
List<Apple> heavyApples = filterApples(inventory, null, 150, false);
```
호출부만 봐서는 GREEN, 0, true 와 같이 아무렇게나 적힌 파라미터가 어떤 동작을 의미하는 지 알 수 없다.
또한 위 코드에서는 동작이 미리 정의되어 있으므로 호출하는 입장에서 유연하게 코드를 작성하기 힘들다. 문제가 잘 정의되어 있는 상태라면, 위 코드도 효과적이겠지만 유연하게 요구사항에 대응해야하는 경우라면 매번 중복된 부분을 가진 함수를 계속해서 생성해야할 것이다.
즉, 유연성과 가독성이 떨어지며, 코드중복을 생산한다.

위와 같은 문제를 해결하기 위해 동작을 파라미터화하는 방법이 있다.
# 동작 파라미터화
아래에서는 동작을 파라미터화하기 위해 인터페이스를 활용할 것이다.
앞선 포스팅에서 설명했던 바와 같이 참 또는 거짓을 반환하는 함수를 프레디케이트라고 한다. 해당 개념을 차용해서 프레디케이트라는 인터페이스를 생성하겠다.
```java
public interface ApplePredicate {
	boolean test (Apple apple);
}
```
아래에는 해당 구현체를 작성하겠다.
```java
public class AppleGreenColorPredicate implements ApplePredicate {
    @Override
    public boolean test(Apple apple) {
        return GREEN.equals(apple.getColor());
    }
}
```
```java
public class AppleHeavyWeightPredicate implements ApplePredicate {
    @Override
    public boolean test(Apple apple) {
        return apple.getWeight() > 150;
    }
}
```
함수를 생성할 때는 파라미터에 인터페이스로 작성하고, 해당 함수들을 불러올 때는 구현체를 통해서 불러오도록 한다. 위와 같은 방법을 디자인패턴에서는 전략 패턴이라고 한다.

## 네 번째 시도 : 추상적 조건으로 필터링
```java
public static List<Apple> filterApples(List<Apple> inventory, ApplePredicate p) {
    List<Apple> result = new ArrayList<>(); 
    for (Apple apple : inventory) {
        if (p.test(apple)) {      // Predicate 객체로 사과 필터링 조건을 캡슐화
            result.add(apple);
        }
    }
    return result;
}
```
위와 같이 작성하면, ApplePredicate 인터페이스의 구현체는 어떤 것이든 파라미터로 넣을 수 있다.
ApplePredicate를 구현한 구현체는 모두 test라는 함수를 가지고 있기 때문에 함수를 파라미터화 하여 넣은 것과 동일하게 동작한다고 생각할 수 있다. 
따라서 위 방법을 통해서 동작을 파라미터화 하여서 제어할 수 있게 되었다.
이제 filtering 조건을 추가해야하는 요구 사항이 있을 때, ApplePredicate 인터페이스의 구현체를 만들면 유연하게 대처할 수 있다.

# 과정 간소화
## 다섯 번째 시도 : 익명 클래스
익명 클래스를 활용하면, 구현체를 직접 만들지 않고도 파라미터화된 동작을 전달할 수있다. 
그 예시는 아래와 같다.
```java
List<Apple> redApples = filterApples(inventory, new ApplePredicate() { // 메서드 동작을 직접 파라미터화
            @Override
            public boolean filter(Apple apple) {
                return RED.equals(apple.getColor());    // 빨간색 사과 필터링
            }
        });
```
익명 클래스는 장황함으로 인해서 코드의 가독성을 떨어트리는 문제가 있다.(그래서 개인적으로 거의 사용하지 않는 코딩 방법이다.) 개인적인 의견으로는 이렇게 할 바에는 차라리 구현체를 매번 만드는게 나을 것이라고 생각한다.
하지만, 모던 자바에서는 이를 해결한 방식으로 람다함수를 사용하는데, 아주 기가 막힌다.^^;

## 여섯 번째 시도 : 람다 표현식 사용
이는 함수형인터페이스라는 개념을 사용한 방식인데, 조금 신기한 방법이다.
그냥 아래와 같이 작성하면된다.
```java
List<Apple> greenApples = filterApples(inventory, (Apple apple) -> GREEN.equals(apple.getColor()));
List<Apple> heavyApples = filterApples(inventory, (Apple apple) -> apple.getWeight() > 150);
List<Apple> redApples = filterApples(inventory, (Apple apple) -> RED.equals(apple.getColor()));
```
java 8 부터 메서드를 하나만 가지는 인터페이스를 파라미터로 작성한 경우에 위와 같이 람다표현식을 파라미터의 값으로 넣을 수 있다..!

## 일곱 번째 시도 : 리스트 형식으로 추상화
```java
public interface Predicate<T> {
    boolean filter (T t);
}
 

public static <T> List<T> filter(List<T> inventory, Predicate<T> p) {
    List<T> result = new ArrayList<>(); 
    for (T e : inventory) {
        if (p.filter(e)) {      
            result.add(e);
        }
    }
    return result;
}
 

List<Apple> greenApples = filter(inventory, (Apple apple) -> GREEN.equals(apple.getColor()));
List<Integer> evenNumbers = filter(numbers, (Integer i) -> i%2 == 0);
```
이제는 바나나도 필터링이 가능하다 

# 실전 예제
## Comparator로 정렬하기
```java
public class ComparatorExample {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("apple", "banana", "cherry", "date");

        // Comparator를 사용하여 문자열 길이로 정렬
        list.sort(Comparator.comparingInt(String::length));

        System.out.println(list);
    }
}

```
## Runnable로 코드 블록 실행하기
```java
public class RunnableExample {
    public static void main(String[] args) {
        Runnable runnable = () -> System.out.println("Hello from a thread!");

        Thread thread = new Thread(runnable);
        thread.start();
    }
}

```
## Callable을 결과로 반환하기

```java
import java.util.concurrent.*;

public class CallableExample {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Callable<String> callable = () -> {
            // 긴 작업을 시뮬레이션
            Thread.sleep(2000);
            return "Result from Callable";
        };

        Future<String> future = executor.submit(callable);

        try {
            // 결과를 기다리고 출력
            String result = future.get();
            System.out.println(result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }
}

```