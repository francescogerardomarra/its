# jpa
- introduction
    - [persistence](persistence/persistence.md)
    - [object-relational mismatch](or-mismatch/or_mismatch.md)
- JDBC
    - [definition](jdbc/definition/definition.md)
    - [connectivity](jdbc/connectivity/connectivity.md)
    - connection management
        - [definition](jdbc/management/definition/definition.md)
        - [example](jdbc/management/example/example.md)
    - connection pooling
        - [definition](jdbc/pooling/definition/definition.md)
        - [example 1: manual](jdbc/pooling/example-1/example_1.md)
        - [example 2: HikariCP](jdbc/pooling/example-2/example_2.md)
- JPA
    - [definition](jpa/definition/definition.md)
    - [jakarta](jpa/jakarta/jakarta.md)
    - [JDBC vs JPA](jpa/comparison/comparison.md)
- ORM
    - [definition](orm/definition/definition.md)
    - [Hibernate](orm/hibernate/hibernate.md)
    - [JPA vs ORM](orm/jpa-vs-orm/jpa_vs_orm.md)
- persistence in Spring
    - [definition](spring/definition/definition.md)
    - Spring boot
      - [what it is](spring/boot/what-it-is/what_it_is.md)
      - [datasource](spring/boot/datasource/datasource.md)
      - [autoconfiguration](spring/boot/auto-config/auto_config.md)
    - [JpaRepository](spring/repository/repository.md)
- entity
    - [mapping](entity/mapping.md)
    - [constraints](entity/constraints.md)
    - [relationships](entity/relationships.md)
- custom queries
  - JPQL
  - Native queries
- dto
    - [definition](../../../java/chapter-2/object-oriented/components/dto/dto.md)
    - [separation of concerns](dto/separation/separation.md)
    - [duty separation](dto/duty/duty.md)
    - [versioning and security](dto/version-sec/version_sec.md)
    - example
        - [db build](dto/example/db-build/db_build.md)
        - [application](dto/example/application/application.md)
        - [running](dto/example/running/running.md)
