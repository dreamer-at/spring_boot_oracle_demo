package kz.prime.api.sboracle.dto.ResponseDTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseDataDTO implements Serializable {
    private String description;
    private String status;
    private Date checkDate;
    private List<ResponseAttributesDTO> responseAttributesDTO;
}
