package com.rubentrivino.franquiciasapi.infrastructure.controller;

import com.rubentrivino.franquiciasapi.application.FranchiseService;
import com.rubentrivino.franquiciasapi.application.ProductService;
import com.rubentrivino.franquiciasapi.domain.Branch;
import com.rubentrivino.franquiciasapi.domain.Franchise;
import com.rubentrivino.franquiciasapi.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FranchiseController {

    private final FranchiseService franchiseService;
    private final ProductService productService;


    @PostMapping("/franchises")
    public Mono<Franchise> createFranchise(@RequestBody Map<String, String> body) {
        String name = body.get("name");
        return Mono.fromCallable(() -> franchiseService.createFranchise(name))
                .subscribeOn(Schedulers.boundedElastic());
    }


    @PostMapping("/franchises/{franchiseId}/branches")
    public Mono<Branch> addBranch(@PathVariable Long franchiseId,
                                  @RequestBody Map<String, String> body) {
        String name = body.get("name");
        return Mono.fromCallable(() -> franchiseService.addBranch(franchiseId, name))
                .subscribeOn(Schedulers.boundedElastic());
    }


    @GetMapping("/franchises/{franchiseId}/branches")
    public Flux<Branch> getBranches(@PathVariable Long franchiseId) {
        return Mono.fromCallable(() -> franchiseService.getBranches(franchiseId))
                .flatMapMany(Flux::fromIterable)
                .subscribeOn(Schedulers.boundedElastic());
    }


    @PostMapping("/branches/{branchId}/products")
    public Mono<Product> addProduct(@PathVariable Long branchId,
                                    @RequestBody Map<String, Object> body) {
        String name = (String) body.get("name");
        int stock = (int) body.get("stock");
        return Mono.fromCallable(() -> productService.addProduct(branchId, name, stock))
                .subscribeOn(Schedulers.boundedElastic());
    }


    @DeleteMapping("/products/{productId}")
    public Mono<Void> deleteProduct(@PathVariable Long productId) {
        return Mono.fromRunnable(() -> productService.deleteProduct(productId))
                .subscribeOn(Schedulers.boundedElastic())
                .then();
    }


    @PutMapping("/products/{productId}/stock")
    public Mono<Product> updateStock(@PathVariable Long productId,
                                     @RequestBody Map<String, Integer> body) {
        int stock = body.get("stock");
        return Mono.fromCallable(() -> productService.updateStock(productId, stock))
                .subscribeOn(Schedulers.boundedElastic());
    }


    @GetMapping("/franchises/{franchiseId}/products/max-stock")
    public Flux<Product> getMaxStockProducts(@PathVariable Long franchiseId) {
        return Mono.fromCallable(() -> productService.findMaxStockByFranchise(franchiseId))
                .flatMapMany(Flux::fromIterable)
                .subscribeOn(Schedulers.boundedElastic());
    }
}
