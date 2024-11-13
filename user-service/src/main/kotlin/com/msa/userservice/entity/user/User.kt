package com.msa.userservice.entity.user

import com.msa.userservice.audit.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.Id

@Entity
data class User(
    var email: String,
    var username: String,
    var password: String,
): BaseEntity(){
    @Id @GeneratedValue(strategy = IDENTITY)
    var id: Long? = null
}
