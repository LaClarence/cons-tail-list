package cons.list;

import module java.base;

public record Cons<T>(T head, GenericList<T> tail) implements GenericList<T> {
  public Cons {
    Objects.requireNonNull(head);
    Objects.requireNonNull(tail);
  }

  @Override
  public String toString() {
    return foldLeft(new StringJoiner(", ", "{", "}"), (sj, x) -> sj.add(String.valueOf(x))).toString();
  }
}
