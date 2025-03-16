/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ducpt.entity;

/**
 *
 * @author LENOVO-DUCKY
 */
public class Course {
    private String courseId;
    private String courseName;
    private double price;
    private String description;
    private double tuitionFee;
    private String startDate;
    private String endDate;
    private String category;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTuitionFee() {
        return tuitionFee;
    }

    public void setTuitionFee(double tuitionFee) {
        this.tuitionFee = tuitionFee;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Course(String courseId, String courseName, double price, String description, double tuitionFee, String startDate, String endDate, String category) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.price = price;
        this.description = description;
        this.tuitionFee = tuitionFee;
        this.startDate = startDate;
        this.endDate = endDate;
        this.category = category;
    }

    public Course(String courseName, String description, double tuitionFee, 
                 String startDate, String endDate, String category) {
        this.courseName = courseName;
        this.description = description;
        this.tuitionFee = tuitionFee;
        this.startDate = startDate;
        this.endDate = endDate;
        this.category = category;
    }

    @Override
    public String toString() {
        return "Course{" + "courseId=" + courseId + ", courseName=" + courseName + ", price=" + price + ", description=" + description + ", tuitionFee=" + tuitionFee + ", startDate=" + startDate + ", endDate=" + endDate + ", category=" + category + '}';
    }
    
    
    
}
