package io.doriball.moduleinfrastructure.persistence.entity

import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "match_record")
class MatchRecordDocument(): BaseTimeDocument() {
}