package kz.prime.api.sboracle.dto.RequestDTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdatedByDTO {
    private String userName;
}
