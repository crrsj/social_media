package com.api.sm.entity;

import jakarta.persistence.*;
import org.hibernate.grammars.hql.HqlParser;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;
    private String name;

    public Role(Long roleId, String name) {
        this.roleId = roleId;
        this.name = name;
    }

    public Role() {
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
   public enum Valuie{
        BASIC(2L),
       ADMIN(1L);
        Long roleId;

       Valuie(Long roleId) {
           this.roleId = roleId;
       }
   }
}
