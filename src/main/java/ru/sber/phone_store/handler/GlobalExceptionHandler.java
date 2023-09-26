package ru.sber.phone_store.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.sber.phone_store.dto.ErrorResponseDto;
import ru.sber.phone_store.exception.DataValidationException;
import ru.sber.phone_store.exception.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Глобальный обработчик исключений для обработки и возврата стандартных ошибок HTTP.
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Обрабатывает исключение {@link MethodArgumentNotValidException}, которое возникает при невалидных аргументах метода.
     *
     * @param e Исключение типа {@link MethodArgumentNotValidException}.
     * @return Map с сообщениями об ошибках валидации аргументов.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("Method argument validation exception occurred", e);
        return e.getBindingResult().getAllErrors().stream()
                .collect(Collectors.toMap(
                        error -> ((FieldError) error).getField(),
                        error -> Objects.requireNonNullElse(error.getDefaultMessage(), ""))
                );
    }

    /**
     * Обрабатывает исключение {@link DataValidationException}, которое возникает при неверных данных.
     *
     * @param e Исключение типа {@link DataValidationException}.
     * @return Объект {@link ErrorResponseDto} с сообщением об ошибке валидации данных.
     */
    @ExceptionHandler(DataValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleDataValidationException(DataValidationException e) {
        log.error("Data validation exception occurred", e);
        return ErrorResponseDto.builder()
                .timestamp(LocalDateTime.now())
                .message(e.getMessage())
                .build();
    }

    /**
     * Обрабатывает исключение {@link EntityNotFoundException}, которое возникает при отсутствии сущности.
     *
     * @param e Исключение типа {@link EntityNotFoundException}.
     * @return Объект {@link ErrorResponseDto} с сообщением об ошибке отсутствия сущности.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDto handleEntityNotFoundException(EntityNotFoundException e) {
        log.error("Entity not found exception occurred", e);
        return ErrorResponseDto.builder()
                .timestamp(LocalDateTime.now())
                .message(e.getMessage())
                .build();
    }

    /**
     * Обрабатывает общие исключения типа {@link Exception} и {@link RuntimeException}.
     *
     * @param e Исключение типа {@link Exception} или {@link RuntimeException}.
     * @return Объект {@link ErrorResponseDto} с сообщением об ошибке сервера.
     */
    @ExceptionHandler(value = {Exception.class, RuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDto handleException(Exception e) {
        log.error("Exception occurred", e);
        return ErrorResponseDto.builder()
                .timestamp(LocalDateTime.now())
                .message("Internal server error")
                .build();
    }
}
