# active-kondo

`clj-kondo` configuration and hooks for our libraries:

- `active.clojure.monad` and `active.clojure.record` (part of
  [active-clojure](https://github.com/active-group/active-clojure))
- [`reacl`](https://github.com/active-group/reacl)

## Usage

Check out to `~/$HOME/.clj-kondo` or to `$XDG_CONFIG_HOME` like
`~/.config/clj-kondo` for newer versions (or any of the other possible ways to
configure `clj-kondo`, see
[here](https://github.com/clj-kondo/clj-kondo/blob/master/doc/config.md#introduction))
and lint your code with `clj-kondo`.

Also, make sure you use a somewhat recent version of
[`clj-kondo`](https://github.com/clj-kondo/clj-kondo).  The oldest version this
config has been confirmed to work with is

    $ clj-kondo --version
    clj-kondo v2020.10.10

The is also [this issue concerning usage of this
configuration](https://github.com/active-group/active-kondo/issues/1).
