package com.zht.ztoolkit.simplelistview;

public class Person {
	public int iconId;
	public String name;  
    public int age;  
    public String email;  
    public String address;  
   
    public Person(int iconId, String name, int age, String email, String address) {  
        super();  
        this.iconId = iconId;
        this.name = name;  
        this.age = age;  
        this.email = email;  
        this.address = address;  
    }  
   
    @Override  
    public String toString() {  
        return "Person [name=" + name + ", age=" + age + ", email=" + email  
                + ", address=" + address + "]";  
    }  
}
