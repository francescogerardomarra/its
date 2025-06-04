# Canary Releases Definition

- canary releases are a deployment strategy used to minimize risk when releasing new versions of software;
- the idea is to gradually roll out a new version of an application to a small subset of users or containers, monitor its performance, and then expand the rollout if everything looks good.

**Example scenario:**

1. a new version of an app is deployed to 5% of users;
2. the team monitors logs, error rates, and user feedback;
3. if everything is stable, the rollout continues to 25%, then 50%, and eventually 100%;
4. if an issue is found at 5%, the rollout is paused or reversed.
