import module java.base;

public class Main {

  static <T> T reduceRec(Tail<T> list, BinaryOperator<T> op, T identity) {
    return switch (list) {
      case Cons(var head, var tail) -> op.apply(head, reduceRec(tail, op, identity));
      case Nil _ -> identity;
    };
  }

  static Integer sum(Tail<Integer> list) {
    return reduceRec(list, Integer::sum, 0);
  }

  private static <T> Tail<T> revRec(Tail<T> list, Tail<T> acc) {
    return switch (list) {
      case Nil _ -> acc;
      case Cons(var head, var tail) -> revRec(tail, Cons.of(head, acc));
    };
  }

  static <T> Tail<T> reverse(Tail<T> list) {
    return revRec(list, Nil.instance());
  }

  static <T> Tail<T> append(Tail<T> list, T elem) {
    return switch (list) {
      case Nil _ -> Cons.of(elem, Nil.instance());
      case Cons(var head, var tail) -> Cons.of(head, append(tail, elem));
    };
  }

  static List<String> graphemes(String text) {
    var breaker = BreakIterator.getCharacterInstance();
    breaker.setText(text);
    var list = new ArrayList<String>();
    var start = breaker.first();
    for (var end = breaker.next(); end != BreakIterator.DONE; end = breaker.next()) {
      list.add(text.substring(start, end));
      start = end;
    }
    return list;
  }

  void main() {

    Tail<Integer> list = Nil.instance();
    for (var i = 10; i > 0; i--) {
      list = Cons.of(i, list);
    }
    System.out.println("Original = " + list.toTailString());
    System.out.println("Somme = " + sum(list));
    System.out.println("Reverse = " + reverse(list).toTailString());
    System.out.println("Append 11 = " + append(list, 11).toTailString());

    var camus = "🔠 L'absurde, c'est la raison lucide qui constate ses limites. 🚀";
    Tail<String> graphemeTail = Nil.instance();
    for (var g : graphemes(camus)) graphemeTail = Cons.of(g, graphemeTail);
    System.out.println("Graphemes = " + reverse(graphemeTail).toTailString());
  }

}
