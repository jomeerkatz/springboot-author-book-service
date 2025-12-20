# Books API - Spring Boot REST API with JPA & PostgreSQL

> A foundational Spring Boot REST API demonstrating clean layered architecture, DTOs, pagination, and comprehensive testing. Built to showcase core Spring Boot development skills for junior backend positions.

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://openjdk.java.net/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Latest-blue.svg)](https://www.postgresql.org/)

---

## 🎯 Project Overview

A RESTful API for managing books and authors, demonstrating fundamental Spring Boot concepts including layered architecture, DTO mapping, JPA relationships, and integration testing. This project focuses on clean code structure and testing practices essential for professional backend development.

**Project Scope:** This is a foundational portfolio project emphasizing core Spring Boot patterns. Advanced features like authentication and authorization are intentionally excluded to keep the focus on architectural fundamentals.

---

## 🏗️ Architecture & Design

### Three-Layer Architecture

```
┌─────────────────────────────────────┐
│   CONTROLLER LAYER                  │
│   - REST Endpoints                  │
│   - DTO Mapping                     │
│   - HTTP Status Handling            │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│   SERVICE LAYER                     │
│   - Business Logic                  │
│   - Partial Updates                 │
│   - Existence Checks                │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│   REPOSITORY LAYER                  │
│   - Spring Data JPA                 │
│   - Custom Queries                  │
│   - Pagination Support              │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│   DATABASE (PostgreSQL)             │
└─────────────────────────────────────┘
```

### Data Model

```
┌──────────────┐
│    Author    │
│──────────────│
│ id (PK)      │◄────┐
│ name         │     │
│ age          │     │
└──────────────┘     │
                     │ @ManyToOne
                     │
               ┌─────┴──────────┐
               │      Book      │
               │────────────────│
               │ isbn (PK)      │
               │ title          │
               │ author_id (FK) │
               └────────────────┘
```

**Entity Relationships:**
- **Many-to-One:** Multiple books can belong to one author
- **Cascade Operations:** Author is persisted/updated when saving a book
- **Primary Keys:** ISBN for books (natural key), auto-generated Long for authors

---

## 💡 Key Technical Concepts Demonstrated

### 1. Spring Boot Fundamentals

✅ **Layered Architecture** with proper separation of concerns
```java
@RestController → @Service → @Repository → Database
```

✅ **Constructor Injection** (preferred over field injection)
```java
public BookController(
    final Mapper<BookEntity, BookDto> mapper,
    final BookService bookService
) {
    this.mapper = mapper;
    this.bookService = bookService;
}
```

✅ **Service Abstraction** with interfaces
```java
public interface BookService {
    BookEntity save(BookEntity book, String isbn);
    Optional<BookEntity> getById(String isbn);
    // ...
}
```

### 2. DTO Pattern Implementation

✅ **Separation of API contracts from domain models**
```java
// API Layer
public class BookDto {
    private String isbn;
    private String title;
    private AuthorDto author;  // Nested DTO
}

// Domain Layer
@Entity
public class BookEntity {
    @Id
    private String isbn;
    private String title;
    @ManyToOne
    private AuthorEntity author;
}
```

✅ **Generic Mapper Interface**
```java
public interface Mapper<A, B> {
    B mapTo(A a);
    A mapFrom(B b);
}
```

✅ **ModelMapper Configuration**
```java
@Configuration
public class MapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
            .setMatchingStrategy(MatchingStrategies.LOOSE);
        return mapper;
    }
}
```

### 3. Spring Data JPA

✅ **Custom Query Methods** via method naming
```java
public interface AuthorRepository extends CrudRepository<AuthorEntity, Long> {
    Iterable<AuthorEntity> ageLessThan(int age);
}
// Spring generates: SELECT * FROM authors WHERE age < ?
```

✅ **JPQL Queries** for complex operations
```java
@Query("SELECT a FROM AuthorEntity a WHERE a.age > ?1")
Iterable<AuthorEntity> findAuthorWithAgeGreaterThan(int age);
```

✅ **Pagination Support**
```java
public interface BookRepository extends 
    CrudRepository<BookEntity, String>,
    PagingAndSortingRepository<BookEntity, String> {
}
```

### 4. RESTful API Design

✅ **Proper HTTP Methods**
- `PUT /books/{isbn}` - Create or update (idempotent)
- `GET /books` - List with pagination
- `PATCH /books/{isbn}` - Partial update
- `DELETE /books/{isbn}` - Delete resource

