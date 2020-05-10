package com.xisra.arch.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "properties")
@Data
public class Properties implements Serializable{

	@Id
	private Long id;

	private String application;
	private String profile;
	private String label;

	@Column(name = "key")
	private String key;
	@Column(name = "value")
	private String value;

	@CreatedDate
	private Date createdAt;
}