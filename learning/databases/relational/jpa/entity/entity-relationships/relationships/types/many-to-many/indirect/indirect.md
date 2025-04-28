# Indirect relationship
**A direct many-to-many relationship in a database (e.g., between `Student` and `Course`)
is impractical due to these reasons:**

  1. **redundancy and integrity issues**
      - storing multiple courses in a `Student` row (e.g., as a list) or multiple
     students in a `Course` row leads to repeated information across rows;
      - this violates normalization principles, and any change to
     a course's details requires updating all related student records, which is tedious and error-prone.
  2. **violates normalization (1NF)**
      - relational databases require atomic values (single data points) in each column;
      - storing arrays or lists (e.g., `enrolled_courses = [101, 102]`) violates
     the rule of atomicity and makes the database design inefficient.
  3. **complex querying**
      - searching for all students enrolled in a course requires parsing lists stored in a column, which 
     is inefficient and complicates database operations;
      - relational databases are optimized for linking rows across tables, not processing lists.
  4. **no room for extra data**
      - details about the relationship (e.g., enrollment dates, grades) cannot be stored 
     directly in the `Student` or `Course` tables;
      - a bridge table is necessary to handle relationship-specific details effectively.

---

### Why a junction table works
**The `Student_Course` junction table resolves these issues by splitting the relationship into two one-to-many relationships:**
1. a student can have many rows in the `Student_Course` table;
2. a course can have many rows in the `Student_Course` table.

**This approach:**
- avoids redundancy and maintains data integrity;
- makes queries efficient using joins;
- allows storing additional details (e.g., grades).

In summary, a junction table is essential to properly handle many-to-many relationships.

