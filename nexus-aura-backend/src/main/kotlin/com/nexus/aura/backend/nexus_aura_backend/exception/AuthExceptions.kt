package com.nexus.aura.backend.nexus_aura_backend.exception

class InvalidCredentialsException(message: String) : RuntimeException(message)
class EmailNotVerifiedException(message: String) : RuntimeException(message)
class AccountLockedException(message: String) : RuntimeException(message)
class PasswordTooWeakException(message: String) : RuntimeException(message)
class OperationNotAllowedException(message: String) : RuntimeException(message)
class TokenAlreadyUsedException(message: String) : RuntimeException(message)
class TokenNotFoundException(message: String) : RuntimeException(message)
class UserAlreadyVerifiedException(message: String) : RuntimeException(message)
class UserDisabledException(message: String) : RuntimeException(message)

