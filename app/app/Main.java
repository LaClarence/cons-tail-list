package app;

import module java.base;
import cons.tail.*;

public class Main {

  static Integer sum(Tail<Integer> list) {
    return list.reduce(Integer::sum, 0);
  }

  static Tail<Integer> quicksort(Tail<Integer> numbers) {
    return switch (numbers) {
      case Nil _ -> Nil.instance();
      case Cons(var pivot, var tail) -> {
        var petits = tail.filter(x -> x < pivot);
        var grands = tail.filter(x -> x >= pivot);
        yield quicksort(petits).concat(Cons.of(pivot, quicksort(grands)));
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
    System.out.println("Reverse     = " + list.reverse().toTailString());
    System.out.println("Append 11   = " + list.append(11).toTailString());
    System.out.println("Filter odd  = " + list.filter(x -> x % 2 != 0).toTailString());
    System.out.println("Quick sort  = " + quicksort(list).toTailString());
    System.out.println("Quick sort  = " + quicksort(Cons.of(5, 3, 2, 8, 7, 1, 9, 6, 4)).toTailString());

    var camus = "🔠 L'absurde, c'est la raison lucide qui constate ses limites. 🚀";
    Tail<String> graphemeTail = Nil.instance();
    for (var g : graphemes(camus)) graphemeTail = Cons.of(g, graphemeTail);
    System.out.println("Graphemes   = " + graphemeTail.reverse().toTailString());
  }
}
