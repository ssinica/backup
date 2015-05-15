package synitex.backup.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import synitex.backup.rest.exception.ItemNotFoundException;
import synitex.backup.rest.exception.RestError;

import javax.servlet.http.HttpServletRequest;

@Component
public class RestExceptionsHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ItemNotFoundException.class)
    public @ResponseBody RestError handleItemNotFoundException(HttpServletRequest req, Exception ex) {
        return new RestError(ex.getMessage());
    }

}
