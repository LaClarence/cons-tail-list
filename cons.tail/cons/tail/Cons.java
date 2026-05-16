package cons.tail;

import module java.base;

public record Cons<T>(T head, Tail<T> tail) implements Tail<T> {
  public Cons {
    Objects.requireNonNull(head);
    Objects.requireNonNull(tail);
  }

  public static <T> Cons<T> of(T head, Tail<T> tail) {
    return new Cons<>(head, tail);
  }

  public static <T> Cons<T> of(T head) {
    return Cons.of(head, Nil.instance());
  }

  public static <T> Tail<T> of(T... values) {
    Tail<T> result = Nil.instance();
    for (var i = values.length - 1; i >= 0; i--) {
      result = Cons.of(values[i], result);
    }
    return result;
  }
}
