package co.com.bancolombia.api.dto.franchise;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FranchiseResponse {
    private String id;
    private String name;
    private String nit;
}
