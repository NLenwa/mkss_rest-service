package de.hsbremen.mkss.restservice.entity;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "oorder")
public class oorder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NonNull
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @Column(name = "date")
    private Date date;

    @NonNull
    @Column(name = "customer_Name")
    private String customerName;

    @OneToMany(mappedBy = "oorder", cascade = CascadeType.ALL, fetch = FetchType.EAGER)

    private Set<LineItem> items;

    public void addLineItem(LineItem item)
    {
        items.add(item);
    }

    public void removeItem(long itemId)
    {
        items.removeIf(item -> item.getId() == itemId);
    }

}


