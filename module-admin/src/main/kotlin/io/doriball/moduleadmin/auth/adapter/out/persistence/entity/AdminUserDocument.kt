package io.doriball.moduleadmin.auth.adapter.out.persistence.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Document(collection = "admin_user")
class AdminUserDocument(
    val adminUserName: String,
    val adminPassword: String?
): UserDetails {

    @Id var id: String? = null

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return listOf()
    }

    override fun getPassword(): String? {
        return adminPassword
    }

    override fun getUsername(): String {
        return adminUserName
    }

}