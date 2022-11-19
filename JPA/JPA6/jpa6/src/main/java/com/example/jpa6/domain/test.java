package com.example.jpa6.domain;

public class test {
    public static void main(String[] args) {
        MemberProduct mp1 = new MemberProduct();
        MemberProduct mp2 = new MemberProduct();

        Member m1 = new Member("m1");
        Member m2 = new Member("m2");

        Product p1 = new Product("p1", "p1");
        Product p2 = new Product("p2", "p2");

        mp1.setMember(m1); mp1.setProduct(p1);
        mp2.setMember(m1); mp2.setProduct(p1);

        System.out.println(mp1.hashCode());
        System.out.println(mp2.hashCode());

        System.out.println(mp1.equals(mp2));

    }
}
