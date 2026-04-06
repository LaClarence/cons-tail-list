import module java.base;

public record Cons<T>(T head, Tail<T> tail) implements Tail<T> {
  public Cons {
    Objects.requireNonNull(head);
    Objects.requireNonNull(tail);
  }

  public static <T> Cons<T> of(T head, Tail<T> tail) {
    return new Cons<>(head, tail);
  }
}