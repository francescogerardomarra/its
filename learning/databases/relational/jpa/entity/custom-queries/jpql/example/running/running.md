# Running
In this example the only change have pertained the addition of a single custom jpql query.

Below a testing guide for said query can be seen:

1. ### Retrieve all orders with the pending status
    Retrieves all orders with the pending status.
    ```bash
    curl -X GET "http://localhost:8080/shop/orders?status=pending" -H "Content-Type: application/json"
    ```
    **note:** for now this is the only call that does not give an error, the reason behind this is because we did not create any objects with a different status.

2. ### Retrieve all orders with the shipped status
    Retrieves all orders with the shipped status.
    ```bash
    curl -X GET "http://localhost:8080/shop/orders?status=shipped" -H "Content-Type: application/json"
    ```
    **note:** this will give an error.

3. ### Retrieve all orders with the delivered status
    Retrieves all orders with the delivered status.
    ```bash
    curl -X GET "http://localhost:8080/shop/orders?status=delivered" -H "Content-Type: application/json"
    ```
    **note:** this will give an error.

4. ### Retrieve all orders with the canceled status
    Retrieves all orders with the canceled status.
    ```bash
    curl -X GET "http://localhost:8080/shop/orders?status=canceled" -H "Content-Type: application/json"
    ```
    **note:** this will give an error.
