public sealed interface Tail<T> permits Cons, Nil {

  default String toTailString() {
    return "{" + elements() + "}";
  }

  private String elements() {
    return switch (this) {
      case Nil _ -> "";
      case Cons(var head, var tail) -> switch (tail) {
        case Nil _ -> String.valueOf(head);
        case Cons<T> _ -> String.valueOf(head) + ", " + tail.elements();
      };
    };
  }
}