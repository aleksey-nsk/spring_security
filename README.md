# Info:
- Проект **Spring Security**.
- Простые примеры **аутентификации** и **авторизации** в Spring Security.

### Модуль example1_in_memory
- Пример **In-Memory аутентификации**.
- Настроил **авторизацию** для 2 пользователей (с ролями USER и ADMIN):  
Имеем 3 URL:  
`/` для всех пользователей (в том числе не аутентифицированных);  
`/user` для пользователей с ролью USER и ADMIN;  
`/admin` для пользователей с ролью ADMIN.  
Запускаю приложение. Пытаюсь открыть `localhost:8081/`:  
![](https://github.com/aleksey-nsk/spring_security/blob/master/screenshots/ex1_01_home.png)  
Затем пытаюсь открыть `localhost:8081/user` и попадаю на `localhost:8081/login` (форма логина):  
![](https://github.com/aleksey-nsk/spring_security/blob/master/screenshots/ex1_02_login.png)  
На форме логина ввожу user/user и попадаю на `localhost:8081/user`:  
![](https://github.com/aleksey-nsk/spring_security/blob/master/screenshots/ex1_03_user.png)  
Далее пытаюсь открыть `localhost:8081/admin` и получаю `403 Forbidden`:  
![](https://github.com/aleksey-nsk/spring_security/blob/master/screenshots/ex1_04_forbidden_403.png)  
Иду по адресу `localhost:8081/logout` и выполняю Log Out:  
![](https://github.com/aleksey-nsk/spring_security/blob/master/screenshots/ex1_05_logout.png)  
Далее опять пытаюсь открыть `localhost:8081/admin` и попадаю на форму логина. Ввожу admin/admin:  
![](https://github.com/aleksey-nsk/spring_security/blob/master/screenshots/ex1_06_login.png)  
В итоге попадаю на страницу `localhost:8081/admin`  
![](https://github.com/aleksey-nsk/spring_security/blob/master/screenshots/ex1_07_admin.png)

### Модуль example2_custom_login_form
- Снова **In-Memory аутентификация**.
- Создаю _кастомную форму логина_:  
Заменяю автоматически генерируемую форму ввода логина/пароля на _свою собственную на Thymeleaf_.  
Запускаю приложение. Пытаюсь открыть `localhost:8082/hello` и попадаю на `localhost:8082/login` (_кастомная форма логина_). Ввожу неверные логин/пароль и попадаю на `localhost:8082/login?error`:  
![](https://github.com/aleksey-nsk/spring_security/blob/master/screenshots/ex2_01_error.png)  
Далее ввожу u1/p1 и попадаю на `localhost:8082/hello`:  
![](https://github.com/aleksey-nsk/spring_security/blob/master/screenshots/ex2_02_hello.png)  
Затем набираю адрес `localhost:8082/login?logout` и вижу сообщение "You have been logged out":  
![](https://github.com/aleksey-nsk/spring_security/blob/master/screenshots/ex2_03_logout.png)  
Далее набираю адрес `localhost:8082/login`, ввожу u2/p2 и попадаю на `localhost:8082`:  
![](https://github.com/aleksey-nsk/spring_security/blob/master/screenshots/ex2_04_main.png)

### Модуль example3_custom_authentication
- **Пользовательская аутентификация** в Spring Security
- Реализовал **пользовательский UserDetailsService** - т.е. написал метод
loadUserByUsername(). По-сути тут всего лишь реализовал извлечение пользователя из БД.

### Модуль example4_custom_authentication_provider
- **Пользовательская аутентификация** в Spring Security
- Реализован **пользовательский AuthencationProvider**. Теперь, помимо извлечения пользователся из БД,
мы будем сами сравнивать пароли и формировать объект Authencation (либо выбрасывать исключение).
- Используется редко.

### Модуль example5_jdbc_auth
- Пример JDBC-аутентификации в Spring Security

### Модуль

### Модуль
