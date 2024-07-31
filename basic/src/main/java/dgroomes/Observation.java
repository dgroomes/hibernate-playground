package dgroomes;

import jakarta.persistence.*;

@Entity
@Table(name = "observations")
public class Observation {

  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private int id;
  private String observation;

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
}
