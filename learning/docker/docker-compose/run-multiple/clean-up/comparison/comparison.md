# Comparison

In this chapter, we show a comparison between [option 1](../option-one/optione_one.md) and [option 2](../option-two/option_two.md):

| Feature                  | `down + up --build` (option 1) | `up --force-recreate --build` (option 2) |
|--------------------------|--------------------------------|------------------------------------------|
| Rebuild images           | yes                            | yes                                      |
| Recreate containers      | yes                            | yes                                      |
| Remove containers        | yes (with `down`)              | yes (via recreate)                       |
| Remove **volumes**       | no (unless `--volumes`)        | no                                       |
| Remove **networks**      | yes                            | no                                       |
| Keeps persistent data?   | not unless volumes are named   | yes                                      |
| Fully resets environment | yes                            | not fully                                |
