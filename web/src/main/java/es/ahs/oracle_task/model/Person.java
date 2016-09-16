package es.ahs.oracle_task.model;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by akuznetsov on 31.08.2016.
 */
@NamedQueries({
        @NamedQuery(name = Person.DELETE, query = "DELETE FROM Person p WHERE p.id=:id"),
        @NamedQuery(name = Person.BY_NAME, query = "SELECT p FROM Person p WHERE p.name=?1"),
        @NamedQuery(name = Person.ALL_SORTED_BY_ID, query = "SELECT DISTINCT(p) FROM Person p ORDER BY p.id"),
        @NamedQuery(name = Person.ALL_SORTED_BY_NAME, query = "SELECT DISTINCT(p) FROM Person p ORDER BY p.name"),
        @NamedQuery(name = Person.BY_CONSUME_DAY_NUMBER, query = "SELECT p FROM Person p WHERE p.dayOfWeekForSend=?1")
})
@Entity
public class Person extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String DELETE = "Person.delete";
    public static final String ALL_SORTED_BY_ID = "Person.getAllSortedById";
    public static final String ALL_SORTED_BY_NAME = "Person.getAllSortedByName";
    public static final String BY_NAME = "Person.getByName";
    public static final String BY_CONSUME_DAY_NUMBER = "Person.getByDayConsume";

    private String name;
    private String mail;

    /**
     * данные по какому городу требуются
     */
    @OneToOne
    private City prefCity;

    /**
     * По каким дням недели отправлять данные: -1 - не отправлять, 0-6 - вс-сб, 7 - каждый день
     */
    private int dayOfWeekForSend;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastSended;

    @PrePersist
    protected void onCreate() {
        created = new Date();
    }

    public Person() {
    }

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public City getPrefCity() {
        return prefCity;
    }

    public void setPrefCity(City prefCity) {
        this.prefCity = prefCity;
    }

    public int getDayOfWeekForSend() {
        return dayOfWeekForSend;
    }

    public void setDayOfWeekForSend(int dayOfWeekForSend) {
        this.dayOfWeekForSend = dayOfWeekForSend;
    }

    public Date getLastSended() {
        return lastSended;
    }

    public void setLastSended(Date lastSended) {
        this.lastSended = lastSended;
    }

    private String fromDate(Date date) {
        if (date == null) return "<old entry>";
        SimpleDateFormat ft =
                new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a zzz");

        return (ft.format(date));
    }

    @Override
    public String toString() {
        return "\nPerson{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mail=" + mail +
                ", created=" + fromDate(created) +
                "}";
    }
}
