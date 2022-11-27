package com.example.jpa11.domain.member;

import com.example.jpa11.domain.address.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findAllByName(member.getName());

        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public Member findOne(Long memberId) {
        return memberRepository.findMemberById(memberId);
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public List<Member> findByName(String name) {
        return memberRepository.findAllByName(name);
    }

    public void memberSetting() {
        Member mem1 = new Member();
        mem1.setName("sim"); mem1.setAddress(new Address("fds", "gdsv", "dfadm"));

        Member mem2 = new Member();
        mem2.setName("kim"); mem2.setAddress(new Address("kk", "fd", "gd"));

        try {
            join(mem1);
            join(mem2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
