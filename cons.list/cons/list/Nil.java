package cons.list;

public record Nil<T>() implements GenericList<T> {
  @Override
  public String toString() {
    return "{}";
  }
}
