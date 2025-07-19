package co.com.bancolombia.mongo.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Document(collection = "branch-products")
public class BranchProductDocument {
    @Id
    private String id;
    private String branchId;
    private String productId;
    private int stock;
    private LocalDateTime deletedAt;
}
