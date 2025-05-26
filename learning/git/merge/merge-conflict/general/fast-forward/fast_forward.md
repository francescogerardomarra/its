# When is it possible to perform a fast-forward merge?

A fast-forward merge is possible when the branch you are merging into has not diverged from the branch you are merging. In other words, a fast-forward merge occurs when there are no new commits on the target branch since the branch you want to merge diverged from it. This situation typically happens in the following scenarios:

1. **Feature Branch Merged into Master**

   - You create a `feature-branch` from `master`.
   - While working on the feature branch, no one else commits directly to `master`.
   - When you finish the feature, you want to merge it back into `master`.
   - A fast-forward merge is possible because there are no new commits on `master` since you created the feature branch.

2. **Upstream Changes Merged into Feature Branch**

   - You create a `feature-branch` from `master`.
   - Meanwhile, someone else makes changes and commits them directly to `master`.
   - When you want to incorporate those changes into your feature branch, a fast-forward merge is possible if no new commits have been made directly on your feature branch.
