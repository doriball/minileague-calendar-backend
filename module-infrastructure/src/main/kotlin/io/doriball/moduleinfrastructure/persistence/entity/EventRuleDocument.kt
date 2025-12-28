package io.doriball.moduleinfrastructure.persistence.entity

import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "event_rule")
class EventRuleDocument(): BaseTimeDocument() {
}