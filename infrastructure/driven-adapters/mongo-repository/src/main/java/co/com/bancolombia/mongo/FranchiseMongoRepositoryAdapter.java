package co.com.bancolombia.mongo;

import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.franchise.gateways.FranchiseRepository;
import co.com.bancolombia.mongo.document.FranchiseDocument;
import co.com.bancolombia.mongo.helper.AdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository
public class FranchiseMongoRepositoryAdapter extends AdapterOperations<Franchise, FranchiseDocument, String, FranchiseMongoDBRepository>
    implements FranchiseRepository
{

    public FranchiseMongoRepositoryAdapter(FranchiseMongoDBRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Franchise.class));
    }
}
