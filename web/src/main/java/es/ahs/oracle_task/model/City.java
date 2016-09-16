package es.ahs.oracle_task.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by akuznetsov on 07.09.2016.
 */
@NamedQueries({
        @NamedQuery(name = City.GET_ALL, query = "SELECT c FROM City c"),
        @NamedQuery(name = City.GET_BY_NAME, query = "SELECT c FROM City c WHERE c.name=?1"),
        @NamedQuery(name = City.GET_BY_PATHNAME, query = "SELECT c FROM City c WHERE c.pathName=?1")
})
@Entity
public class City extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String GET_ALL = "City.getAll";
    public static final String GET_BY_NAME = "City.getByName";
    public static final String GET_BY_PATHNAME = "City.getByPathName";

    private String name;

    @OneToOne
    private Country country;
    private String partOfTerritory;
    private String pathName;

    public City() {
    }

    public City(String name, String pathName) {
        this.pathName = pathName;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getPartOfTerritory() {
        return partOfTerritory;
    }

    public void setPartOfTerritory(String partOfTerritory) {
        this.partOfTerritory = partOfTerritory;
    }

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    @Override
    public String toString() {
        return "\nCity{" +
                "name='" + name + '\'' +
                ", pathName='" + pathName + '\'' +
                ", country=" + country +
                ", partOfTerritory='" + partOfTerritory + '\'' +
                '}';
    }
}
