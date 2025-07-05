package com.nexus.aura.backend.nexus_aura_backend.config

import com.nexus.aura.backend.nexus_aura_backend.repository.UserRepository
import com.nexus.aura.backend.nexus_aura_backend.service.strategy.EmailLookupStrategy
import com.nexus.aura.backend.nexus_aura_backend.service.strategy.UserLookupStrategy
import com.nexus.aura.backend.nexus_aura_backend.service.strategy.UsernameLookupStrategy
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserLookupStrategyConfig {
    @Bean
    fun emailLookupStrategy(userRepository: UserRepository): UserLookupStrategy =
        EmailLookupStrategy(userRepository)

    @Bean
    fun usernameLookupStrategy(userRepository: UserRepository): UserLookupStrategy =
        UsernameLookupStrategy(userRepository)
}

