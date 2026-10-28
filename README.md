# OCaml-style Cons/Tail List in Java

Inspired by [Functional Data Structures and Pattern Matching in Scala](https://medium.com/codex/functional-data-structures-and-pattern-matching-in-scala-7a379092e1e4)

The `Main.java` files shows basic usage of the cons/tail list implementation using Java 27.

The project is split into two modules: `cons.tail` (the library) and `app` (the demo).

## Compiling

```bash
rm -rf out
javac --release 27 -d out --module-source-path . $(find cons.tail app -name "*.java")
```

`--module-source-path .` compiles both modules in one pass, in dependency order, into `out/<module>/`.

Each module is written as an *exploded module* (a plain directory of `.class` files, not a jar):

```
out/
├── cons.tail/
│   ├── module-info.class
│   └── cons/tail/Cons.class, Nil.class, Tail.class
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
