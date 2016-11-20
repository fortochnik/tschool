package tstore.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by mipan on 29.09.2016.
 */
@Entity
@Table(name = "country")
public class CountryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",  unique = true, nullable = false)
    private int id;

    @Column(name = "country", unique = true)
    private String country;

    @OneToMany(mappedBy = "country")
    private Set<AddressEntity> addressEntities;

    public CountryEntity() {
    }

    public CountryEntity(String country) {
        this.country = country;
    }

    public Set<AddressEntity> getAddressEntities() {
        return addressEntities;
    }

    public void setAddressEntities(Set<AddressEntity> addressEntities) {
        this.addressEntities = addressEntities;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return country;
    }
}
