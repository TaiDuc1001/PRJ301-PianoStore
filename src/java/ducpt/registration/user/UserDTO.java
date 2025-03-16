package ducpt.registration.user;

public class UserDTO {
    private String username;
    private String password;
    private String lastname;
    private String firstname;
    private boolean role;

    public UserDTO(String username, String password, String lastname, String firstname, boolean role) {
        this.username = username;
        this.password = password;
        this.lastname = lastname;
        this.firstname = firstname;
        this.role = role;
    }

    // Keep existing constructors and methods...
} 