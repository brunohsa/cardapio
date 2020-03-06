package br.com.unip.cardapio.security.dto

import org.springframework.security.core.AuthenticatedPrincipal
import org.springframework.security.core.authority.SimpleGrantedAuthority

class AuthenticatedUser : AuthenticatedPrincipal {

    var id: Long
    var username: String
    var password: String
    var roles: Set<SimpleGrantedAuthority>

    constructor(id: Long, username: String, password: String, roles: Set<SimpleGrantedAuthority>) {
        this.id = id
        this.username = username
        this.password = password
        this.roles = roles
    }

    override fun getName(): String {
        return username
    }
}