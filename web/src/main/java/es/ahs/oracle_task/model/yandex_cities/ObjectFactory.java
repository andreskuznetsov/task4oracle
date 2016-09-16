
package es.ahs.oracle_task.model.yandex_cities;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the es.ahs.oracle_task.util package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Cities_QNAME = new QName("", "cities");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: es.ahs.oracle_task.util
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CitiesType }
     * 
     */
    public CitiesType createCitiesType() {
        return new CitiesType();
    }

    /**
     * Create an instance of {@link YandexCity }
     * 
     */
    public YandexCity createCityType() {
        return new YandexCity();
    }

    /**
     * Create an instance of {@link YandexCountry }
     * 
     */
    public YandexCountry createCountryType() {
        return new YandexCountry();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CitiesType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "cities")
    public JAXBElement<CitiesType> createCities(CitiesType value) {
        return new JAXBElement<CitiesType>(_Cities_QNAME, CitiesType.class, null, value);
    }

}
