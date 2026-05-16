# OCaml-style Cons/Tail List in Java


Inspired by [Functional Data Structures and Pattern Matching in Scala](https://medium.com/codex/functional-data-structures-and-pattern-matching-in-scala-7a379092e1e4)
and my old CAML Light courses presented by Thérèse Hardin.

## References

- Thérèse Accart Hardin, Véronique Donzeau-Gouge —
  *Concepts et Outils de Programmation : le style fonctionnel, le style impératif avec Caml et Ada*
  (InterEditions, 1991)

## Proposal

`Main.java` demonstrates basic usage of the cons/tail list implementation using Java 25+.

The project is split into two modules: `cons.tail` (the library) and `app` (the demo).

### Compile

```bash
javac -d out/cons.tail \
  cons.tail/module-info.java \
  cons.tail/cons/tail/Cons.java \
  cons.tail/cons/tail/Nil.java \
  cons.tail/cons/tail/Tail.java

javac --module-path out -d out/app \
  app/module-info.java \
  app/app/Main.java
```

### Run

```bash
java --module-path out -m app/app.Main
```

or 

```bash
./run.sh
```
