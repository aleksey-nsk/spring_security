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
Запускаю приложение. Пытаюсь открыть `localhost:8082/hello` и попадаю на `localhost:8082/login` (_кастомная форма логина_). 
Ввожу неверные логин/пароль и попадаю на `localhost:8082/login?error`:  
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
- Пример JDBC-аутентификации в Spring Security.
- В этом примере поменяется только один метод: configure(AuthenticationManagerBuilder auth),
и ещё добавится стартер для работы с БД через JDBC.
- Реализована JDBC-аутентификация со своими таблицами: таблицы используем не стандартные, а свои (так обычно и бывает). 
Поэтому указываем в настройках аутентификации свой SQL-запрос, с помощью которого Spring Security сможет извлечь 
пользователей с их правами из конкретно наших таблиц (или одной таблицы, если права хранятся с пользователями).

### Модуль example6_jwt
- Напишем REST API с использованием JWT-токена (без OAuth2).
- Для работы с JWT-токеном используем библиотеку JJWT (Java JWT: JSON Web Token for Java and Android).
- Этот пример - самописная реализация stateless REST API на JWT,
единое приложение которое и выдаёт, и проверяет токен. И выдаёт REST API.
- На этом примере можно увидеть, как написать свой фильтр авторизации, как сформировать 
и проверить JWT-токен. Но в реальном проекте этот пример лучше не использовать.
- В этом примере будет чистый REST-сервис без фронтенда. Подразумевается, что фронтенд 
написан отдельно: например, на каком-нибудь JavaScript-фрейворке.
- Мы добавим в приложение конечную точку /authenticate для аутентификации. Сюда приходят имя и пароль от пользователя. 
Приложение проверяет пароль, и если он верный, высылает пользователю в ответ JWT-токен. Во всех дальнейших запросах 
пользователь обязан высылать в заголовке JWT-токен, наше приложение проверяет подлинность токена 
в специально написанном фильтре JwtFilter и, если он корректен, пропускает запрос дальше.
- Аутентификация реализована с **пользовательским UserDetailsService**.
- Для отправки запросов будем использовать программу POSTMAN. Например, для «входа» 
с именем/паролем, и для получения JWT-токена. А также для запроса защищённых страниц.
- Проверка:  
Отправим POST-запрос нужного формата по адресу /authencate:  
![](https://github.com/aleksey-nsk/spring_security/blob/master/screenshots/ex6_01_create_jwt.png)  
Отправка запроса с JWT-токеном. Токен получен, теперь с ним можно попасть на защищённую страницу.
Для этого добавим его к запросу /user в заголовок Authorization. При этом выберем тип Bearer-токен — это значит, 
что префикс Bearer будет добавлен к токену. В итоге получим страницу /admin:  
![](https://github.com/aleksey-nsk/spring_security/blob/master/screenshots/ex6_02_request_with_jwt.png)  
Если декодировать токен на сайте https://jwt.io/, 
то в нём видны клеймы:  
sub (имя пользователя);  
authorities;  
exp — когда истекает токен;  
iat — когда выпущен токен:
![](https://github.com/aleksey-nsk/spring_security/blob/master/screenshots/ex6_03_jwt_decoded.png)  

### Модуль example7_oauth2
- Пример OAuth2 авторизации.
- Для OAuth2 аутентификации используем дополнительную зависимость `spring-security-oauth2-autoconfigure`.
- Для настройки переходим по адресу https://console.cloud.google.com и открываю APIs & Services:  
![](https://github.com/aleksey-nsk/spring_security/blob/master/screenshots/ex7_01_open_google.png)  
Далее создаём проект:
![](https://github.com/aleksey-nsk/spring_security/blob/master/screenshots/ex7_02_create_project.png)  
Редактируем информацию о приложении:  
![](https://github.com/aleksey-nsk/spring_security/blob/master/screenshots/ex7_03_app_information.png)  
Создаём учётные данные:  
![](https://github.com/aleksey-nsk/spring_security/blob/master/screenshots/ex7_04_create_oauth_client_id.png)  
Указываем необходимые параметры:  
![](https://github.com/aleksey-nsk/spring_security/blob/master/screenshots/ex7_05_type_name_uris.png)  
Полученные в итоге Client ID и Client Secret вставляем в файл application.yaml:  
![](https://github.com/aleksey-nsk/spring_security/blob/master/screenshots/ex7_06_oauth_client_created.png)  
Далее запускаем приложение и открываем страницу http://localhost:8087 (сюда есть доступ у неавторизованных юзеров):  
![](https://github.com/aleksey-nsk/spring_security/blob/master/screenshots/ex7_07_root.png)  
Далее пытаемся открыть http://localhost:8087/user и попадаем на Гугловую форму аутентификации:  
![](https://github.com/aleksey-nsk/spring_security/blob/master/screenshots/ex7_08_user.png)  
После аутентификации попадаем на нужный адрес:  
![](https://github.com/aleksey-nsk/spring_security/blob/master/screenshots/ex7_09_user_ok.png)  
При этом в консоли видим, что от Google пришли параметры юзера: id, username, userpic, email, gender, locale:  
![](https://github.com/aleksey-nsk/spring_security/blob/master/screenshots/ex7_10_user_info_log.png)  

### Модуль example8_angularjs
- Используем Spring Boot + AngularJS
- Чтобы CSRF работала с AngularJS необходима настройка `.and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())`
- Для преобразования паролей по алгоритму bcrypt используем сайт https://www.browserling.com/tools/bcrypt
- Запускаем приложение. При попытке открыть localhost:8088 попадаем на страницу логина localhost:8088/login  
![](https://github.com/aleksey-nsk/spring_security/blob/master/screenshots/ex8_01_login_page.png)  
Вводим логин/пароль и попадаем на страницу localhost:8088    
![](https://github.com/aleksey-nsk/spring_security/blob/master/screenshots/ex8_02_main_page.png)  
При нажатии на ссылку "Выйти" попадаем на страницу:  
![](https://github.com/aleksey-nsk/spring_security/blob/master/screenshots/ex8_03_logout.png) 
