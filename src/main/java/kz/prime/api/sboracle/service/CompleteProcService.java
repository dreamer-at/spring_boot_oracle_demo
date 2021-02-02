package kz.prime.api.sboracle.service;

import kz.prime.api.sboracle.dto.RequestDTO.RequestCheckDTO;
import kz.prime.api.sboracle.dto.ResponseDTO.InsOpResDTO;
import kz.prime.api.sboracle.dto.ResponseDTO.MemOpResDTO;
import kz.prime.api.sboracle.dto.ResponseDTO.ResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CompleteProcService {
    private final InsertOperationService insertOperationService;
    private final InsertMemberService insertMemberService;
    private final CheckOperationService checkOperationService;

    public ResponseDTO completeAllProcedures(final RequestCheckDTO reqCheck) throws HibernateException {
        Objects.requireNonNull(reqCheck);
        log.info("Starting PROCEDURES execution with requestID: '{}', checkType: '{}', attributes: '{}'",
                reqCheck.getRequestID(), reqCheck.getRequest().getCheckType(), reqCheck.getRequest().getAttributes());
        ResponseDTO responseDTO = new ResponseDTO();
        InsOpResDTO resDTO = insertOperationService.insertOperation(reqCheck);
        try {
            if (resDTO.getOperationId() != null) {
                MemOpResDTO memOpResDTO = insertMemberService.insertMember(reqCheck,
                        resDTO.getOperationId());
                if (memOpResDTO.getMemberId() != null) {
                    responseDTO = checkOperationService.checkOperation(reqCheck,
                            resDTO.getOperationId());
                }
            }
        } catch (IllegalArgumentException ex) {
            log.error("Error by executing PROCEDURES: requestID: '{}', date: '{}'", reqCheck.getRequestID(),
                    reqCheck.getRequestDate());
            ex.printStackTrace();
        }
        log.info("Completed ALL PROCEDURES with requestID: '{}', responseDataDTO: '{}'",
                reqCheck.getRequestID(), responseDTO.getResponseDataDTO());
        return responseDTO;
    }
}