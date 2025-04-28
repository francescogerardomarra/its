# `NTEXT`
**`NTEXT` has the following characteristics:**
- it is mapped to the `String` class;
- it is a **variable-length unicode character string**;
- it only uses as much storage as
  needed for the actual text, plus a small overhead
  to store the length of the string;
- it is used to store large amounts of text data;
- it is typically used when the maximum length of `VARCHAR` is insufficient;
- it is also used when there is a need to store multilingual data or characters outside the ASCII range.