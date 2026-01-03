package io.doriball.modulecalendar.operation.adapter.out.persistence.repository

import io.doriball.moduleinfrastructure.persistence.entity.NoticeDocument
import org.springframework.data.mongodb.repository.MongoRepository

interface NoticeMongoRepository : MongoRepository<NoticeDocument, String> {
}