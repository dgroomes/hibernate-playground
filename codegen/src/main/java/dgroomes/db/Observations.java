package dgroomes.db;
// Generated Jul 30, 2024, 6:59:46 PM by Hibernate Tools 6.5.2.Final


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Observations generated by hbm2java
 */
@Entity
@Table(name="observations"
    ,schema="public"
)
public class Observations  implements java.io.Serializable {


     private int id;
     private ObservationTypes observationTypes;
     private String observation;

    public Observations() {
    }

    public Observations(int id, ObservationTypes observationTypes, String observation) {
       this.id = id;
       this.observationTypes = observationTypes;
       this.observation = observation;
    }
   
     @Id 

    
    @Column(name="id", unique=true, nullable=false)
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="type", nullable=false)
    public ObservationTypes getObservationTypes() {
        return this.observationTypes;
    }
    
    public void setObservationTypes(ObservationTypes observationTypes) {
        this.observationTypes = observationTypes;
    }

    
    @Column(name="observation", nullable=false)
    public String getObservation() {
        return this.observation;
    }
    
    public void setObservation(String observation) {
        this.observation = observation;
    }




}


