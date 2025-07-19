package co.com.bancolombia.model.branchproduct;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BranchProductWithInfo {
    private String branchId;
    private String branchName;
    private String productId;
    private String productName;
    private int stock;
}
