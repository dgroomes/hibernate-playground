package dgroomes.caching.db;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "observation_types")
public class ObservationType {

  @Id
  private int id;

  private String description;

  public ObservationType() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String observation) {
    this.description = observation;
  }
}
