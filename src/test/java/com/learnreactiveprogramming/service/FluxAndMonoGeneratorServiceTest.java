package com.learnreactiveprogramming.service;

import java.util.List;

import org.junit.jupiter.api.Test;

import reactor.test.StepVerifier;

class FluxAndMonoGeneratorServiceTest {

    FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();

    @Test
    void namedFlux() {
        var namedFlux = fluxAndMonoGeneratorService.namesFlux();

        StepVerifier.create(namedFlux)
                .expectNext("alex")
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void namedFluxNamesMap() {
        var namedFluxMap = fluxAndMonoGeneratorService.namesMap();

        StepVerifier.create(namedFluxMap)
                .expectNext("ALEX", "BEN", "CHLOE")
                .verifyComplete();
    }

    @Test
    void namedFluxNamesMapImmuatble() {
        var namedFluxMap = fluxAndMonoGeneratorService.namesMapImmutable();

        StepVerifier.create(namedFluxMap)
                .expectNext("ALEX", "BEN", "CHLOE")
                .verifyComplete();
    }

    @Test
    void namedFluxNamesMapCustomChange() {
        var namedFluxMap = fluxAndMonoGeneratorService.namesMapCustomChange();

        StepVerifier.create(namedFluxMap)
                .expectNext("4-ALEX", "3-BEN", "5-CHLOE")
                .verifyComplete();
    }

    @Test
    void namedFluxMap() {
        var namedFluxMap = fluxAndMonoGeneratorService.namesMapCustomChange();

        StepVerifier.create(namedFluxMap)
                .expectNext("4-ALEX", "3-BEN", "5-CHLOE")
                .verifyComplete();
    }

    @Test
    void namesFluxFilter() {
        var namedFluxMap = fluxAndMonoGeneratorService.namesFluxFilter();

        StepVerifier.create(namedFluxMap)
                .expectNext("alex", "chloe")
                .verifyComplete();
    }

    @Test
    void namedFluxFlatMap() {
        var namedFluxMap = fluxAndMonoGeneratorService.namesFluxFlatMap();

        StepVerifier.create(namedFluxMap)
                .expectNextCount(12)
                .verifyComplete();
    }

    @Test
    void namedFluxFlatMapAsync() {
        var namedFluxMap = fluxAndMonoGeneratorService.namesFluxFlatMapAsync();

        StepVerifier.create(namedFluxMap)
                .expectNextCount(12)
                .verifyComplete();
    }

    @Test
    void namesFluxConcatMapAsync() {
        var namedFluxMap = fluxAndMonoGeneratorService.namesFluxConcatMapAsync();

        StepVerifier.create(namedFluxMap)
                .expectNextCount(12)
                .verifyComplete();
    }

    @Test
    void namesFluxTransform() {
        var namedFluxMap = fluxAndMonoGeneratorService.namesFluxTransform();

        StepVerifier.create(namedFluxMap)
                .expectNext("ALEX", "CHLOE")
                .verifyComplete();
    }

    @Test
    void namedMono() {
        var namedMono = fluxAndMonoGeneratorService.nameMono();

        StepVerifier.create(namedMono)
                .expectNext("alex")
                .verifyComplete();
    }

    @Test
    void namedMonoFlatMap() {
        var namedMono = fluxAndMonoGeneratorService.nameMonoFlatMap();

        StepVerifier.create(namedMono)
                .expectNext(List.of("A", "L", "E", "X"))
                .verifyComplete();
    }

    @Test
    void namedFluxFlatMapMany() {
        var namedMono = fluxAndMonoGeneratorService.namedFluxFlatMapMany();

        StepVerifier.create(namedMono)
                .expectNext("A", "L", "E", "X")
                .verifyComplete();
    }

    @Test
    void namesFluxDefaultIfEmpty() {
        var namedMono = fluxAndMonoGeneratorService.namesFluxDefaultIfEmpty();

        StepVerifier.create(namedMono)
                .expectNext("default")
                .verifyComplete();
    }

    @Test
    void namesFluxSwitchIfEmpty() {
        var namedMono = fluxAndMonoGeneratorService.namesFluxSwitchIfEmpty();

        StepVerifier.create(namedMono)
                .expectNext("DEFAULT")
                .verifyComplete();
    }

    @Test
    void fluxWithConcat() {
        var namedMono = fluxAndMonoGeneratorService.fluxWithConcat();

        StepVerifier.create(namedMono)
                .expectNext("a", "b", "c", "d", "e", "f")
                .verifyComplete();
    }

    @Test
    void fluxWithInstanceConcat() {
        var namedMono = fluxAndMonoGeneratorService.fluxWithInstanceConcat();

        StepVerifier.create(namedMono)
                .expectNext("a", "b", "c", "d", "e", "f")
                .verifyComplete();
    }

    @Test
    void fluxWithMonoAndFluxInstanceConcat() {
        var namedMono = fluxAndMonoGeneratorService.fluxWithMonoAndFluxInstanceConcat();

        StepVerifier.create(namedMono)
                .expectNext("a", "d", "e", "f")
                .verifyComplete();
    }

    @Test
    void fluxWithMonoAndMonoInstanceConcat() {
        var namedMono = fluxAndMonoGeneratorService.fluxWithMonoAndMonoInstanceConcat();

        StepVerifier.create(namedMono)
                .expectNext("a", "d")
                .verifyComplete();
    }

    @Test
    void fluxMerge() {
        var namedMono = fluxAndMonoGeneratorService.fluxMerge();

        StepVerifier.create(namedMono)
                .expectNext("a", "d", "b", "e", "c", "f")
                .verifyComplete();
    }

    @Test
    void fluxMergePrecedencetoSecond() {
        var namedMono = fluxAndMonoGeneratorService.fluxMergePrecedencetoSecond();

        StepVerifier.create(namedMono)
                .expectNext("d", "a", "e", "b", "f", "c")
                .verifyComplete();
    }

    @Test
    void fluxMergeInstance() {
        var namedMono = fluxAndMonoGeneratorService.fluxMergeInstance();

        StepVerifier.create(namedMono)
                .expectNext("a", "d", "b", "e", "c", "f")
                .verifyComplete();
    }

    @Test
    void fluxMergeInstanceMonoAndFlux() {
        var namedMono = fluxAndMonoGeneratorService.fluxMergeInstanceMonoAndFlux();

        StepVerifier.create(namedMono)
                .expectNext("a", "d", "e", "f")
                .verifyComplete();
    }

    @Test
    void fluxMergeInstanceMonoAndMono() {
        var namedMono = fluxAndMonoGeneratorService.fluxMergeInstanceMonoAndMono();

        StepVerifier.create(namedMono)
                .expectNext("a", "d")
                .verifyComplete();
    }
    
    @Test
    void fluxMergeSequential() {
        var namedMono = fluxAndMonoGeneratorService.fluxMergeSequential();

        StepVerifier.create(namedMono)
                .expectNext("a", "b", "c", "d", "e", "f")
                .verifyComplete();
    }
    
    @Test
    void fluxZip(){
        var mergedFlux = fluxAndMonoGeneratorService.fluxZip();

        StepVerifier.create(mergedFlux)
                .expectNext("ad", "be", "cf")
                .verifyComplete();
    }

    @Test
    void fluxZipWithMap(){
        var mergedFlux = fluxAndMonoGeneratorService.fluxZipWithMap();

        StepVerifier.create(mergedFlux)
                .expectNext("ad1", "be2", "cf3")
                .verifyComplete();
    }
    
    @Test
    void fluxZipWithMapInstance(){
        var mergedFlux = fluxAndMonoGeneratorService.fluxZipWithMapInstance();

        StepVerifier.create(mergedFlux)
                .expectNext("ad", "be", "cf")
                .verifyComplete();
    }

    @Test
    void fluxZipMonoAndMono(){
        var mergedFlux = fluxAndMonoGeneratorService.fluxZipMonoAndMono();

        StepVerifier.create(mergedFlux)
                .expectNext("ad")
                .verifyComplete();
    }
    
    @Test
    void fluxZipWithMonoAndMonoInstance(){
        var mergedFlux = fluxAndMonoGeneratorService.fluxZipWithMonoAndMonoInstance();

        StepVerifier.create(mergedFlux)
                .expectNext("ad")
                .verifyComplete();
    }

    @Test
    void fluxZipWithFluxAndMono(){
        var mergedFlux = fluxAndMonoGeneratorService.fluxZipWithFluxAndMono();

        StepVerifier.create(mergedFlux)
                .expectNext("ba")
                .verifyComplete();
    }

    @Test
    void fluxZipWithFluxAndFluxWithLimitedInputs(){
        var mergedFlux = fluxAndMonoGeneratorService.fluxZipWithFluxAndFluxWithLimitedInputs();

        StepVerifier.create(mergedFlux)
                .expectNext("ba", "cb")
                .verifyComplete();
    }
}