package cons.tail;

public sealed interface Tail<T> permits Cons, Nil {

  default String toTailString() {
    return "{" + elements() + "}";
  }

  private String elements() {
    return switch (this) {
      case Nil _ -> "";
      case Cons(var head, var tail) -> {
        var rest = tail.elements();
        yield rest.isEmpty() ? String.valueOf(head) : head + ", " + rest;
      }
    };
  }
}
