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

  static boolean isOdd(int x) {
    return x % 2 == 1;
  }

  static Predicate<Integer> greaterThan(int n) {
    return x -> x > n;
  }

  void main() {
    GenericList<Integer> list = GenericList.empty();
    for (var i = 15; i > 0; i--) {
      list = new Cons<>(i, list);
    }
    IO.println("Original    = " + list);
    IO.println("Somme       = " + sum(list));
    IO.println("Reverse     = " + list.rev());
    IO.println("Append 11   = " + list.append(GenericList.of(11)));
    Predicate<Integer> select = Main::isOdd;
    select = select.and(greaterThan(5));
    IO.println("Filter odd  = " + list.filter(select));
    IO.println("Quick sort  = " + quicksort(list));
    IO.println("Quick sort  = " + quicksort(GenericList.of(5, 3, 2, 8, 7, 1, 9, 6, 4)));

    var camus = "🔠 L'absurde, c'est la raison lucide qui constate ses limites. 🚀";
    GenericList<String> graphemeList = GenericList.empty();
    for (var g : graphemes(camus)) {
      graphemeList = new Cons<>(g, graphemeList);
    }
    IO.println("Graphemes   = " + graphemeList.rev());

    var small = GenericList.of(1, 2, 3);
    BiFunction<String, Integer, String> left = (acc, x) -> "(" + acc + " + " + x + ")";
    BiFunction<Integer, String, String> right = (x, acc) -> "(" + x + " + " + acc + ")";
    IO.println("Left iter   = " + small.foldLeft("0", left));
    IO.println("Left rec    = " + small.foldLeftRec("0", left));
    IO.println("Right iter  = " + small.foldRight("0", right));
    IO.println("Right rec   = " + small.foldRightRec("0", right));

    var big = GenericList.of(IntStream.rangeClosed(1, 1_000_000).boxed().toArray(Integer[]::new));
    IO.println("Big iter    = " + big.foldLeft(0L, (acc, x) -> acc + x));
    String recResult;
    try {
      recResult = String.valueOf(big.foldLeftRec(0L, (acc, x) -> acc + x));
    } catch (StackOverflowError e) {
      recResult = "StackOverflowError";
    }
    IO.println("Big rec     = " + recResult);
  }
}
