# Where Do They Come From?

- **image rebuilds**: when you rebuild an image without assigning a new tag, Docker may create a new image with a unique ID, leaving the old one without a tag;
- **image updates**: pulling a newer version of an image can leave the older, untagged version as a dangling image.
