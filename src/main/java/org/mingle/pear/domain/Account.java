package org.mingle.pear.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.mingle.pear.persistence.BaseDomain;

@Entity
@Table(name = "t_account")
@EqualsAndHashCode(callSuper = false)
public @Data class Account extends BaseDomain<Long> {
	private static final long serialVersionUID = -5113175753421859746L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Version
	@Column(name = "version")
	private Integer version;

	@NotNull
	@Column(name = "name", length = 30, nullable = false, unique = true)
	private String name;

	@Column(name = "age")
	private int age;

	@Override
	public int compareTo(BaseDomain<?> o) {
		return 0;
	}
	
}
