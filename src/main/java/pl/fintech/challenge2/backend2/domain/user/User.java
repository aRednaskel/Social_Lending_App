package pl.fintech.challenge2.backend2.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.fintech.challenge2.backend2.domain.inquiry.Inquiry;
import pl.fintech.challenge2.backend2.domain.loan.Loan;
import pl.fintech.challenge2.backend2.domain.message.Message;
import pl.fintech.challenge2.backend2.domain.offer.Offer;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Email(message = "Wprowadź poprawny email")
    @Column(unique = true)
    private String email;

    @JsonIgnore
    private String password;

    private String name;

    private String surname;

    private String phone;

    private String accountNumber;

    @ManyToMany(targetEntity = Role.class, fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @OneToMany(
            mappedBy = "borrower",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Loan> borrowerLoans;

    @OneToMany(
            mappedBy = "lender",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Loan> lenderLoans;

    @OneToMany(
            mappedBy = "lender",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Offer> offers;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Message> messages;

    @JsonIgnore
    @Override
    public String getUsername() {
        return getEmail();
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return false;
    }
}
