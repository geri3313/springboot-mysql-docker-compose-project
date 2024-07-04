package net.codejava.model;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Entity
@Table(name = "users")
public class User {

	public enum Role {
		USER,
		ADMIN
	}

	@Enumerated(EnumType.STRING)
	private Role role = Role.USER;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true, length = 45)
	private String email;

	@Column(name = "password", nullable = false, length = 20)
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).+$", message = "Password must contain at least one uppercase letter, one lowercase letter, and one special character.")
	private String password;

	@Column(name = "first_name", nullable = false, length = 20)
	@Pattern(regexp = "^[a-zA-Z]+$", message = "First name must contain only letters")
	private String firstName;

	@Column(name = "last_name", nullable = false, length = 20)
	@Pattern(regexp = "^[a-zA-Z]+$", message = "First name must contain only letters")
	private String lastName;

	@Pattern(regexp = "\\+3556\\d{8}", message = "Phone number must start with +35569 and be followed by 7 digits.")
	@Column(name = "phone_number", length = 15)
	private String phoneNumber;

	@Column(name = "fathers_name", length = 45)
	@Pattern(regexp = "^[a-zA-Z]+$", message = "First name must contain only letters")
	private String fathersName;

	// New field to indicate admin status
	@Column(name = "is_admin")
	private boolean isAdmin;

	@Column(name = "profilePicture")
	private String profilePicture;

	// Getters and setters for isAdmin field
	public boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	// Getters and setters for other fields
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getFathersName() {
		return fathersName;
	}

	public void setFathersName(String fathersName) {
		this.fathersName = fathersName;
	}

	public void setProfilePicture(String originalFileName) {
		this.profilePicture = originalFileName;
	}

	public String getProfilePicture() {
		return profilePicture;
	}
}
