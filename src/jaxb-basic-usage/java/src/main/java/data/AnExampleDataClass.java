// The schemagen plugin will fail in post processing on classes with no package.

package data;

import java.util.Map;
import java.util.Set;
import java.util.List;

import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElementWrapper;

/**
 * An example class.
 *
 * Contains various field types to illustrate the mapping.
 */
@XmlRootElement
@XmlType (propOrder = {})
public class AnExampleDataClass {

    @XmlElement
    public int anIntField;

    @XmlElement
    public float aFloatField;

    @XmlElement
    public double aDoubleField;


    @XmlElement
    public Integer aBoxedIntField;


    @XmlElement (required = true)
    public String aRequiredStringField;

    @XmlElement (required = false)
    public String anOptionalStringField;

    @XmlElement (defaultValue = "default")
    public String aStringFieldWithDefaultValue;


    /**
     * An array without a wrapper.
     */
    @XmlElement
    public int [] anArrayWithoutAWrapper;

    /**
     * An array with a wrapper.
     */
    @XmlElementWrapper
    @XmlElement (name = "anArrayElement")
    public int [] anArrayWithAWrapper;


    @XmlElement
    public List<AnExampleDataClass> aListElement;

    @XmlElement
    public Set<AnExampleDataClass> aSetElement;

    @XmlElement
    public Map<Integer, AnExampleDataClass> aMapElement;

}
