# 모던 자바의 세가지 프로그래밍 개념
## 스트림 처리
1. 함수형 프로그래밍 패러다임 도입
- 이전 방식: Java 8 이전에는 데이터를 처리할 때 주로 for 또는 while 같은 반복문을 사용했습니다. 이러한 명령형 방식은 각 단계에서 데이터를 어떻게 처리할지 세부적으로 명시해야 했고, 코드가 복잡하고 길어질 수 있었습니다.
- 스트림 방식: Java 8의 Stream API는 함수형 프로그래밍 패러다임을 도입하여 **"무엇을 할 것인가"**에 초점을 맞추는 선언형 프로그래밍 방식을 제공합니다. 데이터를 어떻게 반복하는지 직접 명시하지 않고, 처리 로직만 선언합니다. 예를 들어, 필터링, 매핑, 정렬 등 고차 함수(함수를 인자로 받거나 리턴하는 함수)를 사용하여 데이터를 다룰 수 있습니다.

예시: 리스트에서 짝수만 골라내고 그 값을 2배로 만들어 리스트로 수집하는 방법



```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
List<Integer> result = new ArrayList<>();
for (Integer number : numbers) {
    if (number % 2 == 0) {
        result.add(number * 2);
    }
}
```

```java
List<Integer> result = numbers.stream()
                              .filter(n -> n % 2 == 0)
                              .map(n -> n * 2)
                              .collect(Collectors.toList());

```


2. 병렬 처리의 용이성
- parrallel stream을 통해 Thread API를 사용하지 않고도 편하게 병렬 처리가 가능함.

3. 코드 가독성
- 메서드 체이닝을 통해 표현한 필터링, 집계, 변경 등의 방식은 제어문을 활용한 외부 반복 방식보다 더 간결하기 때문에 코드 가독성이 높아짐.

## 동작 파라미터화로 메소드에 코드 전달하기
- predicate 람다 함수 활용
기존에는 아래와 같은 코드를 작성하기 위해 Predicate에 해당하는 메서드를 외부에 따로 정의했어야 했으나, 자바8에서는 아래와 같이 람다함수를 통해 정의할 수 있게 된다. 코드의 간결성이 좋아지는 케이스이다.
```java
public static List<Apple> filterApples(List<Apple> inventory, Predicate<Apple> p) {
    List<Apple> result = new ArrayList<>();
    for (Apple apple : inventory) {
        if (p.test(apple)) {
            result.add(apple);
        }
    }
    return result;
}

List<Apple> greenApples = filterApples(inventory, (Apple apple) -> "green".equals(apple.getColor()));
```
여기서 Predicate 함수는 true나 false를 반환하는 함수를 뜻한다. 람다 함수가 아니더라도 기존에 정의된 함수로도 위와 같이 작성할 수 있는데, 아래와 같은 방식으로 작성할 수 있다.
아래의 방식을 메서드 참조라고 한다.
```java
List<Apple> greenApples = filterApples(inventory, Apple:isHeavyGreenApple);

```

Predicate가 아니더라도 값을 변경할 때에 Function을 사용할 수 있고, 값을 소모하여 처리할 때에는 Consumer를 사용할 수 있다. 
이러한 기능을 함수형 인터페이스라고 하며, 자바의 함수를 일급함수로 다룰 수 있도록 만들어 준다.
아래에서 일급함수에 대해서 추가적으로 설명하겠습니다.

## 병렬성과 공유 가변 데이터
앞서 설명했던 Parrallel Stream을 포함하여, ForkJoinPool, ConcurrentHashMap 등을 이용하면 기존의 자바 스레드 API를 활용하는 것보다 쉽게 병렬 처리가 가능하다. 병렬 처리에 대해서는 일단 내가 잘 모르기도 하고 이후 장에서 깊게 다룰 때 더 공부해야 할 듯.

# 추가적인 내용
## 일급함수
일급 함수란 프로그래밍 언어에서 함수를 값처럼 다룰 수 있는 기능을 말합니다. 즉, 함수가 다음과 같은 특성을 가지면 일급 함수라고 할 수 있습니다.
- 변수에 저장할 수 있다.
- 파라미터로 전달할 수 있다.
- 반환값으로 사용할 수 있다.
- 데이터 구조에 저장할 수 있다.

일급함수의 개념을 도임함으로써 동작을 파라미터화 하여 전달할 수 있는데, 이는 코드의 간결성, 코드의 재사용성 증가, 전략 패턴과 유사한 형태를 사용함으로써 유연성 증가 등의 장점을 얻을 수 있다.
## 디폴트 메서드
디폴트 메서드는 해당 인터페이스를 구현하는 모든 클래스에 적용되는 메서드이다. 기존에는 인터페이스에 작성된 모든 메서드를 override해야하는 불편한이 있었으나 모던 자바에서는 이를 개선하기 위해 default 메서드를 만들어서, 코드의 중복작성을 방지한다.
## Null Safe
Optional을 활용하여 NullPointer Exception을 방지할 수 있다.
## 구조적 패턴 매칭
..?

