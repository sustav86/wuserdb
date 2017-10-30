
# Apache Wicket Users CRUD Project

Stack of technologies:

1. Apache Wicket 8
2. Spring JDBC
3. PostrgreSQL
4. TomCat

Development environment:

1. IDE: Eclipse

# Screens

LogInPage:

![Image alt](https://github.com/sustav86/wuserdb/raw/master/projectPages/LogIn.png)

SingUpPage:

![Image alt](https://github.com/sustav86/wuserdb/raw/master/projectPages/SingUp.png)

List of Users:

![Image alt](https://github.com/sustav86/wuserdb/raw/master/projectPages/UsersList.png)

Edit Panel:

![Image alt](https://github.com/sustav86/wuserdb/raw/master/projectPages/UsersListWithPanel.png)

## Files of initialization

1. src/main/resources/initDB.sql - for init Tables
2. src/main/resources/populateDB.sql - add date

## PostrgeyDB properties

- database.init=true
- database.driverClassName=org.postgresql.Driver
- database.url=jdbc:postgresql://localhost:5432/wickusers
- database.user=postgres
- database.password=postgres

## Init population DB

--user

- name: User
- password: user
- role: ROLE_USER

-- admin

- name: Admin
- password: admin
- role: ROLE_ADMIN

## P.S. Sorry for style)


