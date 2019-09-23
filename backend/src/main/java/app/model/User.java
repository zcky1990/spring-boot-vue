package app.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


import org.springframework.data.annotation.Id;

public class User{
	  	@Id
	    private Long id;
	 
	    @NotBlank
	    @Size(min=3, max = 50)
	    private String name;
	 
	    @NotBlank
	    @Size(min=3, max = 50)
	    private String username;
	 
	    @NotBlank
	    @Size(max = 50)
	    @Email
	    private String email;
	 
	    @NotBlank
	    @Size(min=6, max = 100)
	    private String password;
	 
	    public User(String name, String username, String email, String password) {
	        this.name = name;
	        this.username = username;
	        this.email = email;
	        this.password = password;
	    }
	 
	    public Long getId() {
	        return id;
	    }
	 
	    public void setId(Long id) {
	        this.id = id;
	    }
	 
	    public String getUsername() {
	        return username;
	    }
	 
	    public void setUsername(String username) {
	        this.username = username;
	    }
	 
	    public String getName() {
	        return name;
	    }
	 
	    public void setName(String name) {
	        this.name = name;
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
	}
