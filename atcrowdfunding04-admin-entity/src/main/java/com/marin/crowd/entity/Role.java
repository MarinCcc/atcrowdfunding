package com.marin.crowd.entity;

public class Role {
    private Integer id;

    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Role() {}
    @Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + "]";
	}

	public Role(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}