package co.com.bancolombia.api.dto.branchProduct;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BranchProductRequest {
    private String productId;
    private String branchId;
    private int stock;
}
