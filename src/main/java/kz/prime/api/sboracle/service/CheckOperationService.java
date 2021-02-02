package kz.prime.api.sboracle.service;

import kz.prime.api.sboracle.dto.RequestDTO.RequestCheckDTO;
import kz.prime.api.sboracle.dto.ResponseDTO.ResponseAttributesDTO;
import kz.prime.api.sboracle.dto.ResponseDTO.ResponseDTO;
import kz.prime.api.sboracle.dto.ResponseDTO.ResponseDataDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.procedure.ProcedureOutputs;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckOperationService {
    private static final String CHECK_OPERATION = "TEST_OPERATIONS.TEST_INSERT_OPERATION";
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private final EntityManager em;

    public ResponseDTO checkOperation(final RequestCheckDTO reqCheck, final Integer operationId) throws ClassCastException {
        Objects.requireNonNull(reqCheck);
        Objects.requireNonNull(operationId);
        List<ResponseAttributesDTO> resList;
        Date date;
        String desc;
        String status;
        log.info("Starting CHECK_OPERATION procedure execution with operationID: '{}'", operationId);
        StoredProcedureQuery sp = em.createStoredProcedureQuery(CHECK_OPERATION)
                .registerStoredProcedureParameter("p_operation_id", Integer.class, ParameterMode.IN)
                .setParameter("p_operation_id", operationId)
                .registerStoredProcedureParameter("CheckDate", Date.class, ParameterMode.OUT)
                .registerStoredProcedureParameter("Description", String.class, ParameterMode.OUT)
                .registerStoredProcedureParameter("p_status", String.class, ParameterMode.OUT)
                .registerStoredProcedureParameter("members_results", ResponseAttributesDTO.class,
                        ParameterMode.REF_CURSOR);
        try {
            sp.execute();
            @SuppressWarnings("unchecked")
            List<Object[]> results = sp.getResultList();
            resList = results.stream().map(
                    (s) -> new ResponseAttributesDTO(
                    (BigDecimal) s[0],
                    (String) s[1],
                    (String) s[2],
                    (String) s[3],
                    (String) s[4],
                    (String) s[5],
                    (String) s[6],
                    (String) s[7],
                    (String) s[8],
                    (String) s[9]
                    )).collect(Collectors.toList());
            date = ((Date) sp.getOutputParameterValue("CheckDate"));
            desc = ((String) sp.getOutputParameterValue("Description"));
            status = ((String) sp.getOutputParameterValue("p_status"));
        } finally {
            sp.unwrap(ProcedureOutputs.class).release();
        }
        return ResponseDTO.builder()
                .requestId(reqCheck.getRequestID())
                .requestDate(reqCheck.getRequestDate())
                .responseDataDTO(
                        ResponseDataDTO.builder()
                                .checkDate(date)
                                .description(desc)
                                .status(status)
                                .responseAttributesDTO(resList)
                                .build())
                .build();
    }
}


