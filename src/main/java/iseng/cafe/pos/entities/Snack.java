package iseng.cafe.pos.entities;

import javax.persistence.*;

@Table
@Entity
public class Snack extends AbstractEntity<Integer>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "snack_type_id", nullable = false)
    private SnackType snackType;

    @ManyToOne
    @JoinColumn(name = "snack_category_id", nullable = false)
    private SnackCategory snackCategory;

    private Double price;

    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
