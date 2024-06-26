package woongjin.bi.backend.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import woongjin.bi.backend.util.ResponseCode;

@Getter
@RequiredArgsConstructor
public class ResourceNotFoundException extends RuntimeException {

    private final ResponseCode responseCode;
}