# Case 2: when multiple repositories are involved.
### **What’s the Problem Without Transactions?**

**During a typical checkout process, multiple things happen:**
1. The order is saved.
2. The inventory is updated.

**Now, imagine if something fails after saving the order:**
- The order is recorded ✅
- But updating the inventory fails ❌
- Result? You just sold an item that technically still looks “in stock” — not good.

This leads to **inconsistent data**, like overselling or inaccurate inventory levels.

Here’s a method that demonstrates this issue:

```java
// Checkout method without transactions
public void checkout(Order order, List<OrderItem> items) {
    // Save the order
    orderRepository.save(order);

    // Update inventory for each item
    for (OrderItem item : items) {
        Product product = productRepository.findById(item.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Decrease stock quantity
        int updatedStock = product.getStock() - item.getQuantity();
        if (updatedStock < 0) {
            throw new RuntimeException("Insufficient stock");
        }
        product.setStock(updatedStock);

        productRepository.save(product);
    }
}
```

---

No transaction = **big risk**.  
If the app crashes during inventory updates, the order still exists but the stock wasn’t updated — and that’s a real problem.