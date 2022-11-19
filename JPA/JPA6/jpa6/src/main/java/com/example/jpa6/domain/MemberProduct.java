package com.example.jpa6.domain;

public class MemberProduct {
    private Member member;
    private Product product;

    private int orderAmount;

    public Member getMember() {
        return member;
    }

    public Product getProduct() {
        return product;
    }


    public void setMember(Member member) {
        this.member = member;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof MemberProductId) {
            MemberProductId mpi = (MemberProductId) o;

            if (mpi.equals(this)) {
                return true;
            }
        }

        return false;
    }


    @Override
    public int hashCode() {
        return member.hashCode() + product.hashCode();
    }


}
