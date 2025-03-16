package ducpt.registration.course;

public class CourseInsertError {
    private String nameLengthErr;
    private String tuitionFeeErr;
    private String dateErr;
    private String categoryErr;
    private String duplicateNameErr;

    public String getNameLengthErr() {
        return nameLengthErr;
    }

    public void setNameLengthErr(String nameLengthErr) {
        this.nameLengthErr = nameLengthErr;
    }

    public String getTuitionFeeErr() {
        return tuitionFeeErr;
    }

    public void setTuitionFeeErr(String tuitionFeeErr) {
        this.tuitionFeeErr = tuitionFeeErr;
    }

    public String getDateErr() {
        return dateErr;
    }

    public void setDateErr(String dateErr) {
        this.dateErr = dateErr;
    }

    public String getCategoryErr() {
        return categoryErr;
    }

    public void setCategoryErr(String categoryErr) {
        this.categoryErr = categoryErr;
    }

    public String getDuplicateNameErr() {
        return duplicateNameErr;
    }

    public void setDuplicateNameErr(String duplicateNameErr) {
        this.duplicateNameErr = duplicateNameErr;
    }
} 