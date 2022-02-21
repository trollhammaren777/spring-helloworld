package boot.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity(name = "User")
@Table(name = "users")
@Scope("session")
@JsonDeserialize(as = User.class)
@JsonPropertyOrder({ "id", "firstName", "lastName", "nickname", "age", "email", "password", "roles" })
public class User implements UserDetails, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonProperty("id")
    private Long id;

    @Column(name = "first_name")
    @NotEmpty(message = "First name shouldn't be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    @JsonProperty("firstName")
    private String firstName;

    @Column(name = "last_name")
    @NotEmpty(message = "Last name shouldn't be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    @JsonProperty("lastName")
    private String lastName;

    @Column(name = "nickname", unique = true)
    @NotEmpty(message = "Nickname shouldn't be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    @JsonProperty("nickname")
    private String nickname; // уникальное значение, исп-ся для того, чтобы UserServiceImpl, имплементирующий
    // UserDetailsService, находил пользователя по уникальному значению

    @Column(name = "age")
    @Min(value = 0, message = "Age should be greater than 0")
    @JsonProperty("age")
    private int age;

    @Column(name = "email")
    @NotEmpty(message = "Email shouldn't be empty")
    @Email(message = "Please enter valid email address. Email template: nameofyourmailbox@mail.com")
    @JsonProperty("email")
    private String email;

    @Column(name = "password")
    @NotEmpty(message = "Password shouldn't be empty")
    @Size(min = 4, message = "Password length should be greater or equal 4 characters")
    @JsonProperty("password")
    private String password;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = {@JoinColumn(name = "user_id", unique = false, updatable = true)},
            inverseJoinColumns = @JoinColumn(name = "role_id", unique = false, updatable = true)
    )
    @JsonProperty("roles")
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private List<Role> roles = new ArrayList<>();

    public User() {

    }

    public User(Long id, String firstName, String lastName, String nickname,
                int age, String email, String password, List<Role> roles) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickname = nickname;
        this.age = age;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String name) {
        this.firstName = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        if (roles == null) {
            return;
        }
        this.roles = roles;
    }

    public boolean isUserContainingPassedInMethodRole(String roleName) {
        for (Role role: roles) {
            if (role.getRoleName().equals(roleName)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return getNickname();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Role> roles = this.getRoles();
        if (roles == null) {
            return null;
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
        }

        return authorities;
    }
}