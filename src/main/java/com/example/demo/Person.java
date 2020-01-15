package com.example.demo;

public class Person {

	private String id;
	private String name;
	private int age;

	public Person(String name, int age) {
		System.out.println("★★★★★ Person called." + name + ", " + age);
		this.name = name;
		this.age = age;
	}

	public String getId() {
		System.out.println("★★★★★ Person_getId called." + id);
		return id;
	}

	public void setId(String id) {
		System.out.println("★★★★★ Person_setId called." + id);
		this.id = id;
	}

	public String getName() {
		System.out.println("★★★★★ Person_getName called." + name);
		return name;
	}

	public void setName(String name) {
		System.out.println("★★★★★ Person_setName called." + name);
		this.name = name;
	}

	public int getAge() {
		System.out.println("★★★★★ Person_getAge called." + age);
		return age;
	}

	public void setAge(int age) {
		System.out.println("★★★★★ Person_setAge called." + age);
		this.age = age;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", age=" + age + "]";
	}

}
