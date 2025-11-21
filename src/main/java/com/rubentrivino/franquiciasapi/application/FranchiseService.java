package com.rubentrivino.franquiciasapi.application;

import com.rubentrivino.franquiciasapi.domain.Branch;
import com.rubentrivino.franquiciasapi.domain.Franchise;
import com.rubentrivino.franquiciasapi.infrastructure.repository.BranchRepository;
import com.rubentrivino.franquiciasapi.infrastructure.repository.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FranchiseService {

    private final FranchiseRepository franchiseRepository;
    private final BranchRepository branchRepository;

    public Franchise createFranchise(String name) {
        Franchise f = new Franchise();
        f.setName(name);
        return franchiseRepository.save(f);
    }

    public Branch addBranch(Long franchiseId, String branchName) {
        Franchise f = franchiseRepository.findById(franchiseId)
                .orElseThrow(() -> new RuntimeException("Franchise not found"));

        Branch b = new Branch();
        b.setName(branchName);
        b.setFranchise(f);

        return branchRepository.save(b);
    }

    public List<Branch> getBranches(Long franchiseId) {
        Franchise f = franchiseRepository.findById(franchiseId)
                .orElseThrow(() -> new RuntimeException("Franchise not found"));
        return f.getBranches();
    }
}
