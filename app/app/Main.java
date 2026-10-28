package app;

import module java.base;
import cons.list.*;

public class Main {

  static Integer sum(GenericList<Integer> list) {
    return list.foldLeft(0, Integer::sum);
  }

  static GenericList<Integer> quicksort(GenericList<Integer> numbers) {
    return switch (numbers) {
      case Nil<Integer> _ -> GenericList.empty();
      case Cons(var pivot, var tail) -> {
        var petits = tail.filter(x -> x < pivot);
        var grands = tail.filter(x -> x >= pivot);
        yield quicksort(petits).append(new Cons<>(pivot, quicksort(grands)));
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

    GenericList<Integer> list = GenericList.empty();
    for (var i = 10; i > 0; i--) {
      list = new Cons<>(i, list);
    }
    System.out.println("Original    = " + list);
    System.out.println("Somme       = " + sum(list));
    System.out.println("Reverse     = " + list.rev());
    System.out.println("Append 11   = " + list.append(GenericList.of(11)));
    System.out.println("Filter odd  = " + list.filter(x -> x % 2 != 0));
    System.out.println("Quick sort  = " + quicksort(list));
    System.out.println("Quick sort  = " + quicksort(GenericList.of(5, 3, 2, 8, 7, 1, 9, 6, 4)));

    var camus = "🔠 L'absurde, c'est la raison lucide qui constate ses limites. 🚀";
    GenericList<String> graphemeList = GenericList.empty();
    for (var g : graphemes(camus)) graphemeList = new Cons<>(g, graphemeList);
    System.out.println("Graphemes   = " + graphemeList.rev());
  }
}
