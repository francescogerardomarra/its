# Description
When you annotate methods or classes with `@Transactional`, Spring wraps the target bean with a proxy.

A proxy is basically a wrapper object that "stands in" for your real bean (for example, a service class).

It adds extra behavior before or after method calls—without changing your actual code.

In the case of Spring AOP, proxies are used to dynamically inject [behavior](../behaviour/behaviour.md) like logging, security, or transactions.
