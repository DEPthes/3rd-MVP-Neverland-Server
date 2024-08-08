package depth.mvp.thinkerbell.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MapperException extends RuntimeException{
    private final ErrorCode errorCode;
}
