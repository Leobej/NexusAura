package com.nexus.aura.backend.nexus_aura_backend.exception

class UserAlreadyExistsException(message: String) : RuntimeException(message)
class UsernameAlreadyTakenException(message: String) : RuntimeException(message)
class UserNotFoundException(message: String) : RuntimeException(message)

