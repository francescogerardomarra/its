# Case 1: when a single repository is involved

### What’s the problem without transactions?

Imagine you have a method that needs to update both a user’s **password** and **email**. You do these in two separate `save()` calls:

1. **Save the new password**
2. **Save the new email**, but this second step might throw if the email is invalid

Without a transaction, if step2 blows up, step1 has *already* committed—and you’re left in a half‑updated state: the password changed but the email didn’t.

### Bad example: no transaction

```java
// Update password and email without transactions
public void updateCredentials(Long userId, String newPassword, String newEmail) {
    // 1) Load user
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new EntityNotFoundException("User not found"));

    // 2) Update password
    user.setPassword(passwordEncoder.encode(newPassword));
    userRepository.save(user);

    // 3) Update email (this might throw)
    if (!emailValidator.isValid(newEmail)) {
        throw new IllegalArgumentException("Invalid email format");
    }
    user.setEmail(newEmail);
    userRepository.save(user);
}
```
**What goes wrong?**
- if `emailValidator` rejects the new email, you get an exception after the password was already persisted.
- you’ve left the system in an inconsistent state: the user can’t log in with the old password, but their email is unchanged.
