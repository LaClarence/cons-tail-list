package cons.tail;

public enum Nil implements Tail {
  INSTANCE;

  @SuppressWarnings("unchecked")
  public static <T> Tail<T> instance() {
    return (Tail<T>) INSTANCE;
  }
}
