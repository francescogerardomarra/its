Client -> mypublicapi.com (resolves to Swarm Node A)
|
v
Node A (has no replica)
|
Swarm Routing Mesh
|
v
Node D (has a replica of my-api)
|
v
Container handles request
