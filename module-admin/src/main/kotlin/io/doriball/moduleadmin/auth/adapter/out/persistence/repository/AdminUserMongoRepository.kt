package io.doriball.moduleadmin.auth.adapter.out.persistence.repository

import io.doriball.moduleadmin.auth.adapter.out.persistence.entity.AdminUserDocument
import org.springframework.data.mongodb.repository.MongoRepository

interface AdminUserMongoRepository: MongoRepository<AdminUserDocument, String> {

    fun findByAdminUserName(username: String): AdminUserDocument?

}