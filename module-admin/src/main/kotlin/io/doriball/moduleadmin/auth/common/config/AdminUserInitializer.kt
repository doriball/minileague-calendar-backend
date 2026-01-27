package io.doriball.moduleadmin.auth.common.config

import io.doriball.moduleadmin.auth.adapter.out.persistence.entity.AdminUserDocument
import io.doriball.moduleadmin.auth.adapter.out.persistence.repository.AdminUserMongoRepository
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Profile("!test")
@Component
class AdminUserInitializer(
    private val repository: AdminUserMongoRepository,
    private val passwordEncoder: PasswordEncoder,
    @Value("\${doriball.management.admin_username}")
    private var adminUsername: String,
    @Value("\${doriball.management.admin_password}")
    private var adminUserPassword: String
) {

    @PostConstruct
    fun init() {
        if (repository.findByAdminUserName(adminUsername) == null) {
            repository.save(
                AdminUserDocument(
                    adminUserName = adminUsername,
                    adminPassword = passwordEncoder.encode(adminUserPassword)
                )
            )
        }
    }

}