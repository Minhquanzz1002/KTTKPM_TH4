package vn.edu.iuh.entity;

import javax.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Permission extends BaseEntity{
    private String name;
    private String key;
}
