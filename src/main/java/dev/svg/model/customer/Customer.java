package dev.svg.model.customer;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import dev.svg.model.account.Account;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "idCard")
public class Customer {

    @Id
    @Column(name = "id_card")
    @Size(min = 9, max = 9)
    private String idCard;

    @Embedded
    private Name name;

    @Embedded
    private Address address;

    @AttributeOverrides({
            @AttributeOverride(name="city", column = @Column(name = "secondary_city")),
            @AttributeOverride(name="postalCode", column = @Column(name = "secondary_postal_code")),
            @AttributeOverride(name="street", column = @Column(name = "secondary_street")),
            @AttributeOverride(name="streetNumber", column = @Column(name = "secondary_street_number"))
    })
    @Embedded
    private Address secondaryAddress;

    @Email
    @Column(unique = true)
    private String email;

    @NotNull
    @Column(unique = true)
    private String phone;

    @ManyToMany
    @JoinTable(
            name = "customer_account",
            joinColumns = @JoinColumn(name = "id_card"),
            inverseJoinColumns = @JoinColumn(name = "account_number")
    )
    private List<Account> accounts = new ArrayList<>();

    public void addToAccounts(Account account){
        if (accounts == null) {
            accounts = new ArrayList<>();
        }
        accounts.add(account);
    }

}