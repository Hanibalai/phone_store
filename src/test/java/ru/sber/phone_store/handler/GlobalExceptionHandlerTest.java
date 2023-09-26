package ru.sber.phone_store.handler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import ru.sber.phone_store.dto.ErrorResponseDto;
import ru.sber.phone_store.exception.DataValidationException;
import ru.sber.phone_store.exception.EntityNotFoundException;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {
    @Spy
    private GlobalExceptionHandler handler;

    @Test
    void testHandleMethodArgumentNotValidException() {
        Method method = new Object() {
        }.getClass().getEnclosingMethod();

        MethodParameter parameter = Mockito.mock(MethodParameter.class);
        when(parameter.getExecutable()).thenReturn(method);

        FieldError error = Mockito.mock(FieldError.class);
        when(error.getField()).thenReturn("field");
        when(error.getDefaultMessage()).thenReturn("Field error message");

        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        Mockito.when(bindingResult.getAllErrors()).thenReturn(List.of(error));

        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(parameter,
                bindingResult);

        Map<String, String> errorResponseMap = handler.handleMethodArgumentNotValidException(exception);

        assertAll(() -> {
            assertEquals(1, errorResponseMap.size());
            assertEquals("field", errorResponseMap.keySet().stream().findFirst().orElse(""));
            assertEquals("Field error message", errorResponseMap.get("field"));
        });
    }


    @Test
    void testHandleDataValidationException() {
        DataValidationException exception = mock(DataValidationException.class);
        when(exception.getMessage()).thenReturn("Data validation exception");

        ErrorResponseDto response = handler.handleDataValidationException(exception);

        assertEquals("Data validation exception", response.getMessage());
    }

    @Test
    void testHandleEntityNotFoundException() {
        EntityNotFoundException exception = mock(EntityNotFoundException.class);
        when(exception.getMessage()).thenReturn("Entity not found exception");

        ErrorResponseDto response = handler.handleEntityNotFoundException(exception);

        assertEquals("Entity not found exception", response.getMessage());
    }

    @Test
    void testHandleException() {
        Exception exception = mock(Exception.class);
        when(exception.getMessage()).thenReturn("Internal server error");

        ErrorResponseDto response = handler.handleException(exception);

        assertEquals("Internal server error", response.getMessage());
    }
}