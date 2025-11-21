package com.rubentrivino.franquiciasapi.application;

import com.rubentrivino.franquiciasapi.domain.Branch;
import com.rubentrivino.franquiciasapi.domain.Product;
import com.rubentrivino.franquiciasapi.infrastructure.repository.BranchRepository;
import com.rubentrivino.franquiciasapi.infrastructure.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final BranchRepository branchRepository;

    public Product addProduct(Long branchId, String name, int stock) {
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new RuntimeException("Branch not found"));

        Product p = new Product();
        p.setName(name);
        p.setStock(stock);
        p.setBranch(branch);

        return productRepository.save(p);
    }

    public void deleteProduct(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new RuntimeException("Product not found");
        }
        productRepository.deleteById(productId);
    }

    public Product updateStock(Long productId, int newStock) {
        Product p = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        p.setStock(newStock);
        return productRepository.save(p);
    }

    @Transactional(readOnly = true)
    public List<Product> findMaxStockByFranchise(Long franchiseId) {
        List<Branch> branches = branchRepository.findAll().stream()
                .filter(branch -> branch.getFranchise() != null)
                .filter(branch -> franchiseId.equals(branch.getFranchise().getId()))
                .collect(Collectors.toList());

        return branches.stream()
                .map(b -> {
                    List<Product> products = b.getProducts();
                    if (products == null || products.isEmpty()) {
                        return null;
                    }
                    return products.stream()
                            .max(Comparator.comparingInt(Product::getStock))
                            .orElse(null);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
