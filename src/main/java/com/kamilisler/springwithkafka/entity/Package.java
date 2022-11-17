package com.kamilisler.springwithkafka.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "package")
public class Package {
    @Id
    @NotNull
    private Long id;
    @NotNull
    private Long order_id;
    @NotNull
    private Long user_id;
    @NotNull
    private Long customer_id;
    @NotNull
    private Long store_id;
    @NotNull
    private Long origin_address_id;
    @NotNull
    private String type;

    private Integer eta;
    private Date delivery_date;
    private Date created_at;
    private Date waiting_for_assignment_at;
    private Date collected_at;
    private Date arrival_for_pickup_at;
    private Date picked_up_at;
    private Date in_delivery_at;
    private Date arrival_for_delivery_at;
    private Date completed_at;
    private Date last_updated_at;
    private Date cancelled_at;
    private Boolean collected;
    private Boolean cancelled;
    private String cancel_reason;
    private String status;
    private Boolean reassigned;

}
