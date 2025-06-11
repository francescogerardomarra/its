# Description
A **Spring bean** is simply a **regular java object** that's been **declared as a managed object** using annotations.

**Annotations like:**
- [`@Component`](../../../spring-object-management/component/component.md);
- [other various specifications of `@Component`](../../../spring-object-management/cheatsheet/cheatsheet.md).

## By whom is the bean managed?
The Spring bean is managed by the **Spring IoC (Inversion of Control) container**.

**The container manages everything about the bean:**
- creating it;
- injecting its dependencies;
- configuring it;
- and managing its full lifecycle (from creation to destruction).