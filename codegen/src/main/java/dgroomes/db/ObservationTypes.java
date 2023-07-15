package dgroomes.db;
// Generated Jul 15, 2023, 12:14:27 AM by Hibernate Tools 6.2.6.Final


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * ObservationTypes generated by hbm2java
 */
@Entity
@Table(name="observation_types"
    ,schema="public"
)
public class ObservationTypes  implements java.io.Serializable {


     private int id;
     private String description;
     private Set<Observations> observationses = new HashSet<Observations>(0);

    public ObservationTypes() {
    }

    public ObservationTypes(int id, String description, Set<Observations> observationses) {
       this.id = id;
       this.description = description;
       this.observationses = observationses;
    }
   
     @Id 

    
    @Column(name="id", unique=true, nullable=false)
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    
    @Column(name="description", nullable=false)
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="observationTypes")
    public Set<Observations> getObservationses() {
        return this.observationses;
    }
    
    public void setObservationses(Set<Observations> observationses) {
        this.observationses = observationses;
    }




}


