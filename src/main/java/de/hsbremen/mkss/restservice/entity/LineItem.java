package de.hsbremen.mkss.restservice.entity;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.*;


@Entity
public class LineItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NonNull
    @Column(name = "Product_Name")
    private String productName;

    @NonNull
    @Column(name = "Price")
    private float price;

    @NonNull
    @Column(name = "Quantity")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "orderid", referencedColumnName = "id", nullable = false)

    private oorder oorder;


}


