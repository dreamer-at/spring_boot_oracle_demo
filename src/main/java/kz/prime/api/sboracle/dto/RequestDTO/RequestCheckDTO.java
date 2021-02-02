package kz.prime.api.sboracle.dto.RequestDTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.sql.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestCheckDTO {
    private String requestID;
    private Date requestDate;
    private RequestParamDTO request;
}