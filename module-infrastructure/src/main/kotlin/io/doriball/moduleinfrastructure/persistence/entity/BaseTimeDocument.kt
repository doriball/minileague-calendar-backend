package io.doriball.moduleinfrastructure.persistence.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

abstract class BaseTimeDocument {
    @CreatedDate
    var createdAt: LocalDateTime? = null
        protected set
    @LastModifiedDate
    var modifiedAt: LocalDateTime? = null
        protected set

}