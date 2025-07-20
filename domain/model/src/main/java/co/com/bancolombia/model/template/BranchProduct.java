package co.com.bancolombia.model.template;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
//import lombok.NoArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class BranchProduct {
    private String id;
    private String branchId;
    private String productId;
    private int stock;
    private LocalDateTime deletedAt;
}
