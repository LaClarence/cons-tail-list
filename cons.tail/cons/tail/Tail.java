package cons.tail;

import module java.base;

public sealed interface Tail<T> permits Cons, Nil {

  default T reduce(BinaryOperator<T> op, T identity) {
    return switch (this) {
      case Nil _ -> identity;
      case Cons(var head, var tail) -> op.apply(head, tail.reduce(op, identity));
    };
  }

  default Tail<T> reverse() {
    return revRec(this, Nil.instance());
  }

  default Tail<T> append(T elem) {
    return switch (this) {
      case Nil _ -> Cons.of(elem, Nil.instance());
      case Cons(var head, var tail) -> Cons.of(head, tail.append(elem));
    };
  }

  default Tail<T> concat(Tail<T> other) {
    return switch (this) {
      case Nil _ -> other;
      case Cons(var head, var tail) -> Cons.of(head, tail.concat(other));
    };
  }

  default Tail<T> filter(Predicate<T> pred) {
    return filterRec(this, pred, Nil.instance());
  }

  default String toTailString() {
    return "{" + elements() + "}";
  }

  private static <T> Tail<T> revRec(Tail<T> list, Tail<T> acc) {
    return switch (list) {
      case Nil _ -> acc;
      case Cons(var head, var tail) -> revRec(tail, Cons.of(head, acc));
    };
  }

  private static <T> Tail<T> filterRec(Tail<T> list, Predicate<T> pred, Tail<T> acc) {
    return switch (list) {
      case Nil _ -> acc;
      case Cons(var head, var tail) -> filterRec(tail, pred,
          pred.test(head) ? acc.append(head) : acc);
    };
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
