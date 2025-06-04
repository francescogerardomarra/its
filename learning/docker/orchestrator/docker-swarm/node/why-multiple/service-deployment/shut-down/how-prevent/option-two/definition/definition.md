# How Prevent Definition

- to fully prevent dropped or disrupted client requests during a Docker Swarm rolling update, you must combine all the next elements under the [steps section](../../../../../../../index.md);
- each one solves a different part of the shutdown problem;
- as specify within [graceful shutdown handler](../step/graceful-shutdown/graceful_shutdown.md) chapter, **this strategy is not fully reliable for achieving zero downtime** (better to use [option 1](../../../../../../../index.md) strategy).
