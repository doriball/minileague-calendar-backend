package io.doriball.modulecore.domain.match

import io.doriball.modulecore.domain.enums.ResultType

class MatchRecordStageRound(
    val roundNo: Int,
    val opponentDeckIconIds: MutableList<Int>,
    val results: MutableList<ResultType>
) {

    val roundMatchResult: ResultType
        get() {
            if (results.contains(ResultType.ID)) return ResultType.ID

            val wcount = results.count { it == ResultType.W }
            val lcount = results.count { it == ResultType.L }

            return when {
                wcount == lcount -> ResultType.D
                wcount > lcount -> ResultType.W
                else -> ResultType.L
            }
        }

}