package co.com.bancolombia.mongo;

import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.model.branch.gateways.BranchRepository;
import co.com.bancolombia.mongo.document.BranchDocument;
import co.com.bancolombia.mongo.helper.AdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public class BranchMongoRepositoryAdapter extends AdapterOperations<Branch, BranchDocument, String, BranchMongoDBRepository>
    implements BranchRepository
{

    public BranchMongoRepositoryAdapter(BranchMongoDBRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Branch.class));
    }

    @Override
    public Flux<Branch> findByFranchiseId(String franchiseId) {
        return repository.findAll()
            .filter(branch -> franchiseId.equals(branch.getFranchiseId()))
            .map(branchDocument -> mapper.map(branchDocument, Branch.class));
    }
}
