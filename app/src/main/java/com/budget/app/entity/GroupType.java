package com.budget.app.entity;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name ="group_types")
@Component
public class GroupType
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_type_id")
    private int id;

    @Column(name = "group_label")
    private String groupLabel;

    @OneToMany(mappedBy = "groupType",cascade = CascadeType.ALL)
    private List<Group> groups = new ArrayList<Group>();

    public GroupType() {}

    public GroupType(int id, String groupLabel, List<Group> groups)
    {
        this.id = id;
        this.groupLabel = groupLabel;
        this.groups = groups;
    }

    public int getId() {return id;}

    public String getGroupLabel() {return groupLabel;}

    public List<Group> getGroups() {return groups;}

    public void setId(int id) {this.id = id;}

    public void setGroupLabel(String groupLabel) {this.groupLabel = groupLabel;}

    public void setGroups(List<Group> groups) {this.groups = groups;}
}