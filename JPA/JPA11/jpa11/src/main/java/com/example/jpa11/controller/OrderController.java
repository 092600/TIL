package com.example.jpa11.controller;

import com.example.jpa11.domain.address.Address;
import com.example.jpa11.domain.item.Item;
import com.example.jpa11.domain.item.ItemService;
import com.example.jpa11.domain.member.Member;
import com.example.jpa11.domain.member.MemberService;
import com.example.jpa11.domain.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping(value = "/order")
    public String createForm(Model model) {

        memberService.memberSetting();

        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();

        model.addAttribute("members", members);
        model.addAttribute("items", items);

        return "order/createOrderForm";
    }

    @PostMapping(value = "/order")
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count) {

        System.out.println("memberId = " + memberId + ", itemId = " + itemId + ", count = " +count);
        orderService.order(memberId, itemId, count);
        return "redirect:/orders/"+memberId;
    }

    @GetMapping(value = "/orders/{memberId}")
    public String orders(Model model, @PathVariable("memberId") Long memberId) {

        return "order/createOrderForm";
    }
}
