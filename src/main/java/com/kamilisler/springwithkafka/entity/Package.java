package com.kamilisler.springwithkafka.entity;
import javax.persistence.Column;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "package")
public class Package {
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "order_id")
    private Long order_id;
    @Column(name = "user_id")
    private Long user_id;
    @Column(name = "customer_id")
    private Long customer_id;
    @Column(name = "store_id")
    private Long store_id;
    @Column(name = "origin_address_id")
    private Long origin_address_id;

    @Column(name = "type")
    private String type; // enum için sor?

    @Column(name = "eta")
    private Integer eta;

    @Column(name = "delivery_date")
    private Date delivery_date;
    @Column(name = "created_at")
    private Date created_at;
    @Column(name = "waiting_for_assignment_at")
    private Date waiting_for_assignment_at;
    @Column(name = "collected_at")
    private Date collected_at;
    @Column(name = "arrival_for_pickup_at")
    private Date arrival_for_pickup_at;
    @Column(name = "picked_up_at")
    private Date picked_up_at;
    @Column(name = "in_delivery_at")
    private Date in_delivery_at;
    @Column(name = "arrival_for_delivery_at")
    private Date arrival_for_delivery_at;
    @Column(name = "completed_at")
    private Date completed_at;
    @Column(name = "last_updated_at")
    private Date last_updated_at;
    @Column(name = "cancelled_at")
    private Date cancelled_at;

    @Column(name = "collected")
    private Boolean collected;
    @Column(name = "cancelled")
    private Boolean cancelled;
    @Column(name = "cancel_reason")
    private String cancel_reason;
    @Column(name = "status")
    private String status; // enum?
    @Column(name = "reassigned")
    private Boolean reassigned; // ?

    public Package(Long id, Long order_id, Long user_id, Long customer_id, Long store_id, Long origin_address_id, String type, Integer eta, Date delivery_date, Date created_at, Date waiting_for_assignment_at, Date collected_at, Date arrival_for_pickup_at, Date picked_up_at, Date in_delivery_at, Date arrival_for_delivery_at, Date completed_at, Date last_updated_at, Date cancelled_at, Boolean collected, Boolean cancelled, String cancel_reason, String status, Boolean reassigned) {
        this.id = id;
        this.order_id = order_id;
        this.user_id = user_id;
        this.customer_id = customer_id;
        this.store_id = store_id;
        this.origin_address_id = origin_address_id;
        this.type = type;
        this.eta = eta;
        this.delivery_date = delivery_date;
        this.created_at = created_at;
        this.waiting_for_assignment_at = waiting_for_assignment_at;
        this.collected_at = collected_at;
        this.arrival_for_pickup_at = arrival_for_pickup_at;
        this.picked_up_at = picked_up_at;
        this.in_delivery_at = in_delivery_at;
        this.arrival_for_delivery_at = arrival_for_delivery_at;
        this.completed_at = completed_at;
        this.last_updated_at = last_updated_at;
        this.cancelled_at = cancelled_at;
        this.collected = collected;
        this.cancelled = cancelled;
        this.cancel_reason = cancel_reason;
        this.status = status;
        this.reassigned = reassigned;
    }

    public Package() {

    }


    @Override
    public String toString() {
        return "Package{" +
                "id=" + id +
                ", order_id=" + order_id +
                ", user_id=" + user_id +
                ", customer_id=" + customer_id +
                ", store_id=" + store_id +
                ", origin_address_id=" + origin_address_id +
                ", type='" + type + '\'' +
                ", eta=" + eta +
                ", delivery_date=" + delivery_date +
                ", created_at=" + created_at +
                ", waiting_for_assignment_at=" + waiting_for_assignment_at +
                ", collected_at=" + collected_at +
                ", arrival_for_pickup_at=" + arrival_for_pickup_at +
                ", picked_up_at=" + picked_up_at +
                ", in_delivery_at=" + in_delivery_at +
                ", arrival_for_delivery_at=" + arrival_for_delivery_at +
                ", completed_at=" + completed_at +
                ", last_updated_at=" + last_updated_at +
                ", cancelled_at=" + cancelled_at +
                ", collected=" + collected +
                ", cancelled=" + cancelled +
                ", cancel_reason='" + cancel_reason + '\'' +
                ", status='" + status + '\'' +
                ", reassigned=" + reassigned +
                '}';
    }

    public Long getId() {
        return id;
    }
    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Long customer_id) {
        this.customer_id = customer_id;
    }

    public Long getStore_id() {
        return store_id;
    }

    public void setStore_id(Long store_id) {
        this.store_id = store_id;
    }

    public Long getOrigin_address_id() {
        return origin_address_id;
    }

    public void setOrigin_address_id(Long origin_address_id) {
        this.origin_address_id = origin_address_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getEta() {
        return eta;
    }

    public void setEta(Integer eta) {
        this.eta = eta;
    }

    public Date getDelivery_date() {
        return delivery_date;
    }

    public void setDelivery_date(Date delivery_date) {
        this.delivery_date = delivery_date;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getWaiting_for_assignment_at() {
        return waiting_for_assignment_at;
    }

    public void setWaiting_for_assignment_at(Date waiting_for_assignment_at) {
        this.waiting_for_assignment_at = waiting_for_assignment_at;
    }

    public Date getCollected_at() {
        return collected_at;
    }

    public void setCollected_at(Date collected_at) {
        this.collected_at = collected_at;
    }

    public Date getArrival_for_pickup_at() {
        return arrival_for_pickup_at;
    }

    public void setArrival_for_pickup_at(Date arrival_for_pickup_at) {
        this.arrival_for_pickup_at = arrival_for_pickup_at;
    }

    public Date getPicked_up_at() {
        return picked_up_at;
    }

    public void setPicked_up_at(Date picked_up_at) {
        this.picked_up_at = picked_up_at;
    }

    public Date getIn_delivery_at() {
        return in_delivery_at;
    }

    public void setIn_delivery_at(Date in_delivery_at) {
        this.in_delivery_at = in_delivery_at;
    }

    public Date getArrival_for_delivery_at() {
        return arrival_for_delivery_at;
    }

    public void setArrival_for_delivery_at(Date arrival_for_delivery_at) {
        this.arrival_for_delivery_at = arrival_for_delivery_at;
    }

    public Date getCompleted_at() {
        return completed_at;
    }

    public void setCompleted_at(Date completed_at) {
        this.completed_at = completed_at;
    }

    public Date getLast_updated_at() {
        return last_updated_at;
    }

    public void setLast_updated_at(Date last_updated_at) {
        this.last_updated_at = last_updated_at;
    }

    public Date getCancelled_at() {
        return cancelled_at;
    }

    public void setCancelled_at(Date cancelled_at) {
        this.cancelled_at = cancelled_at;
    }

    public Boolean getCollected() {
        return collected;
    }

    public void setCollected(Boolean collected) {
        this.collected = collected;
    }

    public Boolean getCancelled() {
        return cancelled;
    }

    public void setCancelled(Boolean cancelled) {
        this.cancelled = cancelled;
    }

    public String getCancel_reason() {
        return cancel_reason;
    }

    public void setCancel_reason(String cancel_reason) {
        this.cancel_reason = cancel_reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getReassigned() {
        return reassigned;
    }

    public void setReassigned(Boolean reassigned) {
        this.reassigned = reassigned;
    }


}
