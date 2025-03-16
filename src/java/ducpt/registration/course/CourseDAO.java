package ducpt.registration.course;

import ducpt.database.DBUtils;
import ducpt.entity.Course;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

public class CourseDAO {
    private List<Course> courses;
    
    public List<Course> getCourses() {
        return courses;
    }

    public boolean addCourse(Course course) throws SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;
        
        try {
            System.out.println("\n=== CourseDAO.addCourse ===");
            con = DBUtils.makeConnection();
            if (con != null) {
                System.out.println("Database connection successful");
                
                String sql = "INSERT INTO Courses (name, description, tuitionFee, startDate, endDate, category) "
                          + "VALUES (?, ?, ?, ?, ?, ?)";
                System.out.println("SQL Query: " + sql);
                
                stm = con.prepareStatement(sql);
                stm.setString(1, course.getCourseName());
                stm.setString(2, course.getDescription());
                stm.setDouble(3, course.getTuitionFee());
                stm.setString(4, course.getStartDate());
                stm.setString(5, course.getEndDate());
                stm.setString(6, course.getCategory());
                
                System.out.println("Executing query with values:");
                System.out.println("Name: " + course.getCourseName());
                System.out.println("Description: " + course.getDescription());
                System.out.println("Tuition Fee: " + course.getTuitionFee());
                System.out.println("Start Date: " + course.getStartDate());
                System.out.println("End Date: " + course.getEndDate());
                System.out.println("Category: " + course.getCategory());
                
                int rowsAffected = stm.executeUpdate();
                System.out.println("Rows affected: " + rowsAffected);
                
                result = rowsAffected > 0;
            } else {
                System.out.println("Failed to get database connection!");
            }
        } catch (SQLException e) {
            System.out.println("SQL Error in addCourse: " + e.getMessage());
            e.printStackTrace();
            throw e;
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
            System.out.println("Final result of addCourse: " + result);
        }
        
