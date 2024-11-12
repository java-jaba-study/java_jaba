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
s.forEach(System.out::println);
```