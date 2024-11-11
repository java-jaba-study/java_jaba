```declarative
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

List<String> lowCaloricDishesName = menu.stream()
                                    .filter(d -> d.getCalories() < 400)
                                    .sorted(comparing(Dish::getCalories))
                                    .map(Dish::getName)
                                    .collect(toList());

```
#### stream을 parallelStream()으로 바꾸면 멀티코어 아키텍처에서 병렬로 실행 가능

### 스트림
```declarative
import static java.util.stream.Collectors.toList;

List<String> threeHighCaloricDishNames = menu.stream()
                                                .filter(dish -> dish.getCalories() > 300)
                                                .map(Dish::getName)
                                                .limit(3)
                                                collect(toList());

System.out.println(threeHighCaloricDishnames);
```

- filter: 람다를 인수로 받아 스트림에서 특정 요소를 제외.(if문 역할)
- map: 람다를 이용해서 한 요소를 다른 요소로 변환 or 정보 추출 -> 스트림에 있는 값을 원하는 메소드에 넣으면 메소드 실행 결과(반환값) 담김
- limit: 스트림에 저장되는 수 제한 
- collect: 스트림을 다른 형식으로 변환

### 스트림 연산
#### 중간연산: 다른 스트림을 반환
```declarative
List<String> names = 
    menu.stream()
    .filter(dish -> {
        System.out.println("filtering: " + dish.getName());
        return dish.getCalories() > 300;
})
    .map(dish -> {
        System.out.println("mapping: " + dish.getName());
        return dish.getName();
})
    .limit(3)
    .collect(toList());
        
```
```declarative
filtering:pork
mapping:pork
...
```
- 쇼트서킷
- loop fusion: filter와 map은 서로 다른 연산이지만 한 과정으로 병합

#### 최종연산: 스트림 파이프라인에서 결과를 도출