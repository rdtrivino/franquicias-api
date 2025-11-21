package com.rubentrivino.franquiciasapi.application;

import com.rubentrivino.franquiciasapi.domain.Franchise;
import com.rubentrivino.franquiciasapi.infrastructure.repository.BranchRepository;
import com.rubentrivino.franquiciasapi.infrastructure.repository.FranchiseRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

class FranchiseServiceTest {

    @Test
    void createFranchise_shouldReturnFranchise() {
        FranchiseRepository repo = Mockito.mock(FranchiseRepository.class);
        BranchRepository branchRepo = Mockito.mock(BranchRepository.class);
        FranchiseService service = new FranchiseService(repo, branchRepo);

        Mockito.when(repo.save(Mockito.any(Franchise.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        Franchise f = service.createFranchise("Test Franchise");

        assertThat(f.getName()).isEqualTo("Test Franchise");
    }
}
