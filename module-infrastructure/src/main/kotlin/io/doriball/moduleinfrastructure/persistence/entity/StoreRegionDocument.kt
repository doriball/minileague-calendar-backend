package io.doriball.moduleinfrastructure.persistence.entity

import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "store_region")
class StoreRegionDocument(): BaseTimeDocument() {
}