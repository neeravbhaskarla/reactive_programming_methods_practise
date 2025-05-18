package com.learnreactiveprogramming.service;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class FluxAndMonoGeneratorService {
    List<String> names = List.of("alex", "ben", "chloe");

    public Flux<String> namesFlux() {
        return Flux.fromIterable(names).log();
    }

    public Flux<String> namesMap() {
        return Flux.fromIterable(names)
                .map(String::toUpperCase)
                .log();
    }

    public Flux<String> namesMapImmutable() {
        var flux = Flux.fromIterable(names).log();
        return flux.map(String::toUpperCase); // immutable (creates new Flux<String>)
    }

    public Flux<String> namesMapCustomChange() {
        var flux = Flux.fromIterable(names)
                .map(String::toUpperCase)
                .map(name -> name.length() + "-" + name)
                .log();
        return flux;
    }

    public Flux<String> namesFluxFilter() {
        var flux = Flux.fromIterable(names)
                .filter(name -> name.length() > 3)
                .log();
        return flux;
    }

    public Flux<String> namesFluxFlatMap() {
        var flux = Flux.fromIterable(names)
                .flatMap(this::flattenString)
                .log();
        return flux;
    }

    // If ordering does not matter
    public Flux<String> namesFluxFlatMapAsync() {
        var flux = Flux.fromIterable(names)
                .flatMap(this::flattenStringWithDelay)
                .log();
        return flux;
    }

    // If ordering does matter (but consumes more time)
    public Flux<String> namesFluxConcatMapAsync() {
        var flux = Flux.fromIterable(names)
                .concatMap(this::flattenStringWithDelay)
                .log();
        return flux;
    }

    public Flux<String> namesFluxTransform() {
        Function<Flux<String>, Flux<String>> transformToIndividualUpperCaseChars = name -> name
                .filter(word -> word.length() > 3).map(String::toUpperCase);

        var flux = Flux.fromIterable(names)
                .transform(transformToIndividualUpperCaseChars)
                .log();

        return flux;
    }

    public Flux<String> namesFluxDefaultIfEmpty() {
        Function<Flux<String>, Flux<String>> transformToIndividualUpperCaseChars = name -> name
                .filter(word -> word.length() > 13).map(String::toUpperCase);

        var flux = Flux.fromIterable(names)
                .transform(transformToIndividualUpperCaseChars)
                .defaultIfEmpty("default")
                .log();

        return flux;
    }

    public Flux<String> namesFluxSwitchIfEmpty() {
        Function<Flux<String>, Flux<String>> transformToIndividualUpperCaseChars = name -> name
                .filter(word -> word.length() > 13).map(String::toUpperCase);

        var defaultFlux = Flux.just("default").transform(name -> name.map(String::toUpperCase));

        var flux = Flux.fromIterable(names)
                .transform(transformToIndividualUpperCaseChars)
                .switchIfEmpty(defaultFlux)
                .log();

        return flux;
    }

    public Mono<String> nameMono() {
        return Mono.just("alex");
    }

    public Mono<List<String>> nameMonoFlatMap() {
        return Mono.just("alex")
                .map(String::toUpperCase)
                .flatMap(this::getSplitString);
    }

    // Hence, we have multiple outputs. Conversion List of Objects to Flux is
    // possible using flatMapMany() method
    public Flux<String> namedFluxFlatMapMany() {
        return Mono.just("alex")
                .map(String::toUpperCase)
                .flatMapMany(this::flattenString);
    }

    Flux<String> flattenString(String name) {
        var splitName = name.split("");
        return Flux.fromArray(splitName);
    }

    Mono<List<String>> getSplitString(String name) {
        return Mono.just(List.of(name.split("")));
    }

    Flux<String> flattenStringWithDelay(String name) {
        var splitName = name.split("");
        var randomDelay = new Random().nextInt(1000);
        return Flux.fromArray(splitName).delayElements(Duration.ofMillis(randomDelay));
    }

    Flux<String> fluxWithConcat() {
        var flux1 = Flux.just("a", "b", "c");
        var flux2 = Flux.just("d", "e", "f");
        return Flux.concat(flux1, flux2);
    }

    Flux<String> fluxWithInstanceConcat() {
        var flux1 = Flux.just("a", "b", "c");
        var flux2 = Flux.just("d", "e", "f");
        return flux1.concatWith(flux2);
    }

    Flux<String> fluxWithMonoAndFluxInstanceConcat() {
        var flux1 = Mono.just("a");
        var flux2 = Flux.just("d", "e", "f");
        return flux1.concatWith(flux2);
    }

    Flux<String> fluxWithMonoAndMonoInstanceConcat() {
        var flux1 = Mono.just("a");
        var flux2 = Mono.just("d");
        return flux1.concatWith(flux2);
    }

    Flux<String> fluxMerge() {
        var flux1 = Flux.just("a", "b", "c").delayElements(Duration.ofMillis(100));
        var flux2 = Flux.just("d", "e", "f").delayElements(Duration.ofMillis(150));
        return Flux.merge(flux1, flux2);
    }

    Flux<String> fluxMergePrecedencetoSecond() {
        var flux1 = Flux.just("a", "b", "c").delayElements(Duration.ofMillis(100));
        var flux2 = Flux.just("d", "e", "f").delayElements(Duration.ofMillis(90));
        return Flux.merge(flux1, flux2);
    }

    Flux<String> fluxMergeInstance() {
        var flux1 = Flux.just("a", "b", "c").delayElements(Duration.ofMillis(100));
        var flux2 = Flux.just("d", "e", "f").delayElements(Duration.ofMillis(150));
        return flux1.mergeWith(flux2);
    }

    Flux<String> fluxMergeInstanceMonoAndFlux() {
        var flux1 = Mono.just("a").delayElement(Duration.ofMillis(100));
        var flux2 = Flux.just("d", "e", "f").delayElements(Duration.ofMillis(200));
        return flux1.mergeWith(flux2);
    }

    Flux<String> fluxMergeInstanceMonoAndMono() {
        var mono1 = Mono.just("a").delayElement(Duration.ofMillis(100));
        var mono2 = Mono.just("d").delayElement(Duration.ofMillis(200));
        return mono1.mergeWith(mono2);
    }

    Flux<String> fluxMergeSequential() {
        var flux1 = Flux.just("a", "b", "c").delayElements(Duration.ofMillis(100));
        var flux2 = Flux.just("d", "e", "f").delayElements(Duration.ofMillis(200));
        return Flux.mergeSequential(flux1, flux2);
    }

    Flux<String> fluxZip() {
        var flux1 = Flux.just("a", "b", "c").delayElements(Duration.ofMillis(100));
        var flux2 = Flux.just("d", "e", "f").delayElements(Duration.ofMillis(200));
        return Flux.zip(flux1, flux2, (first, second) -> first + second);
    }

    Flux<String> fluxZipWithMap() {
        var flux1 = Flux.just("a", "b", "c");
        var flux2 = Flux.just("d", "e", "f");
        var flux3 = Flux.just("1", "2", "3");
        return Flux.zip(flux1, flux2, flux3).map(t4 -> t4.getT1() + t4.getT2() + t4.getT3());
    }

    Flux<String> fluxZipWithMapInstance() {
        var flux1 = Flux.just("a", "b", "c");
        var flux2 = Flux.just("d", "e", "f");
        return flux1.zipWith(flux2).map(t4 -> t4.getT1() + t4.getT2());
    }

    Mono<String> fluxZipMonoAndMono() {
        var mono1 = Mono.just("a");
        var mono2 = Mono.just("d");
        return Mono.zip(mono1, mono2, (first, second) -> first + second);
    }

    Mono<String> fluxZipWithMonoAndMonoInstance() {
        var mono1 = Mono.just("a");
        var mono2 = Mono.just("d");
        return mono1.zipWith(mono2).map(t2 -> t2.getT1() + t2.getT2());
    }

    Flux<String> fluxZipWithFluxAndMono(){
        var flux = Flux.just("b", "c", "d");
        var mono = Mono.just("a");
        return flux.zipWith(mono).map(t2 -> t2.getT1() + t2.getT2());
    }

    Flux<String> fluxZipWithFluxAndFluxWithLimitedInputs(){
        var flux1 = Flux.just("b", "c", "d");
        var flux2 = Flux.just("a", "b");
        return flux1.zipWith(flux2).map(t2 -> t2.getT1() + t2.getT2());
    }
}
