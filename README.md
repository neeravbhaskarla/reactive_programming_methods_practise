# Full Notes: `FluxAndMonoGeneratorService`

---

## ✅ Basic Flux Operations

---

### 📌 Method: `namesFlux()`

**Description:** Emits the list of names as a `Flux` and logs emissions.
**APIs used:** `Flux.fromIterable()`, `log()`

```java
public Flux<String> namesFlux() {
    return Flux.fromIterable(names).log();
}
```

---

### 📌 Method: `namesMap()`

**Description:** Converts each name to uppercase.
**APIs used:** `map()`

```java
public Flux<String> namesMap() {
    return Flux.fromIterable(names)
            .map(String::toUpperCase)
            .log();
}
```

---

### 📌 Method: `namesMapImmutable()`

**Description:** Demonstrates immutability — creates a new mapped `Flux`.
**APIs used:** `map()`

```java
public Flux<String> namesMapImmutable() {
    var flux = Flux.fromIterable(names).log();
    return flux.map(String::toUpperCase);
}
```

---

### 📌 Method: `namesMapCustomChange()`

**Description:** Converts to uppercase, then prefixes length to each name.
**APIs used:** `map()`

```java
public Flux<String> namesMapCustomChange() {
    var flux = Flux.fromIterable(names)
            .map(String::toUpperCase)
            .map(name -> name.length() + "-" + name)
            .log();
    return flux;
}
```

---

### 📌 Method: `namesFluxFilter()`

**Description:** Filters names where length > 3.
**APIs used:** `filter()`

```java
public Flux<String> namesFluxFilter() {
    var flux = Flux.fromIterable(names)
            .filter(name -> name.length() > 3)
            .log();
    return flux;
}
```

---

## ✅ FlatMap / ConcatMap Operations

---

### 📌 Method: `namesFluxFlatMap()`

**Description:** Flattens each name to characters (no guaranteed order).
**APIs used:** `flatMap()`

```java
public Flux<String> namesFluxFlatMap() {
    var flux = Flux.fromIterable(names)
            .flatMap(this::flattenString)
            .log();
    return flux;
}
```

---

### 📌 Method: `namesFluxFlatMapAsync()`

**Description:** Same as `flatMap()` but with delay.
**APIs used:** `flatMap()`

```java
public Flux<String> namesFluxFlatMapAsync() {
    var flux = Flux.fromIterable(names)
            .flatMap(this::flattenStringWithDelay)
            .log();
    return flux;
}
```

---

### 📌 Method: `namesFluxConcatMapAsync()`

**Description:** Flattens while preserving order (consumes more time).
**APIs used:** `concatMap()`

```java
public Flux<String> namesFluxConcatMapAsync() {
    var flux = Flux.fromIterable(names)
            .concatMap(this::flattenStringWithDelay)
            .log();
    return flux;
}
```

---

## ✅ Transform & Fallback Operations

---

### 📌 Method: `namesFluxTransform()`

**Description:** Applies reusable filtering + uppercase transformation via `transform()`.
**APIs used:** `transform()`, `filter()`, `map()`

```java
public Flux<String> namesFluxTransform() {
    Function<Flux<String>, Flux<String>> transformToIndividualUpperCaseChars = name ->
            name.filter(word -> word.length() > 3)
                .map(String::toUpperCase);

    var flux = Flux.fromIterable(names)
            .transform(transformToIndividualUpperCaseChars)
            .log();

    return flux;
}
```

---

### 📌 Method: `namesFluxDefaultIfEmpty()`

**Description:** Provides default value if no elements pass the filter.
**APIs used:** `transform()`, `defaultIfEmpty()`

```java
public Flux<String> namesFluxDefaultIfEmpty() {
    Function<Flux<String>, Flux<String>> transformToIndividualUpperCaseChars = name ->
            name.filter(word -> word.length() > 13)
                .map(String::toUpperCase);

    var flux = Flux.fromIterable(names)
            .transform(transformToIndividualUpperCaseChars)
            .defaultIfEmpty("default")
            .log();

    return flux;
}
```

---

### 📌 Method: `namesFluxSwitchIfEmpty()`

**Description:** Switches to another Flux if source is empty.
**APIs used:** `transform()`, `switchIfEmpty()`

```java
public Flux<String> namesFluxSwitchIfEmpty() {
    Function<Flux<String>, Flux<String>> transformToIndividualUpperCaseChars = name ->
            name.filter(word -> word.length() > 13)
                .map(String::toUpperCase);

    var defaultFlux = Flux.just("default").transform(name -> name.map(String::toUpperCase));

    var flux = Flux.fromIterable(names)
            .transform(transformToIndividualUpperCaseChars)
            .switchIfEmpty(defaultFlux)
            .log();

    return flux;
}
```

---

## ✅ Mono Operations

---

### 📌 Method: `nameMono()`

**Description:** Emits a single value "alex".
**APIs used:** `Mono.just()`

```java
public Mono<String> nameMono() {
    return Mono.just("alex");
}
```

---