✅ **Correct Status Codes**
- `201 CREATED` - Resource created
- `200 OK` - Successful operation
- `204 NO CONTENT` - Successful deletion
- `404 NOT FOUND` - Resource doesn't exist

✅ **Pagination Implementation**
```java
@GetMapping("/books")
public Page<BookDto> getAllBooks(Pageable pageable) {
    Page<BookEntity> books = bookService.getAllBooks(pageable);
    return books.map(mapper::mapTo);
}
```

Usage: `GET /books?page=0&size=10&sort=title,asc`

### 5. Comprehensive Testing Strategy

✅ **Repository Integration Tests** with H2
```java
@SpringBootTest
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
public class BookRepositoryIntegrationTests {
    
    @Autowired
    private BookRepository bookRepository;
    
    @Test
    public void testThatBookCanBeCreatedAndRecalled() {
        // Test with real database operations
    }
}
```

✅ **Controller Integration Tests** with MockMvc
```java
@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerIntegrationTests {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    public void testThatCreatesBookReturnsHttpStatus201() throws Exception {
        mockMvc.perform(put("/books/isbn-123")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBook))
            .andExpect(status().isCreated());
    }
}
```

✅ **Test Data Utilities** for reusability
```java
public final class TestDataUtil {
    public static AuthorEntity createTestAuthor() {
        return AuthorEntity.builder()
            .name("Test Author")
            .age(45)
            .build();
    }
}
```

---

## 🛠️ Technical Stack

| Category | Technology | Purpose |
|----------|-----------|---------|
| **Framework** | Spring Boot 4.0.0 | Application framework |
| **Language** | Java 17 | Programming language |
| **Database** | PostgreSQL | Production database |
| **ORM** | Hibernate (via Spring Data JPA) | Object-relational mapping |
| **DTO Mapping** | ModelMapper 3.0.0 | Object mapping |
| **Boilerplate** | Lombok | Code generation (@Data, @Builder) |
| **Testing** | H2 Database | In-memory testing database |
| **Testing** | MockMvc | HTTP layer testing |
| **Testing** | AssertJ | Fluent assertions |

---

## 📝 API Documentation

### Book Endpoints

#### Create or Update Book
```http
PUT /books/{isbn}
Content-Type: application/json

{
  "isbn": "978-0-123456-78-9",
  "title": "Spring Boot in Action",
  "author": {
    "name": "Craig Walls",
    "age": 45
  }
}

Response: 201 Created (new) or 200 OK (update)
```

#### List Books (Paginated)
```http
GET /books?page=0&size=10&sort=title,asc

Response: 200 OK
{
  "content": [...],
  "pageable": {...},
  "totalElements": 50,
  "totalPages": 5
}
```

#### Get Book by ISBN
```http
GET /books/{isbn}

Response: 200 OK or 404 Not Found
```

#### Partial Update Book
```http
PATCH /books/{isbn}
Content-Type: application/json

{
  "title": "Updated Title"
}

Response: 200 OK or 404 Not Found
```

#### Delete Book
```http
DELETE /books/{isbn}

Response: 204 No Content or 404 Not Found
```

### Author Endpoints

#### Create Author
```http
POST /authors
Content-Type: application/json

{
  "name": "Martin Fowler",
  "age": 60
}

Response: 201 Created
```

#### List All Authors
```http
GET /authors

Response: 200 OK
[
  {
    "id": 1,
    "name": "Martin Fowler",
    "age": 60
  }
]
```

#### Get Author by ID
```http
GET /authors/{id}

Response: 200 OK or 404 Not Found
```

#### Update Author
```http
PUT /authors/{id}
Content-Type: application/json

{
  "name": "Updated Name",
  "age": 61
}

Response: 200 OK or 404 Not Found
```

#### Partial Update Author
```http
PATCH /authors/{id}
Content-Type: application/json

{
  "age": 62
}

Response: 200 OK or 404 Not Found
```

#### Delete Author
```http
DELETE /authors/{id}

Response: 204 No Content or 404 Not Found
```

---

## 🚀 Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- PostgreSQL
- Your favorite IDE (IntelliJ IDEA recommended)

### Database Setup

1. Create PostgreSQL database:
```sql
CREATE DATABASE mydb;
CREATE USER jo WITH PASSWORD 'supersecret';
GRANT ALL PRIVILEGES ON DATABASE mydb TO jo;
```

