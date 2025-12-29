package io.doriball.modulecore.domain.match

import io.doriball.modulecore.enums.ResultType
import io.doriball.modulecore.enums.StageType

class MatchRecordStage(
    val stageNo: Int,
    val type: StageType,
    val rounds: MutableList<MatchRecordStageRound>,
) {

    val stageMatchResult: MatchResult
        get() {
            val roundResults = rounds.map { it.roundMatchResult }
            return MatchResult(
                wins = roundResults.count { it == ResultType.W },
                losses = roundResults.count { it == ResultType.L },
                draws = roundResults.count { it == ResultType.D || it == ResultType.ID }
            )
        }

}