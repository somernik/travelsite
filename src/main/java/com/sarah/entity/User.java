package com.sarah.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * A class to represent a user.
 * @author somernik
 */
@Entity
@Table(name = "user")
public class User extends BaseEntity<Long> {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name="increment", strategy="increment")
    @Column(name = "id")
    private Long userid;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<ReviewEntity> reviews = new HashSet<ReviewEntity>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name="userlocation", joinColumns = {
            @JoinColumn(name="User_id")},
            inverseJoinColumns = {
            @JoinColumn(name="Location_id")
            })
    private Set<LocationEntity> locations = new HashSet<LocationEntity>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.user", cascade=CascadeType.ALL)
    private Set<UserPrivilegeEntity> userPrivileges = new HashSet<UserPrivilegeEntity>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "taglocationuser", joinColumns = {
            @JoinColumn(name = "User_id") },
            inverseJoinColumns = {
            @JoinColumn(name = "tagLocation_id") })
    private Set<TaglocationEntity> taglocations = new HashSet<TaglocationEntity>();

    /**
     * Instantiates a new User.
     */
    public User() {}

    /**
     * Instantiates a new User no id
     *
     * @param firstName the first name
     * @param lastName  the last name
     */
    public User(String firstName, String lastName, String email, String password, String userName) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.userName = userName;

        createContributorPrivilege();
    }

    /**
     * Instantiates a new User.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @param userid    the userid
     */
    public User(String firstName, String lastName, Long userid, String email, String password, String userName) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
        this.userid = userid;
        this.email = email;
        this.password = password;
        this.userName = userName;

        createContributorPrivilege();
    }

    public User(String firstName, String lastName, String email, String password, String userName, Set<ReviewEntity> reviews) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.reviews = reviews;

        createContributorPrivilege();
    }

    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets first name.
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets last name.
     *
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets userid.
     *
     * @return the userid
     */
    public Long getId() {
        return userid;
    }

    /**
     * Sets userid.
     *
     * @param userid the userid
     */
    public void setId(Long userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<ReviewEntity> getReviews() {
        return reviews;
    }

    public void setReviews(Set<ReviewEntity> reviews) {
        this.reviews = reviews;
    }

    public Set<LocationEntity> getLocations() {
        return locations;
    }

    public void setLocations(Set<LocationEntity> locations) {
        this.locations = locations;
    }

    public Set<UserPrivilegeEntity> getUserPrivileges() {
        return userPrivileges;
    }

    public void setUserPrivileges(Set<UserPrivilegeEntity> userPrivileges) {
        this.userPrivileges = userPrivileges;
    }

    public Set<TaglocationEntity> getTaglocations() {
        return taglocations;
    }

    public void setTaglocations(Set<TaglocationEntity> taglocationEntities) {
        this.taglocations = taglocationEntities;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userid='" + userid + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (userid != user.userid) return false;
        if (!firstName.equals(user.firstName)) return false;
        if (!lastName.equals(user.lastName)) return false;
        if (!email.equals(user.email)) return false;
        if (!password.equals(user.password)) return false;
        return userName.equals(user.userName);
    }

    private void createContributorPrivilege() {
        PrivilegeEntity privilegeEntity = new PrivilegeEntity(2, "Contributor");

        UserPrivilegeEntity userPrivilege = new UserPrivilegeEntity(this.getUserName(), this, privilegeEntity);

        this.getUserPrivileges().add(userPrivilege);
    }
}