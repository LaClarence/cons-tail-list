package cons.list;

import module java.base;

public sealed interface GenericList<T> permits Cons, Nil {

  static <T> GenericList<T> empty() {
    return new Nil<>();
  }

  @SafeVarargs
  static <T> GenericList<T> of(T... values) {
    GenericList<T> result = empty();
    for (var i = values.length - 1; i >= 0; i--) {
      result = new Cons<>(values[i], result);
    }
    return result;
  }

  default <R> R foldLeft(R acc, BiFunction<R, T, R> f) {
    return switch (this) {
      case Nil<T> _ -> acc;
      case Cons(var head, var tail) -> tail.foldLeft(f.apply(acc, head), f);
    };
  }

  default <R> R foldRight(R acc, BiFunction<T, R, R> f) {
    return switch (this) {
      case Nil<T> _ -> acc;
      case Cons(var head, var tail) -> f.apply(head, tail.foldRight(acc, f));
    };
  }

  default GenericList<T> rev() {
    return foldLeft(empty(), (acc, x) -> new Cons<>(x, acc));
  }

  default GenericList<T> append(GenericList<T> other) {
    return foldRight(other, Cons::new);
  }

  default GenericList<T> filter(Predicate<T> pred) {
    return foldRight(empty(), (x, acc) -> pred.test(x) ? new Cons<>(x, acc) : acc);
  }
}
