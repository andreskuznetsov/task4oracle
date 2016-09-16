package es.ahs.oracle_task.model;

import org.springframework.stereotype.Service;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.io.Serializable;

/**
 * Created by akuznetsov on 09.09.2016.
 */
@NamedQueries({
    @NamedQuery(name = Country.GET_ALL, query = "SELECT c FROM Country c"),
    @NamedQuery(name = Country.GET_BY_NAME, query = "SELECT c FROM Country c WHERE c.name=?1")

})

@Entity
public class Country extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String GET_ALL = "Country.getAll";
    public static final String GET_BY_NAME = "Country.getByName";

    private String name;
    private String pathId;

    public Country() {
    }

    public Country(String name, String pathId) {
        this.name = name;
        this.pathId = pathId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPathId() {
        return pathId;
    }

    public void setPathId(String pathId) {
        this.pathId = pathId;
    }

    @Override
    public String toString() {
        return "\nCountry{" +
                "name='" + name + '\'' +
                ", pathId='" + pathId + '\'' +
                '}';
    }
}
