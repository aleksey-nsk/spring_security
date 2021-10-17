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

Задать своего пользователя в In-Memory аутентификации можно
в файле application.yaml

Для настройки In-Memory аутентификации в коде необходимо создать 
класс, который расширяет класс **WebSecurityConfigurerAdapter**,
и сделать его бином с помощью **@EnableWebSecurity**.

Затем надо переопределить метод **configure(AuthenticationManagerBuilder auth)** 
класса WebSecurityConfigurerAdapter, и уже с помощью билдера
AuthenticationManagerBuilder настроить AuthenticationManager (который
выполняет аутентификацию).

В бине PasswordEncoder задаётся, как шифровать пароль. 
Мы задали NoOpPasswordEncoder, который не делает ничего — оставляет пароль 
в первоначальном виде. Конечно, в реальном приложении NoOpPasswordEncoder 
не пригоден — пароль нужно шифровать, например, с помощью BCryptPasswordEncoder.

ДАЛЕЕ АВТОРИЗАЦИЯ

Но для того, чтобы настроить права (для двух пользователей), 
дополним контроллер еще парой методов. Итак, пусть будут две роли USER и ADMIN, 
а также три URL:  

/               
для всех пользователей (в том числе не аутентифицированных)  

/user           
для пользователей с ролью USER и ADMIN  

/admin          
для пользователей с ролью ADMIN  

Настроил авторизацию.
См. на скриншотах.
![](https://github.com/aleksey-nsk/spring_security/blob/master/screenshots/ex1_01_home.png)
![](https://github.com/aleksey-nsk/spring_security/blob/master/screenshots/ex1_02_login.png)
![](https://github.com/aleksey-nsk/spring_security/blob/master/screenshots/ex1_03_user.png)
![](https://github.com/aleksey-nsk/spring_security/blob/master/screenshots/ex1_04_forbidden_403.png)
![](https://github.com/aleksey-nsk/spring_security/blob/master/screenshots/ex1_05_logout.png)
![](https://github.com/aleksey-nsk/spring_security/blob/master/screenshots/ex1_06_login.png)
![](https://github.com/aleksey-nsk/spring_security/blob/master/screenshots/ex1_07_admin.png)

- Custom Login Form
![](https://github.com/aleksey-nsk/spring_security/blob/master/screenshots/ex2_01_error.png)
![](https://github.com/aleksey-nsk/spring_security/blob/master/screenshots/ex2_02_hello.png)
![](https://github.com/aleksey-nsk/spring_security/blob/master/screenshots/ex2_03_logout.png)
![](https://github.com/aleksey-nsk/spring_security/blob/master/screenshots/ex2_04_main.png)
