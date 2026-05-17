# OCaml-style Cons/Tail List in Java

Inspired by [Functional Data Structures and Pattern Matching in Scala](https://medium.com/codex/functional-data-structures-and-pattern-matching-in-scala-7a379092e1e4)

The `Main.java` files shows basic usage of the cons/tail list implementation using Java 25+.

The project is split into two modules: `cons.tail` (the library) and `app` (the demo).

## Compiling 

```bash
rm -rf out 
mkdir out
javac -d out/cons.tail cons.tail/**/*.java
javac --module-path out -d out/app/**/*.java
```

## Running

```bash
java --module-path out -m app/app.Main
```

or 

```bash
./run.sh
```
