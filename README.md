# 🚀 Order Management System

[![Java](https://img.shields.io/badge/Java-17-%23ED8B00)](https://openjdk.org/projects/jdk/17/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.5-%236DB33F)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-%23336791)](https://www.postgresql.org/)
[![Swagger](https://img.shields.io/badge/Swagger%20UI-3.0-%2385EA2D)](https://swagger.io/tools/swagger-ui/)
[![License](https://img.shields.io/badge/License-MIT-blue)](LICENSE)

Система управления заказами клиентов с момента их размещения до выполнения.  
Реализована на **Spring Boot** с аутентификацией через **Spring Security**, хранением данных в **PostgreSQL** и документацией API через **Swagger UI**.

---

## 🌟 Возможности
- Создание и управление заказами
- Управление товарами и клиентами
- Автоматическое хеширование паролей (BCrypt)
- Ролевая модель доступа (ADMIN/CUSTOMER)
- Документированное REST API
- Глобальная обработка исключений (AOP)

---

## 🛠️ Технологии
- **Java 17**
- **Spring Boot 3.1.5**
   - Spring Data JPA (Hibernate)
   - Spring Security
   - Spring Validation
- **PostgreSQL 16**
- **Swagger UI** (springdoc-openapi)
- **Lombok**
- **Guava**
- **JUnit 5** + **Mockito**
- **AOP** (обработчик исключений)
 
---

## ⚡ Быстрый старт

### Предварительные требования
- Java 17+
- Maven 3.8+
- PostgreSQL 14+

### Установка
1. Клонируйте репозиторий:
   ```bash
   git clone https://github.com/your-repo/order-management.git
   cd order-management
   ```
2. Убедитесь, что у вас установлен PostgreSQL, и запустите сервер базы данных.
3. Создайте базу данных и пользователя с необходимыми правами (используйте данные из application.properties или переменные окружения).
4. Настройте параметры подключения в src/main/resources/application.properties или через переменные окружения:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/order_management_db
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```
5. Соберите и запустите проект: 
   ```bash
   mvn clean package
   mvn spring-boot:run
   ```
6. Проект будет доступен по адресу:
   http://localhost:8080

---

## 📚 API Документация
### Swagger UI
Документация доступна по адресу:
http://localhost:8080/swagger-ui/index.html

### Основные эндпоинты
#### Управление продуктами
| Метод | Путь               | Описание                  |
|-------|--------------------|---------------------------|
| GET   | `/products/{id}`   | Получить продукт по ID    |
| POST  | `/products`        | Создать новый продукт     |
| PUT   | `/products/{id}`   | Обновить продукт          |
| DELETE| `/products/{id}`   | Удалить продукт           |

#### Управление заказами
| Метод | Путь                     | Описание                     |
|-------|--------------------------|------------------------------|
| POST  | `/orders`                | Создать заказ                |
| GET   | `/orders/{id}`           | Получить заказ по ID         |
| PUT   | `/orders/{id}/status`    | Обновить статус заказа       |
| GET   | `/orders/my`             | Заказы текущего пользователя |

---

## 🔒 Аутентификация
- Реализована через **Spring Security**
- Пароли хранятся в виде BCrypt-хешей
- Доступные роли:
     - `ROLE_ADMIN` - полный доступ
     - `ROLE_CUSTOMER` - ограниченные права
- Некоторые эндпоинты доступны только авторизованным пользователям.

---

## 🚦 CI/CD
- Автоматическая сборка и тестирование через GitHub Actions
- Чекстайл для проверки кода
- Деплой на Render.com

---

## 🧪 Тестирование
 - Запуск тестов:
```bash
 mvn test
```
 - Юнит-тесты с JUnit 5 и Mockito.
 - Тестируется сервисный слой и контроллеры.
 - Тесты запускаются автоматически в CI.

---

## 📬 Контакты
- Если у вас есть вопросы или предложения, обращайтесь через GitHub.

---

## Note
Это мой первый Spring Boot проект, созданный для обучения и портфолио.
