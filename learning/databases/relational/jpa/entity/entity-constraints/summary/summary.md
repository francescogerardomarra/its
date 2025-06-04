# Summary
**This summary table shows the mapping between Entity-level and Database-level Constraints:**

| **Constraint Type** | **Entity-Level (Java / Hibernate)**                    | **Database-Level (PostgreSQL)**                         | **Description**                                                         |
|---------------------|--------------------------------------------------------|---------------------------------------------------------|-------------------------------------------------------------------------|
| notNull             | `@NotNull`                                             | `NOT NULL`                                              | Ensures that a column cannot contain null values.                       |
| unique              | Custom implementation (`@Column(unique=true)`)         | `UNIQUE`                                                | Ensures column values are unique. Hibernate lacks a built-in `@Unique`. |
| size                | `@Size(min=..., max=...)`                              | `CHARACTER VARYING(n)` or `TEXT` with length constraint | Enforces a string length limit.                                         |
| range               | `@Min(value = ...)`, `@Max(value = ...)`               | `CHECK (column >= min AND column <= max)`               | Validates numeric range boundaries.                                     |
| pattern             | `@Pattern(regexp = "regex")`                           | `CHECK (column ~ 'regex')`                              | Ensures a string matches a regular expression.                          |
| enumerated          | `@Enumerated(EnumType.STRING)`                         | `ENUM('value1', 'value2', ...)`                         | Maps enums to strings (Hibernate) or native ENUM types (PostgreSQL).    |
| foreignKey          | `@ManyToOne`, `@OneToMany`, `@JoinColumn`              | `FOREIGN KEY (...) REFERENCES ...`                      | Maintains referential integrity via foreign key constraints.            |
| cascade             | `@ManyToOne(cascade = CascadeType.ALL)`                | `ON DELETE CASCADE`, `ON UPDATE CASCADE`                | Handles delete/update cascading operations.                             |
| defaultValue        | `@ColumnDefault("default_value")` *(not standard JPA)* | `DEFAULT 'default_value'`                               | Sets a default value if no value is provided.                           |
| notBlank            | `@NotBlank` *(ensures not null and not empty)*         | No direct equivalent *(use `CHECK (column != '')`)*     | Ensures that a string is neither null nor empty.                        |
| compositeUnique     | Multiple field annotations                             | `UNIQUE(column1, column2)`                              | Ensures a combination of columns is unique.                             |
