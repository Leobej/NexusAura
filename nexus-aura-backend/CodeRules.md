Code Standards & Branch Naming Conventions
Code Standards
Backend (Kotlin - Spring Boot)
Formatting & Style
Follow Kotlin Coding Conventions (JetBrains Style Guide).

Use 4 spaces for indentation.

Naming conventions:

Classes & Objects: PascalCase → UserAccountService

Functions & Variables: camelCase() → getUserById(), userId

Constants: UPPERCASE_SNAKE_CASE → JWT_EXPIRATION_TIME

Packages: lowercase.with.dots → com.nexusaura.auth

DTOs & Entities: UserAccountDTO, UserAccountEntity

Best Practices
Use data classes for DTOs.

Prefer val over var.

Use coroutines for async operations.

Always handle null safety (?. and ?:).

