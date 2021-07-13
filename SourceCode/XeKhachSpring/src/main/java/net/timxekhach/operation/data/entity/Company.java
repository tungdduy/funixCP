package net.timxekhach.operation.data.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import net.timxekhach.operation.data.mapped.Company_MAPPED;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Company extends Company_MAPPED {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;
}