### Default Propagation Type
When using `@Transactional` without explicitly setting a propagation type, the default propagation type for your transaction is applied.

### **What Is the Default Propagation Type?**
**The default propagation type depends on the framework or configuration:**
- **Spring Framework** → `Propagation.REQUIRED`
- **Java EE (JTA)** → **Required**
- **Hibernate** → `Propagation.REQUIRED`

Since we are using **Spring Framework** in our examples and projects, the default propagation type for our transactions is **Propagation.REQUIRED**.
