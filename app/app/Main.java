package app;

import module java.base;
import cons.tail.*;

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

  static <T> Tail<T> filter(Tail<T> list, Predicate<T> op, Tail<T> filtered) {
    return switch (list) {
      case Nil _ -> filtered;
      case Cons(var head, var tail) -> filter(tail, op,
          op.test(head) ? append(filtered, head) : filtered);
    };
  }

  static <T> Tail<T> concat(Tail<T> a, Tail<T> b) {
    return switch (a) {
      case Nil _ -> b;
      case Cons(var head, var tail) -> Cons.of(head, concat(tail, b));
    };
  }

  static Tail<Integer> quicksort(Tail<Integer> numbers) {
    return switch (numbers) {
      case Nil _ -> Nil.instance();
      case Cons(var pivot, var tail) -> {
        var petits = filter(tail, x -> x < pivot, Nil.instance());
        var grands = filter(tail, x -> x >= pivot, Nil.instance());
        yield concat(quicksort(petits), Cons.of(pivot, quicksort(grands)));
      }
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

  public static void main(String[] args) {

    Tail<Integer> list = Nil.instance();
    for (var i = 10; i > 0; i--) {
      list = Cons.of(i, list);
    }
    System.out.println("Original    = " + list.toTailString());
    System.out.println("Somme       = " + sum(list));
    System.out.println("Reverse     = " + reverse(list).toTailString());
    System.out.println("Append 11   = " + append(list, 11).toTailString());
    System.out.println("Filter odd  = " + filter(list, x -> x % 2 != 0, Nil.instance()).toTailString());
    System.out.println("Quick sort  = " + quicksort(list).toTailString());
    System.out.println("Quick sort  = " + quicksort(Cons.of(5, 3, 2, 8, 7, 1, 9, 6, 4)).toTailString());

    var camus = "🔠 L'absurde, c'est la raison lucide qui constate ses limites. 🚀";
    Tail<String> graphemeTail = Nil.instance();
    for (var g : graphemes(camus)) graphemeTail = Cons.of(g, graphemeTail);
    System.out.println("Graphemes   = " + reverse(graphemeTail).toTailString());
  }
}