- transactions
    - [definition](transactions/definition/definition.md)
    - [why do they matter?](transactions/why/why.md)
    - what is the role of transactions in database management systems (DBMS)?
        - [what is a transaction?](transactions/dbms/definition/definition.md)
        - [ACID principles](transactions/dbms/acid/acid.md)
        - [concurrency control](transactions/dbms/concurrency/concurrency.md)
        - [error recovery](transactions/dbms/error-recovery/error_recovery.md)
        - [data integrity](transactions/dbms/integrity/integrity.md)
    - transaction lifecycle
        - **Start, Commit, and Rollback**
            - `@Transactional`
                - [description](transactions/lifecycle/stages/transactional/description/description.md)
                - [what happens when you don't use `@Transactional`?](transactions/lifecycle/stages/transactional/what/what.md)
            - [what triggers the start of a transaction?](transactions/lifecycle/stages/start/start.md)
            - [Spring Object management](transactions/lifecycle/stages/spring-object-management/index.md)
            - [Spring AOP in transaction management](transactions/lifecycle/stages/aop/index.md)
            - [what happens when a transaction is committed?](transactions/lifecycle/stages/commit/commit.md)
            - [rollback: when and how it happens?](transactions/lifecycle/stages/rollback/rollback.md)
    - using transactions
        - isolation levels
            - [description](transactions/using/isolation/description/description.md)
            - [why do they matter?](transactions/using/isolation/why/why.md)
            - [what do they affect?](transactions/using/isolation/what/what.md)
            - [default isolation level](transactions/using/isolation/default/default.md)
            - example
                - dirty reads
                    - [can they occur in PostgreSQL?](transactions/using/isolation/example/dirty-reads/can-it-occur/can_it_occur.md)
                - non-repeatable reads
                    - [example](transactions/using/isolation/example/non-repeatable-reads/example/example.md)
                    - [timeline](transactions/using/isolation/example/non-repeatable-reads/timeline/timeline.md)
                    - [solution](transactions/using/isolation/example/non-repeatable-reads/solution/solution.md)
                - phantom reads
                    - [example](transactions/using/isolation/example/phantom-reads/example/example.md)
                    - [timeline](transactions/using/isolation/example/phantom-reads/timeline/timeline.md)
                    - [solution](transactions/using/isolation/example/phantom-reads/solution/solution.md)
        - propagation
            - [description](transactions/using/propagation/description/description.md)
            - [why does it matter?](transactions/using/propagation/why/why.md)
            - [what does it affect?](transactions/using/propagation/what/what.md)
            - [default propagation type](transactions/using/propagation/default/default.md)
            - example
                - [generic](transactions/using/propagation/example/generic/generic.md)
                - nested transactions
                    - [description](transactions/using/propagation/example/nested/description/description.md)
                    - [example](transactions/using/propagation/example/nested/example/example.md)
    - **Transaction Boundaries**
        - [how transactions are started and ended in different contexts](transactions/lifecycle/boundaries/contexts/contexts.md)
        - [Adding a transactional context to a method](transactions/lifecycle/boundaries/transactional-context/transactional_context.md)
    - when does a method need to be marked with a transactional context (transactional boundary)?
        - [marking a method with a transactional context](transactions/transactional-boundary/marking/marking.md)
        - [isolation requirements](transactions/isolation-requirements/isolation_requirements.md)
        - [when read operations don’t need a transaction](transactions/read-ops/read_ops.md)
    - consequences of not using transactions:
        - [case 1: when a single repository is involved](transactions/consequences/case-1/case_1.md)
        - [case 2: when multiple repositories are involved](transactions/consequences/case-2/case_2.md)
        - [comparison](transactions/consequences/comparison/comparison.md)
- explicit entity management
    - `EntityManager`
        - [description](entity-management/entity-manager/description/description.md)
        - [methods](entity-management/entity-manager/methods/methods.md)
    - `EntityManagerFactory`
        - [description](entity-management/entity-manager-factory/description/description.md)//todo
        - methods
        - factory design pattern
    - persistence context
        - [description](entity-management/persistence-context/description/description.md)
        - example
    - entity life-cycle
        - [description](entity-management/life-cycle/description/description.md)
        - [states](entity-management/life-cycle/states/states.md)
        - [typical flow](entity-management/life-cycle/flow/flow.md)
        - [real world example]()
        - [code example]()
        - [spring example]()
    - fetching
        - [eager vs lazy](entity-management/fetching/eager-vs-lazy/eager_vs_lazy.md)
        - best practices
    - modifying queries
        - `@Modifying`
        - native
        - jpql
    - lifecycle callbacks
        - `@PrePersist`
        - `@PreUpdate`
        - `@PostLoad`
        - cheatsheet


```
## 🔄 Transaction Propagation

### Transaction Context Passing
- **Contextual propagation** (thread-local, coroutine context)
- **Out-of-band propagation** (headers in microservices)

### Propagation in Distributed Systems
- **X/Open XA model**
- **Two-Phase Commit (2PC)**
- **Three-Phase Commit (3PC)**

---

## 🔐 Transaction Isolation

### Implementation Techniques
- **MVCC** (Multi-Version Concurrency Control)
- **Lock-based isolation**
- **Snapshot isolation**

---

## 🔒 Locking Mechanisms

### Types of Locks
- **Shared Locks**
- **Exclusive Locks**
- **Intent Locks**
- **Row-level, Page-level, Table-level locks**

### Locking Protocols
- **Two-Phase Locking (2PL)**
- **Strict 2PL**
- **Conservative 2PL**

### Deadlocks
- **Deadlock detection**
- **Deadlock prevention**
- **Timeout-based strategies**

### Lock Escalation

```

