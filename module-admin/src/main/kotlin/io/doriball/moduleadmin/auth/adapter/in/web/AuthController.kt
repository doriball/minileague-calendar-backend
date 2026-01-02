package io.doriball.moduleadmin.auth.adapter.`in`.web

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class AuthController {

    @GetMapping("/login")
    fun login(): String {
        return "login"
    }

}