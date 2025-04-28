# Understanding Event Loops, Events, and Callbacks

Modern non-blocking systems, such as those used in reactive programming, rely heavily on **event-driven architectures**. To grasp this concept fully, it's crucial to understand the roles of **events**, **event loops**, **callbacks**, and how they work together to handle responses efficiently.

---

## Key Concepts

### 1. What is an Event?
An **event** is any action or occurrence that the program recognizes and processes. Examples include:

- A user sending an HTTP request (i.e. an incoming requests)
- A database query finishing and returning a result (i.e. a completed operation)
- A timeout triggering a scheduled task.

Events are signals that something has occurred, and they often carry additional data about what happened.

---

### 2. What is an Event Loop?
An **event loop** is a mechanism that:

1. It is a programming construct that monitors and processes events. It is a core part of asynchronous programming. 
2. It runs continuously in a single thread.
3. It listens for events (both incoming requests and completed operations).
4. Continuously checks for new events that need to be processed.
5. Delegates those events to the appropriate handlers (e.g. callbacks or tasks).
6. Waits for responses or new events and repeats the process.
7. When an event occurs, the event loop identifies and executes the associated callback (a piece of code meant to handle the event).

The event loop operates in a single thread but can handle thousands of events concurrently by delegating tasks (e.g. to a database or a filesystem) and returning to handle other events. The event loop's purpose is to efficiently handle multiple tasks without blocking the thread, enabling the system to scale.


---

### 3. What is a Callback?
A **callback** is a function registered to execute when a specific event occurs. Instead of waiting for the event to complete, the program continues processing other tasks, and the callback is invoked when the result is ready.

**Registering a callback** means associating a function or code block with an event. When the event occurs, the callback is triggered and executed.

An example is:

1. A server receives a user request for data.
2. The server sends a query to a database and tells the event loop:  
   “When the database responds, run this callback function.”
3. The callback is not executed immediately; it waits until the event (e.g. the database response) occurs.
4. The database operation completes and generates an event.
5. The event loop thread detects the event.
6. The event loop thread invokes the callback associated with the event.


---

### 4. Event Loops and Handling Responses
In an event-driven system:
- Events are **queued** for processing.
- The **event loop thread** retrieves events from the queue one by one.
- If an event requires external work (e.g. a database query), the event loop registers a **callback** and moves on to the next event.
- When the external work is complete, the callback is triggered to process the result.

---

## How it Works: Step-by-Step Example

Let’s consider a simple example of a user making a request to fetch data from a database:

### Step 1: A Request is Made
1. A user sends a request to the server (e.g. `GET /data`).
2. The event is placed in the **event queue**.

### Step 2: Event Loop Picks Up the Event
1. The **event loop thread** retrieves the event from the queue.
2. It identifies that this event requires a database query.
3. The event loop delegates the query task to a database handler and **registers a callback** for when the query finishes.

### Step 3: Event Loop Handles Other Events
1. While the database query is in progress, the event loop continues to retrieve and process other events from the queue (e.g. other user requests).
2. It does not wait for the database to finish the query.

### Step 4: Callback Executes When Database Responds
1. When the database query completes, the registered callback is executed.
2. The callback processes the data and sends a response to the user.

---

## Example Code: Event Loop in Action
Here is a simplified example using JavaScript (a language commonly associated with event loops):

```javascript
const http = require('http');

// Simulated database query function
function fetchDataFromDatabase(callback) {
    console.log("Querying the database...");
    setTimeout(() => {
        callback("Data from the database"); // Callback is triggered after 2 seconds
    }, 2000);
}

// Create an HTTP server
const server = http.createServer((req, res) => {
    if (req.url === '/data') {
        console.log("Received request for /data");
        
        // Register a callback for when the database query completes
        fetchDataFromDatabase((data) => {
            res.writeHead(200, { 'Content-Type': 'text/plain' });
            res.end(data); // Send the response to the user
        });

        console.log("Query sent to database, moving on...");
    } else {
        res.writeHead(404, { 'Content-Type': 'text/plain' });
        res.end("Not Found");
    }
});

server.listen(8080, () => {
    console.log("Server is running on http://localhost:8080");
});
```

### Explanation of the Code
1. **Querying the Database:**
   - The `fetchDataFromDatabase` function simulates a database query with a delay (using `setTimeout`).
   - The callback function is registered to execute when the query completes.

2. **Non-Blocking Behavior:**
   - The server immediately logs "Query sent to database, moving on..." and continues handling other requests.
   - It does not block or wait for the query to finish.

3. **Handling the Response:**
   - When the query completes, the callback sends the database result to the user.

---

## Advantages of Event Loops
- **High Scalability:** A single thread can handle thousands of concurrent requests.
- **Efficiency:** No resources are wasted waiting for slow operations like I/O.

## Disadvantages
- **Complexity:** Debugging and reasoning about asynchronous code can be challenging.
- **Not Suitable for CPU-Bound Tasks:** Heavy computations can block the event loop, affecting performance.

---

## Summary Table
| Term              | Description                                          |
|-------------------|------------------------------------------------------|
| Event             | A signal that something occurred (e.g. request, I/O) |
| Event Loop        | A thread managing events and delegating tasks        |
| Callback          | A function executed when an event is completed       |
| Event Queue       | A queue holding events waiting to be processed       |
| Event Loop Thread | The single thread running the event loop             |

---

By understanding how events, event loops, and callbacks work together, developers can build highly efficient, non-blocking applications that scale effectively. This architecture is foundational in reactive systems like Spring WebFlux.