2. Configure connection in `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/mydb
spring.datasource.username=jo
spring.datasource.password=supersecret
```

### Running the Application

```bash
# Build the project
./mvnw clean install

# Run the application
./mvnw spring-boot:run

# Application will start on http://localhost:8080
```

### Running Tests

```bash
# Run all tests
./mvnw test

# Tests use H2 in-memory database (no PostgreSQL required)
```

---

## 🧪 Testing Strategy

### Test Coverage

| Layer | Test Type | Technology | Coverage |
|-------|-----------|------------|----------|
| **Repository** | Integration | H2 + Spring Boot Test | All CRUD operations + custom queries |
| **Controller** | Integration | MockMvc | All endpoints + edge cases |

### Repository Test Examples

**Testing Custom Queries:**
```java
@Test
public void testThatAuthorsUnder80Recalled() {
    // Given
    authorRepository.save(createAuthor("Young Author", 30));
    authorRepository.save(createAuthor("Old Author", 90));
    
    // When
    Iterable<AuthorEntity> result = authorRepository.ageLessThan(80);
    
    // Then
    assertThat(result).hasSize(1);
    assertThat(result.iterator().next().getName())
        .isEqualTo("Young Author");
}
```

### Controller Test Examples

**Testing HTTP Status Codes:**
```java
@Test
public void testThatGetBookByIsbnReturnsHttpStatus404NotFound() throws Exception {
    mockMvc.perform(get("/books/non-existent-isbn"))
        .andExpect(status().isNotFound());
}
```

**Testing Response Bodies:**
```java
@Test
public void testThatCreatesBookReturnsCreatedBook() throws Exception {
    mockMvc.perform(put("/books/isbn-123")
            .contentType(MediaType.APPLICATION_JSON)
            .content(bookJson))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.isbn").value("isbn-123"))
        .andExpect(jsonPath("$.title").value("Test Book"));
}
```

---

## 📊 What This Project Demonstrates

### For Junior Backend Positions

✅ **Spring Boot Fundamentals**
- Application configuration and properties
- Dependency injection patterns
- Bean lifecycle management

✅ **RESTful API Design**
- Resource-oriented URLs
- Proper HTTP method usage
- Status code semantics

✅ **Data Persistence**
- JPA/Hibernate entity mapping
- Repository pattern with Spring Data
- Custom query methods

✅ **Clean Architecture**
- Layered separation of concerns
- DTO pattern for API contracts
- Service abstraction with interfaces

✅ **Testing Practices**
- Integration testing with real database operations
- HTTP layer testing with MockMvc
- Test data management patterns

✅ **Professional Practices**
- Constructor injection (testability)
- Lombok for boilerplate reduction
- Pagination for large datasets
- Proper use of Optional<T>

---

## 🔄 Project Scope & Limitations

### Intentionally Included
- ✅ Clean layered architecture
- ✅ DTO pattern with ModelMapper
- ✅ JPA relationships and cascade operations
- ✅ Pagination support
- ✅ Custom repository queries
- ✅ Comprehensive integration tests
- ✅ Partial update logic

### Intentionally Excluded (Future Enhancements)
- ⏭️ Authentication & Authorization (Spring Security)
- ⏭️ Input validation (@Valid, Bean Validation)
- ⏭️ Global exception handling (@ControllerAdvice)
- ⏭️ API documentation (Swagger/OpenAPI)
- ⏭️ Service layer unit tests with Mockito
- ⏭️ Database migrations (Flyway/Liquibase)
- ⏭️ Actuator endpoints (health, metrics)

**Why this scope?**  
This project focuses on demonstrating fundamental Spring Boot architecture and testing patterns. Advanced features are excluded to keep the codebase clear and focused on core concepts essential for junior developer roles.

---

## 🎓 Key Learning Outcomes

### Technical Skills Developed

1. **Spring Boot Application Development**
   - Project setup and configuration
   - Dependency management with Maven
   - Environment-specific properties

2. **JPA/Hibernate Proficiency**
   - Entity mapping and relationships
   - Repository pattern implementation
   - Custom query methods

3. **Testing Methodology**
   - Integration testing strategies
   - MockMvc for HTTP testing
   - H2 for test isolation

4. **RESTful API Patterns**
   - Resource design
   - HTTP semantics
   - Pagination implementation

5. **Clean Code Practices**
   - Dependency injection
   - DTO pattern
   - Separation of concerns

---
