package com.tbland.whale.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name="role_details",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"account_id","role_id"})
                // ngan du lieu trung
        }
)
public class RoleDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    //1 account co the co nhieu roledetails thong qua account_id
    @JoinColumn(name="account_id")
    private Account acount;
    @ManyToOne
    //1 role co the co nhieu roledetaials thong qua role_id
    @JoinColumn(name="role_id")
    private Role role;
}
