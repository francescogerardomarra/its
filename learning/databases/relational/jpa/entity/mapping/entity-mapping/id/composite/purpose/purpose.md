# Purpose
Composite keys in databases are used when a single column
is not sufficient to uniquely identify a record.

Instead, a combination of two or more columns is used
as the primary key to ensure each record is unique.

### Example
**Consider a `CourseRegistration` table that records which student is registered for which course:**
- **Columns**: `student_id`, `course_id`
- **Composite Key**: (`student_id`, `course_id`)
This ensures a student cannot register for the same course
more than once, and each registration record is uniquely identified
by the combination of `student_id` and `course_id`.
