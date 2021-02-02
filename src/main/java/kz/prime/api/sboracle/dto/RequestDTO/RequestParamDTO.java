package kz.prime.api.sboracle.dto.RequestDTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestParamDTO {
    // private String id;
    private Integer sourceSystem;
    private String checkType;
    //private List<String> checkLists;
    private List<AttributesDTO> attributes;
    private AuthorDTO author;
    //private UpdatedByDTO updatedBy;
}
