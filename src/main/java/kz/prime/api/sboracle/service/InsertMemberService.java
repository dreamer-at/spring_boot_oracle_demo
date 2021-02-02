package kz.prime.api.sboracle.service;

import kz.prime.api.sboracle.dto.RequestDTO.RequestCheckDTO;
import kz.prime.api.sboracle.dto.ResponseDTO.MemOpResDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.procedure.ProcedureOutputs;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class InsertMemberService {
    private static final String INSERT_MEMBER = "TEST_OPERATIONS.TEST_INSERT_OPERATION";
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private final EntityManager em;

    public MemOpResDTO insertMember(final RequestCheckDTO reqCheck, final Integer operationId) {
        Objects.requireNonNull(reqCheck);
        Objects.requireNonNull(operationId);
        log.info("Starting INSERT_MEMBER procedure execution with operationID: '{}'", operationId);
        StoredProcedureQuery sp = em.createStoredProcedureQuery(INSERT_MEMBER);
        Integer memberId;
        Integer resCode;
        String resMessage;
        reqCheck.getRequest().getAttributes().forEach(s -> sp
                .registerStoredProcedureParameter("p_operation_id", Integer.class, ParameterMode.IN)
                .setParameter("p_operation_id", operationId)
                .registerStoredProcedureParameter("p_name", String.class, ParameterMode.IN)
                .setParameter("p_name", s.getFullName())
                .registerStoredProcedureParameter("p_subdate", Date.class, ParameterMode.IN)
                .setParameter("p_subdate", s.getBirthDate())
                .registerStoredProcedureParameter("p_subcountry", String.class, ParameterMode.IN)
                .setParameter("p_subcountry", s.getRegCountry())
                .registerStoredProcedureParameter("p_rescountry", String.class, ParameterMode.IN)
                .setParameter("p_rescountry", s.getCountryCitizenship())
                .registerStoredProcedureParameter("p_clientid", String.class, ParameterMode.IN)
                .setParameter("p_clientid", s.getIin_bin())
                .registerStoredProcedureParameter("p_docnumber", String.class, ParameterMode.IN)
                .setParameter("p_docnumber", s.getNumberDocument())
                .registerStoredProcedureParameter("p_clienttype", Integer.class, ParameterMode.IN)
                .setParameter("p_clienttype", s.getClientType())
                .registerStoredProcedureParameter("p_member_id", Integer.class, ParameterMode.OUT)
                .registerStoredProcedureParameter("p_result_code", Integer.class, ParameterMode.OUT)
                .registerStoredProcedureParameter("p_result_message", String.class, ParameterMode.OUT).execute());
        try {
            memberId = (Integer) sp.getOutputParameterValue("p_member_id");
            resCode = (Integer) sp.getOutputParameterValue("p_result_code");
            resMessage = (String) sp.getOutputParameterValue("p_result_message");
        } finally {
            sp.unwrap(ProcedureOutputs.class).release();
        }
        return MemOpResDTO.builder()
                .memberId(memberId)
                .resCode(resCode)
                .resMessage(resMessage)
                .build();
    }
}
