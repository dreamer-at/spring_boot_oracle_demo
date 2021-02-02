package kz.prime.api.sboracle.dto.RequestDTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.sql.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AttributesDTO {
      private String fullName;
      private int clientType;
      private Date birthDate;
      private String numberDocument;
      private String iin_bin;
      private String regCountry;
      private String countryCitizenship;
}
