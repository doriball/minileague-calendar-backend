package io.doriball.moduleadmin.operation.adapter.out.persistence.repository

import io.doriball.moduleinfrastructure.persistence.entity.AnnouncementDocument
import org.springframework.data.mongodb.repository.MongoRepository

interface AnnouncementMongoRepository: MongoRepository<AnnouncementDocument, String> {
}