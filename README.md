# casbin-spring-boot-example

A simple example of [casbin-spring-boot-starter](https://github.com/jcasbin/casbin-spring-boot-starter).

## Requirement

JDK 17+

## Usage

Run with:

```shell
mvn spring-boot:run
```

Which will start a server at `http://localhost:8080` with following api:

- `GET /auth/login`: login to system.

  Request params:

  - `username`: `admin` or `user`
  - `password`: `pwd`

  Example: `GET /auth/login?username=admin&password=pwd`
- `GET /auth/logout`: logout the system.
- `GET /data/admins/all`: available when login with `admin` account.
- `PUT /data/admins/state/{state}`: Change the protected data, available when login with `admin`/`user` account.
- `GET /data/users/all`: available when login with `admin`/`user` account.

Special thanks to <https://github.com/jveverka/spring-examples/tree/java-17/spring-jcasbin>