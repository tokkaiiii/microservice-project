package com.msa.userservice.entity.user

import com.msa.userservice.audit.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.Id

@Entity
class User(
    @Column(nullable = false, unique = true, length = 50)
    var email: String = "",

    @Column(nullable = false, unique = true, length = 50)
    var userId: String = "",

    @Column(nullable = false, length = 50)
    var username: String = "",

    @Column(nullable = false, unique = true,length = 100)
    var password: String = "",

): BaseEntity(){
    @Id @GeneratedValue(strategy = IDENTITY)
    var id: Long? = null
}
