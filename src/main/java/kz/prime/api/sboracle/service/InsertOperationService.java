package kz.prime.api.sboracle.service;

import kz.prime.api.sboracle.dto.RequestDTO.RequestCheckDTO;
import kz.prime.api.sboracle.dto.ResponseDTO.InsOpResDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.sql.Date;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class InsertOperationService {
    private static final String INSERT_OPERATION = "TEST_OPERATIONS.TEST_INSERT_OPERATION";
    private final EntityManager em;

    public InsOpResDTO insertOperation(final RequestCheckDTO reqCheck) {
        Objects.requireNonNull(reqCheck);
        log.info("Starting INSERT_OPERATION procedure execution with requestID: '{}'", reqCheck.getRequestID());
        StoredProcedureQuery sp = em.createStoredProcedureQuery(INSERT_OPERATION)
                .registerStoredProcedureParameter("p_uid", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_systemid", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_username", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_operationdate", Date.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_check_type", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_operation_id", Integer.class, ParameterMode.OUT)
                .registerStoredProcedureParameter("p_result_code", Integer.class, ParameterMode.OUT)
                .registerStoredProcedureParameter("p_result_message", String.class, ParameterMode.OUT)
                .setParameter("p_uid", reqCheck.getRequestID())
                .setParameter("p_systemid", reqCheck.getRequest().getSourceSystem())
                .setParameter("p_username", reqCheck.getRequest().getAuthor().getUserName())
                .setParameter("p_operationdate", reqCheck.getRequestDate())
                .setParameter("p_check_type", reqCheck.getRequest().getCheckType());
        sp.execute();
        return InsOpResDTO.builder()
                .operationId((Integer) sp.getOutputParameterValue("p_operation_id"))
                .resCode((Integer) sp.getOutputParameterValue("p_operation_id"))
                .resMessage((String) sp.getOutputParameterValue("p_result_message"))
                .build();
    }
}
