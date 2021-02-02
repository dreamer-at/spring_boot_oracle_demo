package kz.prime.api.sboracle.dto.ResponseDTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseAttributesDTO {
    private BigDecimal percent;
    private String checked;
    private String matched;
    private String dictCommentDTO;
    private String iin_bin;
    private String fullName;
    private String birthDate;
    private String regCountry;
    private String countryCitizenship;
    private String checkList;
}