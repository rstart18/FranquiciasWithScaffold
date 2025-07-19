package co.com.bancolombia.api.dto.branchProduct;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BranchProductResponse {
    private String id;
    private String productId;
    private String branchId;
    private int stock;
}
