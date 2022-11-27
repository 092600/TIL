package com.example.jpa11.controller;

import com.example.jpa11.domain.item.Item;
import com.example.jpa11.domain.item.ItemService;
import com.example.jpa11.domain.item.book.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping(value = "/items")
    public String items(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);

        System.out.println(items.size());

        return "items/item";
    }

    @GetMapping(value = "/items/new")
    public String createForm() {
        return "items/createItemForm";
    }

    @PostMapping(value = "/items/new")
    public String create(Book item) {

        itemService.saveItem(item);
        return "redirect:/items/new";
    }


    @GetMapping(value = "/items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {

        Book item = itemService.findOne(itemId);
        model.addAttribute("item", item);

        return "items/updateItemForm";
    }

    @PostMapping(value = "/items/{itemId}/edit")
    public String updateItem(@ModelAttribute("item") Book tmp) {
        itemService.saveItem(tmp);

        return "redirect:/items";
    }


}
