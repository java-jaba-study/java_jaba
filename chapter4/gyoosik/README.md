#  Introducing Stream


- What is a stream?
- Collections vs. streams
- Internal vs. external iteration
- Intermediate vs. terminal operations


## What is Stream

- Sequence of elements : Collections are about data; streams are about computations.
- Source : collections, arrays, or I/O resources.

## Traversable only Once
- terminal operation은 한번 수행 가능
```
s.forEach(System.out::println);
s.forEach(System.out::println); // 에러 발생함
```

## stream operation
### intermediate operations
 - Stream<T> filter(Predicate\<T>)
 - Stream<R> map(Function<T,R>)
 - Stream<T> limit(int)
 - Stream<T> sorted(Comparator\<T>)
 - Stream<T> distinct() # 중복 제거
### terminal operations
 - void forEach(Stream<>)
 - long count(Stream<>)
 - List/Map/Integer collect(Stream<>)