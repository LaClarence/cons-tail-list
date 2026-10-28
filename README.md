# OCaml-style Cons List in Java

Inspired by [Functional Data Structures and Pattern Matching in Scala](https://medium.com/codex/functional-data-structures-and-pattern-matching-in-scala-7a379092e1e4)

The `Main.java` files shows basic usage of the cons list implementation using Java 27.

The project is split into two modules: `cons.list` (the library) and `app` (the demo).

## Compiling

```bash
rm -rf out
javac --release 27 -d out --module-source-path . $(find cons.list app -name "*.java")
```

`--module-source-path .` compiles both modules in one pass, in dependency order, into `out/<module>/`.

Each module is written as an *exploded module* (a plain directory of `.class` files, not a jar):

```
out/
├── cons.list/
│   ├── module-info.class
│   └── cons/list/Cons.class, GenericList.class, Nil.class
└── app/
    ├── module-info.class
    └── app/Main.class
```

`java --module-path out` accepts these directories just like modular jars.

## Running

```bash
java --module-path out -m app/app.Main
```

or 

```bash
./run.sh
```

`run.sh` does the whole cycle:

1. Checks that both `java` and `javac` are version 27, otherwise prints an error and stops.
2. Deletes `out/` and compiles both modules into it (see [Compiling](#compiling)).
3. Runs `app/app.Main` from the module path.

## What the demo shows

`Main.java` prints:

```
Original    = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}
Somme       = 55
Reverse     = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1}
Append 11   = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11}
Filter odd  = {1, 3, 5, 7, 9}
Quick sort  = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}
Quick sort  = {1, 2, 3, 4, 5, 6, 7, 8, 9}
Graphemes   = {🔠,  , L, ', a, b, s, u, r, d, e, ,,  , c, ', e, s, t, ...,  , 🚀}
```

Line by line:

- **Original**: builds the list by consing `10`, `9`, … `1` onto `GenericList.empty()`, like `1 :: 2 :: … :: []` in OCaml.
- **Somme**: sums the elements with `foldLeft(0, Integer::sum)`.
- **Reverse**: `rev()`, itself a `foldLeft` that conses each element onto a new list.
- **Append 11**: `append(GenericList.of(11))` joins two lists, like OCaml's `@`.
- **Filter odd**: `filter` keeps the elements matching a predicate.
- **Quick sort**: a recursive quicksort written with a pattern-matching `switch`: `case Nil` for the empty list, `case Cons(var pivot, var tail)` to split head from tail, as in OCaml's `match … with [] -> … | pivot :: tail -> …`. It runs on the list above, then on the unsorted `GenericList.of(5, 3, 2, 8, 7, 1, 9, 6, 4)`.
- **Graphemes**: splits a quote from Camus into user-perceived characters with `BreakIterator` (so the emojis stay whole), conses them onto a `GenericList<String>`, which builds it backwards, then `rev()` restores the order.

The lists are immutable: every operation returns a new list, so `Reverse`, `Append 11`, `Filter odd` and the first `Quick sort` all start from the same original `{1, …, 10}`.
