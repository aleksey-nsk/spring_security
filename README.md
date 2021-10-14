# Info:
- Проект **Spring Security**

#### Модуль example1_in_memory
- In-Memory аутентификация
URL = localhost:8081/api/v1/hello
Происходит перенаправление на страницу localhost:8081/login
(страница с формой логина/пароля).
Страница входа нах-ся в классе
org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter
Spring Security создаёт пользователя по умолчанию
(user, пароль генерится).
Также создаётся страница чтобы разлогиниться
localhost:8081/logout
**Form-Based аутентификация**.
**In-Memory аутентификация**
