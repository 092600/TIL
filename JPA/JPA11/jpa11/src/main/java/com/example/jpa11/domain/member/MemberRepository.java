package com.example.jpa11.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findMemberById(Long id);

    List<Member> findAll();

    List<Member> findAllByName(String name);
}
