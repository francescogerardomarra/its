# `Definition`
**The `@EmbeddedId` annotation has the following characteristics:**
- it involves creating an
[embeddable class](../../../../../../../../../../java/chapter-2/object-oriented/embeddable-classes/definition/definition.md)
that represents the composite key
and embedding it into the entity.

**Here are the steps to use it:**
1. **Create and embeddable class:**
   - this class contains the fields that form the
   composite key and is annotated with `@Embeddable`.
2. **Embed the key in the entity:**
   - the entity class uses `@EmbeddedId` to embed the composite key class.

**Here is an example:**
<div style="display: flex; justify-content: space-between; margin: 0 -5px;">
  <div style="width: 50%; margin: 0 5px;">

**Embeddable Class:**
```java
import java.io.Serializable;
import jakarta.persistence.Embeddable;

@Embeddable
public class OrderId implements Serializable {
    private Long orderId;
    private Long productId;

    // Constructors, Getters, Setters, hashCode, equals
    public OrderId() {}

    public OrderId(Long orderId, Long productId) {
        this.orderId = orderId;
        this.productId = productId;
    }

    // Getters and Setters

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderId orderId1 = (OrderId) o;
        return Objects.equals(orderId, orderId1.orderId) &&
               Objects.equals(productId, orderId1.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, productId);
    }
}
```
  </div>
  <div style="width: 50%; margin: 0 5px;">

**Entity Class:**
```java
import jakarta.persistence.Entity;
import jakarta.persistence.EmbeddedId;

@Entity
public class Order {
    @EmbeddedId
    private OrderId id;

    // Other fields, Getters, Setters

    public OrderId getId() {
        return id;
    }

    public void setId(OrderId id) {
        this.id = id;
    }

    // Other Getters and Setters
}
```
  </div>
</div>