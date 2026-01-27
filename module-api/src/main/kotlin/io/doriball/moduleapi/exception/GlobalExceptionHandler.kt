package io.doriball.moduleapi.exception

import io.doriball.moduleapi.enums.ResponseCode
import io.doriball.moduleapi.response.FailResponse
import io.doriball.moduleapi.util.APIResponseUtil
import io.doriball.modulecore.shared.exception.BadRequestException
import io.doriball.modulecore.shared.exception.NotFoundException
import io.doriball.modulecore.shared.notification.model.ErrorEvent
import io.doriball.modulecore.shared.notification.port.ErrorMessageSender
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.ConversionNotSupportedException
import org.springframework.beans.TypeMismatchException
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.http.converter.HttpMessageNotWritableException
import org.springframework.validation.method.MethodValidationException
import org.springframework.web.HttpMediaTypeNotAcceptableException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingPathVariableException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.ServletRequestBindingException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.context.request.async.AsyncRequestTimeoutException
import org.springframework.web.method.annotation.HandlerMethodValidationException
import org.springframework.web.multipart.MaxUploadSizeExceededException
import org.springframework.web.multipart.support.MissingServletRequestPartException
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@Profile("!test")
@RestControllerAdvice
class GlobalExceptionHandler(
    private val errorMessageSender: ErrorMessageSender,
    @Value("\${spring.application.name:unknown}") private val moduleName: String,
    @Value("\${spring.profiles.active:default}") private val profile: String,
) : ResponseEntityExceptionHandler() {

    /**
     * 이하 Spring WEB MVC 관련 예외
     */

    override fun handleHttpRequestMethodNotSupported(
        ex: HttpRequestMethodNotSupportedException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? = ResponseEntity
        .status(status)
        .body(
            APIResponseUtil
                .failResponse(
                    ResponseCode.REQUEST_METHOD_NOT_SUPPORTED.code,
                    ResponseCode.REQUEST_METHOD_NOT_SUPPORTED.message
                )
        )

    override fun handleHttpMediaTypeNotSupported(
        ex: HttpMediaTypeNotSupportedException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? = ResponseEntity
        .status(status)
        .body(
            APIResponseUtil
                .failResponse(
                    ResponseCode.MEDIA_TYPE_NOT_SUPPORTED.code,
                    ResponseCode.MEDIA_TYPE_NOT_SUPPORTED.message
                )
        )

    override fun handleHttpMediaTypeNotAcceptable(
        ex: HttpMediaTypeNotAcceptableException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? = ResponseEntity
        .status(status)
        .body(
            APIResponseUtil
                .failResponse(
                    ResponseCode.MEDIA_TYPE_NOT_ACCEPTABLE.code,
                    ResponseCode.MEDIA_TYPE_NOT_ACCEPTABLE.message
                )
        )

    override fun handleMissingPathVariable(
        ex: MissingPathVariableException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? = ResponseEntity
        .status(status)
        .body(
            APIResponseUtil
                .failResponse(
                    ResponseCode.MISSING_PATH_VARIABLE.code,
                    ResponseCode.MISSING_PATH_VARIABLE.message
                )
        )

    override fun handleMissingServletRequestParameter(
        ex: MissingServletRequestParameterException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? = ResponseEntity
        .status(status)
        .body(
            APIResponseUtil
                .failResponse(
                    ResponseCode.MISSING_SERVLET_REQUEST_PARAMETER.code,
                    ResponseCode.MISSING_SERVLET_REQUEST_PARAMETER.message
                )
        )

    override fun handleMissingServletRequestPart(
        ex: MissingServletRequestPartException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? = ResponseEntity
        .status(status)
        .body(
            APIResponseUtil
                .failResponse(
                    ResponseCode.MISSING_SERVLET_REQUEST_PART.code,
                    ResponseCode.MISSING_SERVLET_REQUEST_PART.message
                )
        )

    override fun handleServletRequestBindingException(
        ex: ServletRequestBindingException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? = ResponseEntity
        .status(status)
        .body(
            APIResponseUtil
                .failResponse(
                    ResponseCode.SERVER_REQUEST_BINDING_FAILURE.code,
                    ResponseCode.SERVER_REQUEST_BINDING_FAILURE.message
                )
        )

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        val errors = ex.bindingResult.fieldErrors.map { it.defaultMessage ?: "" }
        return ResponseEntity
            .status(status)
            .body(
                APIResponseUtil
                    .failResponse(
                        ResponseCode.REQUEST_METHOD_NOT_SUPPORTED.code,
                        errors.joinToString(",")
                    )
            )
    }

    override fun handleHandlerMethodValidationException(
        ex: HandlerMethodValidationException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? = ResponseEntity
        .status(status)
        .body(
            APIResponseUtil
                .failResponse(
                    ResponseCode.METHOD_ARGUMENT_NOT_VALID.code,
                    ResponseCode.METHOD_ARGUMENT_NOT_VALID.message
                )
        )

    override fun handleNoHandlerFoundException(
        ex: NoHandlerFoundException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? = ResponseEntity
        .status(status)
        .body(
            APIResponseUtil
                .failResponse(
                    ResponseCode.NO_HANDLER_FOUND.code,
                    ResponseCode.NO_HANDLER_FOUND.message
                )
        )

    override fun handleAsyncRequestTimeoutException(
        ex: AsyncRequestTimeoutException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? = ResponseEntity
        .status(status)
        .body(
            APIResponseUtil
                .failResponse(
                    ResponseCode.ASYNC_REQUEST_TIMEOUT.code,
                    ResponseCode.ASYNC_REQUEST_TIMEOUT.message
                )
        )

    override fun handleMaxUploadSizeExceededException(
        ex: MaxUploadSizeExceededException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> = ResponseEntity
        .status(status)
        .body(
            APIResponseUtil
                .failResponse(
                    ResponseCode.MAX_UPLOAD_SIZE_EXCEEDED.code,
                    ResponseCode.MAX_UPLOAD_SIZE_EXCEEDED.message
                )
        )

    override fun handleConversionNotSupported(
        ex: ConversionNotSupportedException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? = ResponseEntity
        .status(status)
        .body(
            APIResponseUtil
                .failResponse(
                    ResponseCode.CONVERSION_NOT_SUPPORTED.code,
                    ResponseCode.CONVERSION_NOT_SUPPORTED.message
                )
        )

    override fun handleTypeMismatch(
        ex: TypeMismatchException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? = ResponseEntity
        .status(status)
        .body(
            APIResponseUtil
                .failResponse(
                    ResponseCode.TYPE_MISMATCH.code,
                    ResponseCode.TYPE_MISMATCH.message
                )
        )

    override fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? = ResponseEntity
        .status(status)
        .body(
            APIResponseUtil
                .failResponse(
                    ResponseCode.HTTP_MESSAGE_NOT_READABLE.code,
                    ResponseCode.HTTP_MESSAGE_NOT_READABLE.message
                )
        )

    override fun handleHttpMessageNotWritable(
        ex: HttpMessageNotWritableException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? = ResponseEntity
        .status(status)
        .body(
            APIResponseUtil
                .failResponse(
                    ResponseCode.HTTP_MESSAGE_NOT_WRITABLE.code,
                    ResponseCode.HTTP_MESSAGE_NOT_WRITABLE.message
                )
        )

    override fun handleMethodValidationException(
        ex: MethodValidationException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any>? = ResponseEntity
        .status(status)
        .body(
            APIResponseUtil
                .failResponse(
                    ResponseCode.METHOD_ARGUMENT_NOT_VALID.code,
                    ResponseCode.METHOD_ARGUMENT_NOT_VALID.message
                )
        )


    /**
     * 공통 예외
     */

    @ExceptionHandler(NotFoundException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleNotFoundException(request: HttpServletRequest, ex: NotFoundException): FailResponse =
        APIResponseUtil.failResponse(
            ResponseCode.COMMON_RESOURCE_NOT_FOUND.code,
            ResponseCode.COMMON_RESOURCE_NOT_FOUND.message
        )

    @ExceptionHandler(BadRequestException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBadRequestException(request: HttpServletRequest, ex: BadRequestException): FailResponse =
        APIResponseUtil.failResponse(
            ResponseCode.COMMON_INVALID_REQUEST.code,
            ex.message ?: ResponseCode.COMMON_INVALID_REQUEST.message,
        )

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleException(request: HttpServletRequest, ex: Exception): FailResponse {
        errorMessageSender.sendErrorMessage(
            ErrorEvent(
                moduleName = moduleName,
                profile = profile,
                remoteAddress = request.remoteAddr,
                requestUrl = request.requestURL.toString(),
                exceptionName = ex.javaClass.simpleName,
                exceptionMessage = ex.message ?: "",
                exceptionStackTrace = ex.stackTraceToString()
            )
        )
        return APIResponseUtil.failResponse(
            ResponseCode.INTERNAL_SERVER_ERROR.code,
            ResponseCode.INTERNAL_SERVER_ERROR.message,
        )
    }

}