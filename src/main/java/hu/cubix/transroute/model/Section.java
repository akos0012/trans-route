package hu.cubix.transroute.model;

import jakarta.persistence.*;

@Entity
public class Section {
    @Id
    @GeneratedValue
    private long id;

    private int sectionOrder;

    @ManyToOne
    private TransportPlan transportPlan;

    @OneToOne
    private Milestone startMilestone;

    @OneToOne
    private Milestone endMilestone;

    public Section() {
    }

    public Section(TransportPlan transportPlan, Milestone startMilestone, Milestone endMilestone) {
        this.sectionOrder = 0;
        this.transportPlan = transportPlan;
        this.startMilestone = startMilestone;
        this.endMilestone = endMilestone;
    }

    public Section(TransportPlan transportPlan, Milestone startMilestone, Milestone endMilestone, int sectionOrder) {
        this.sectionOrder = sectionOrder;
        this.transportPlan = transportPlan;
        this.startMilestone = startMilestone;
        this.endMilestone = endMilestone;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getSectionOrder() {
        return sectionOrder;
    }

    public void setSectionOrder(int sectionOrder) {
        this.sectionOrder = sectionOrder;
    }

    public TransportPlan getTransportPlan() {
        return transportPlan;
    }

    public void setTransportPlan(TransportPlan transportPlan) {
        this.transportPlan = transportPlan;
    }

    public Milestone getStartMilestone() {
        return startMilestone;
    }

    public void setStartMilestone(Milestone startMilestone) {
        this.startMilestone = startMilestone;
    }

    public Milestone getEndMilestone() {
        return endMilestone;
    }

    public void setEndMilestone(Milestone endMilestone) {
        this.endMilestone = endMilestone;
    }
}
