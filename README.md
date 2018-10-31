# scramblies-challenge

Scramble challenge

## Prerequisites

You will need [Leiningen][] 2.0.0 or above installed.

[leiningen]: https://github.com/technomancy/leiningen

## Configuration

You need to have a file named `config.edn` at project's top level with following contents
```Clojure
{:server {:port <port-number>}}
```

## Develop

Development is done with Emacs + CIDER. 

When you're on a clj buffer <kbd>M-x cider-jack-in-clj&cljs</kbd>

When prompted for clojurescript repl type, provide `figwheel`

When figwheel repl has finished compiling clojurescript hit `(go)` on the clojure repl to start the system.

Hit `(reset)` when you have completed changes to reload changed files and restart the system.

Use the sass watcher plugin of leiningen to watch and automatically reload scss files located on `resources/public/sass` directory with `lein sass auto`

## Test

Use `lein test` to run tests

## Build

`lein uberjar`

Make sure to have the config.edn file on the same level with the produced uberjar that you want to run. 


## License

Copyright © 2018 FIXME
