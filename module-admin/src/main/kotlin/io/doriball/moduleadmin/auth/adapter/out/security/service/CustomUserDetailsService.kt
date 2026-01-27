package io.doriball.moduleadmin.auth.adapter.out.security.service

import io.doriball.moduleadmin.auth.adapter.out.persistence.repository.AdminUserMongoRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(private val repository: AdminUserMongoRepository): UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        return repository.findByAdminUserName(username) ?: throw UsernameNotFoundException("User not found")
    }

}