package dgroomes.hql.db;

import jakarta.persistence.*;

@Entity
@Table(name = "observations")
public class Observation {

  @Id
  private int id;
  private String observation;

  @ManyToOne
  @JoinColumn(name = "type")
  private ObservationType type;

  public Observation() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getObservation() {
    return observation;
  }

  public void setObservation(String observation) {
    this.observation = observation;
  }

  public ObservationType getType() {
    return type;
  }

  public void setType(ObservationType type) {
    this.type = type;
  }
}
