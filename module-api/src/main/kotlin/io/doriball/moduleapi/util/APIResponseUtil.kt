package io.doriball.moduleapi.util

import io.doriball.moduleapi.enums.ResponseCode
import io.doriball.moduleapi.response.*

object APIResponseUtil {

    fun successResponse(): CommonResponse =
        CommonResponse(code = ResponseCode.SUCCESS.code, success = true)

    fun failResponse(code: String, message: String?): FailResponse = FailResponse(
        code = code,
        message = message,
    )

    fun failResponse(code: String, message: String?, detail: Any): FailResponse = FailResponse(
        code = code,
        message = message,
        detail = detail,
    )

    fun <T> singeResultResponse(data: T): SingleResultResponse<T> = SingleResultResponse(
        code = ResponseCode.SUCCESS.code,
        success = true,
        data = data,
    )

    fun <T> listResultResponse(contents: List<T>): ListResultResponse<T> = ListResultResponse(
        code = ResponseCode.SUCCESS.code,
        success = true,
        data = contents,
    )

    fun <T> paginationResultResponse(
        contents: List<T>,
        page: Int,
        pageSize: Int,
        totalPages: Long,
        totalSize: Long
    ): PaginationResultResponse<T> = PaginationResultResponse(
        code = ResponseCode.SUCCESS.code,
        success = true,
        contents = contents,
        page = page,
        pageSize = pageSize,
        totalPages = totalPages,
        totalSize = totalSize,
    )

    fun <T> infinityScrollResultResponse(contents: List<T>, page: Int, pageSize: Int): InfinityScrollResultResponse<T> =
        InfinityScrollResultResponse(
            code = ResponseCode.SUCCESS.code,
            success = true,
            contents = contents,
            page = page,
            pageSize = pageSize,
        )

}