        return result;
    }

    public boolean deleteCourse(String name) throws SQLException {
        try (Connection con = DBUtils.makeConnection();
             PreparedStatement stm = con.prepareStatement(
                "DELETE FROM Courses WHERE name=?")) {
            
            stm.setString(1, name);
            return stm.executeUpdate() > 0;
        }
    }

    public void searchByName(String searchValue) throws SQLException {
        System.out.println("\n=== CourseDAO.searchByName ===");
        System.out.println("Searching for: " + searchValue);
        
        try (Connection con = DBUtils.makeConnection();
             PreparedStatement stm = con.prepareStatement(
                "SELECT * FROM Courses WHERE name LIKE ?")) {
            
            stm.setString(1, "%" + searchValue + "%");
            try (ResultSet rs = stm.executeQuery()) {
                courses = new ArrayList<>();
                while (rs.next()) {
                    String name = rs.getString("name");
                    String description = rs.getString("description");
                    double tuitionFee = rs.getDouble("tuitionFee");
                    String startDate = rs.getString("startDate");
                    String endDate = rs.getString("endDate");
                    String category = rs.getString("category");
                    
                    System.out.println("Found course: " + name);
                    System.out.println("With values:");
                    System.out.println("Description: " + description);
                    System.out.println("Tuition Fee: " + tuitionFee);
                    System.out.println("Start Date: " + startDate);
                    System.out.println("End Date: " + endDate);
                    System.out.println("Category: " + category);
                    
                    Course course = new Course(name, description, tuitionFee, 
                                             startDate, endDate, category);
                    courses.add(course);
                }
                System.out.println("Total courses found: " + (courses != null ? courses.size() : 0));
            }
        }
    }

    public List<Course> getAllCourses() throws SQLException {
        List<Course> allCourses = new ArrayList<>();
        String sql = "SELECT name, description, tuitionFee, startDate, endDate, category " +
                     "FROM Courses";
        
        System.out.println("\n=== CourseDAO.getAllCourses ===");
        System.out.println("Executing SQL: " + sql);
        
        try (Connection con = DBUtils.makeConnection();
             PreparedStatement stm = con.prepareStatement(sql);
             ResultSet rs = stm.executeQuery()) {
            
            System.out.println("Database connection successful");
            int count = 0;
            
            while (rs.next()) {
                count++;
                String name = rs.getString("name");
                String description = rs.getString("description");
                double tuitionFee = rs.getDouble("tuitionFee");
                String startDate = rs.getString("startDate");
                String endDate = rs.getString("endDate");
                String category = rs.getString("category");
                
                System.out.println("Found course " + count + ": " + name);
                System.out.println("Description: " + description);
                System.out.println("Tuition Fee: " + tuitionFee);
                System.out.println("Start Date: " + startDate);
                System.out.println("End Date: " + endDate);
                System.out.println("Category: " + category);
                
                Course course = new Course(name, description, tuitionFee, 
                                        startDate, endDate, category);
                allCourses.add(course);
            }
            
            System.out.println("Total courses found: " + count);
        } catch (SQLException e) {
            System.out.println("Error in getAllCourses: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
        
        return allCourses;
    }

    public boolean updateCourse(String name, String description, double tuitionFee, 
                              String startDate, String endDate, String category) throws SQLException {
        try (Connection con = DBUtils.makeConnection();
             PreparedStatement stm = con.prepareStatement(
                "UPDATE Courses SET description=?, tuitionFee=?, startDate=?, endDate=?, category=? WHERE name=?")) {
            
            stm.setString(1, description);
            stm.setDouble(2, tuitionFee);
            stm.setDate(3, java.sql.Date.valueOf(startDate));
            stm.setDate(4, java.sql.Date.valueOf(endDate));
            stm.setString(5, category);
            stm.setString(6, name);
            
            return stm.executeUpdate() > 0;
        }
    }

    public List<Course> getActiveCoursesPaginated(int page, int pageSize) throws SQLException {
        int offset = (page - 1) * pageSize;
        System.out.println("Getting active courses: page=" + page + ", pageSize=" + pageSize + ", offset=" + offset);
        
        try (Connection con = DBUtils.makeConnection();
             PreparedStatement stm = con.prepareStatement(
                "SELECT * FROM Courses WHERE status = 'active' AND quantity > 0 " +
                "ORDER BY startDate DESC " +
                "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY")) {
            
            stm.setInt(1, offset);
            stm.setInt(2, pageSize);
            
            try (ResultSet rs = stm.executeQuery()) {
                List<Course> courses = new ArrayList<>();
                while (rs.next()) {
                    courses.add(new Course(
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("tuitionFee"),
                        rs.getString("startDate"),
                        rs.getString("endDate"),
                        rs.getString("category")
                    ));
                }
                System.out.println("Retrieved " + courses.size() + " active courses");
                return courses;
            }
        }
    }

    public int getTotalActiveCourses() throws SQLException {
        try (Connection con = DBUtils.makeConnection();
             PreparedStatement stm = con.prepareStatement(
                "SELECT COUNT(*) FROM Courses WHERE status = 'active' AND quantity > 0");
             ResultSet rs = stm.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }
    }

    public List<Course> getFirst20ActiveCourses() throws SQLException {
        try (Connection con = DBUtils.makeConnection();
             PreparedStatement stm = con.prepareStatement(
                "SELECT TOP 20 * FROM Courses " +
                "WHERE status = 'active' AND quantity > 0 " +
                "ORDER BY startDate DESC")) {
            
            try (ResultSet rs = stm.executeQuery()) {
                List<Course> courses = new ArrayList<>();
                while (rs.next()) {
                    courses.add(new Course(
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("tuitionFee"),
                        rs.getString("startDate"),
                        rs.getString("endDate"),
                        rs.getString("category")
                    ));
                }
                System.out.println("Retrieved " + courses.size() + " courses from first 20 active");
                return courses;
            }
        }
    }

    public List<Course> getPaginatedCourses(int page) throws SQLException {
        int pageSize = 5;
        int offset = (page - 1) * pageSize;
        
        System.out.println("Pagination request: page=" + page + ", pageSize=" + pageSize + ", offset=" + offset);
        
        try (Connection con = DBUtils.makeConnection();
             PreparedStatement stm = con.prepareStatement(
                "WITH TopCourses AS (" +
                "  SELECT TOP 20 * FROM Courses " +
                "  WHERE status = 'active' AND quantity > 0 " +
                "  ORDER BY CONVERT(DATE, startDate) DESC" +
                ") " +
                "SELECT * FROM TopCourses " +
                "ORDER BY CONVERT(DATE, startDate) DESC " +
                "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY")) {
            
            stm.setInt(1, offset);
            stm.setInt(2, pageSize);
            
            try (ResultSet rs = stm.executeQuery()) {
                List<Course> courses = new ArrayList<>();
                while (rs.next()) {
                    courses.add(new Course(
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("tuitionFee"),
                        rs.getString("startDate"),
                        rs.getString("endDate"),
                        rs.getString("category")
                    ));
                }
                System.out.println("Fetched " + courses.size() + " courses for page " + page);
                return courses;
            }
        }
    }

    public int getTotalPages() throws SQLException {
        int totalRecords = 20; // We only want to paginate through max 20 records
        int pageSize = 5;
        int totalPages = (int) Math.ceil(totalRecords / (double)pageSize);
        System.out.println("Total records (capped at 20): " + totalRecords + ", Total pages: " + totalPages);
        return totalPages;
    }

    public List<Course> searchByNamePaginated(String searchValue, int page) throws SQLException {
        int pageSize = 5;
        int offset = (page - 1) * pageSize;
        
        try (Connection con = DBUtils.makeConnection();
             PreparedStatement stm = con.prepareStatement(
                "WITH TopCourses AS (" +
                "  SELECT TOP 20 * FROM Courses " +
                "  WHERE name LIKE ? AND status = 'active' AND quantity > 0 " +
                "  ORDER BY CONVERT(DATE, startDate) DESC" +
                ") " +
                "SELECT * FROM TopCourses " +
                "ORDER BY CONVERT(DATE, startDate) DESC " +
                "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY")) {
            
            stm.setString(1, "%" + searchValue + "%");
            stm.setInt(2, offset);
            stm.setInt(3, pageSize);
            
            try (ResultSet rs = stm.executeQuery()) {
                List<Course> courses = new ArrayList<>();
                while (rs.next()) {
                    courses.add(new Course(
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("tuitionFee"),
                        rs.getString("startDate"),
                        rs.getString("endDate"),
                        rs.getString("category")
                    ));
                }
                return courses;
            }
        }
    }

    public int getSearchTotalPages(String searchValue) throws SQLException {
        try (Connection con = DBUtils.makeConnection();
             PreparedStatement stm = con.prepareStatement(
                "SELECT COUNT(*) FROM (" +
                "  SELECT TOP 20 * FROM Courses " +
                "  WHERE name LIKE ? AND status = 'active' AND quantity > 0" +
                ") as TopCourses")) {
            
            stm.setString(1, "%" + searchValue + "%");
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    int totalRecords = rs.getInt(1);
                    return (int) Math.ceil(totalRecords / 5.0);
                }
                return 0;
            }
        }
    }

    public boolean isDuplicateName(String name) throws SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "SELECT name FROM Courses WHERE name = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, name);
                rs = stm.executeQuery();
                return rs.next();
            }
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return false;
    }

    public Course getCourseByName(String name) throws SQLException {
        try (Connection con = DBUtils.makeConnection();
             PreparedStatement stm = con.prepareStatement(
                "SELECT * FROM Courses WHERE name = ? AND status = 'active' AND quantity > 0")) {
            
            stm.setString(1, name);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    return new Course(
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("tuitionFee"),
                        rs.getString("startDate"),
                        rs.getString("endDate"),
                        rs.getString("category")
                    );
                }
                return null;
            }
        }
    }

    public List<Course> getActiveCourses() throws SQLException {
        List<Course> activeCourses = new ArrayList<>();
        String sql = "SELECT name, description, tuitionFee, startDate, endDate, category " +
                     "FROM Courses " +
                     "WHERE status = 'active' AND quantity > 0 " +
                     "ORDER BY name";
        
        System.out.println("\n=== CourseDAO.getActiveCourses ===");
        System.out.println("Executing SQL: " + sql);
        
        try (Connection con = DBUtils.makeConnection();
             PreparedStatement stm = con.prepareStatement(sql);
             ResultSet rs = stm.executeQuery()) {
            
            System.out.println("Database connection successful");
            int count = 0;
            
            while (rs.next()) {
                count++;
                String name = rs.getString("name");
                String description = rs.getString("description");
                double tuitionFee = rs.getDouble("tuitionFee");
                String startDate = rs.getString("startDate");
                String endDate = rs.getString("endDate");
                String category = rs.getString("category");
                
                System.out.println("Found course " + count + ": " + name);
                
                Course course = new Course(name, description, tuitionFee, 
                                        startDate, endDate, category);
                activeCourses.add(course);
            }
            
            System.out.println("Total active courses found: " + count);
        } catch (SQLException e) {
            System.out.println("Error in getActiveCourses: " + e.getMessage());
            throw e;
        }
        
        return activeCourses;
    }
} 