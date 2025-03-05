package com.budget.app.entity;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name ="groups")
@Component
public class Group
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private int id;

    @Column(name = "group_name")
    private String groupName;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "group_type_id")
    private GroupType groupType;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "budget_id")
    private Budget budget;

    @OneToMany(mappedBy = "group",cascade = CascadeType.ALL)
    private List<LineItem> lineItems = new ArrayList<LineItem>();

    public Group() {}

    public Group(int id, String groupName, GroupType groupType, Budget budget, List<LineItem> lineItems)
    {
        this.id = id;
        this.groupName = groupName;
        this.groupType = groupType;
        this.budget = budget;
        this.lineItems = lineItems;
    }

    public int getId() {return id;}

    public String getGroupName() {return groupName;}

    public GroupType getGroupType() {return groupType;}

    public Budget getBudget() {return budget;}

    public List<LineItem> getLineItems() {return lineItems;}

    public void setId(int id) {this.id = id;}

    public void setGroupName(String groupName) {this.groupName = groupName;}

    public void setGroupType(GroupType groupType) {this.groupType = groupType;}

    public void setBudget(Budget budget) {this.budget = budget;}

    public void setLineItems(List<LineItem> lineItems) {this.lineItems = lineItems;}
}
