package com.jpaproject.controller;

import com.jpaproject.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.awt.*;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @ResponseBody
    @GetMapping(value = "/order",produces = MediaType.TEXT_HTML_VALUE)
    public String orderPage(){
        return """
                <a href="/order/now?order_id=2&user_id=2">Order now</a>
                """;
    }

    @ResponseBody
    @GetMapping(value = "/order/now")
    public String orderNow(
            @RequestParam("order_id") Long orderId,
            @RequestParam("user_id") Long userId
    ){
        try{
            orderService.confirmOrder(orderId,userId);
            return "order confirm successfully";
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
