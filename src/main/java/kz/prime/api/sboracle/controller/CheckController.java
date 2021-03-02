package kz.prime.api.sboracle.controller;

import kz.prime.api.sboracle.dto.RequestDTO.RequestCheckDTO;
import kz.prime.api.sboracle.dto.ResponseDTO.ResponseDTO;
import kz.prime.api.sboracle.service.CompleteProcService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/check/", consumes = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CheckController {
    private final CompleteProcService completeProcService;

    /**
     * Responds allowed http methods in the header.
     * <p>
     * This always responds with HTTP-Code 200
     */
    @RequestMapping(method = RequestMethod.OPTIONS)
    ResponseEntity<?> collectionOptions() {
        return ResponseEntity
                .ok()
                .allow(HttpMethod.GET, HttpMethod.POST, HttpMethod.OPTIONS)
                .build();
    }

    /**
     * Responds allowed http methods the header.
     * <p>
     * This always responds with HTTP-Code 200
     */
    @RequestMapping(value = "{id}", method = RequestMethod.OPTIONS)
    ResponseEntity<?> singularOptions(@SuppressWarnings("unused") @PathVariable(required = false) UUID id) {
        return ResponseEntity
                .ok()
                .allow(HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.OPTIONS)
                .build();
    }

    @PostMapping(path = "/full/")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO fullCheck(@RequestBody RequestCheckDTO requestCheckDTO) throws RuntimeException {
        return completeProcService.completeAllProcedures(requestCheckDTO);
    }
}
