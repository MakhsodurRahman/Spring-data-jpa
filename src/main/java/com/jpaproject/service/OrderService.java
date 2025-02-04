package com.jpaproject.service;

import com.jpaproject.dto.InvoiceDescItem;
import com.jpaproject.dto.InvoiceInfo;
import com.jpaproject.entity.AppUser;
import com.jpaproject.entity.Orders;
import com.jpaproject.repository.AppUserRepository;
import com.jpaproject.repository.OrderRepository;
import com.jpaproject.repository.impl.ProductRepo;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class OrderService {


    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private AppUserRepository appUserRepository;

    private OrderRepository orderRepository;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private JavaMailSender mailSender;
    private TransactionTemplate transactionTemplate;





    public void confirmOrder(Long orderId,Long userId){


        CompletableFuture.supplyAsync(new Supplier<Orders>() {
            @Override
            public Orders get() {
                return transactionTemplate.execute(new TransactionCallback<Orders>() {
                    @Override
                    public Orders doInTransaction(TransactionStatus status) {
                        return orderRepository.findByIdAndUser(orderId,new AppUser(userId));
                    }
                });
            }
        }).thenApply(orders -> buildInvoice(orders))
                .thenAccept(invoiceInfo -> sendInvoiceMailTo(invoiceInfo))
                .exceptionally(ex->{
                    ex.printStackTrace();
                    return null;
                });

        /*
        var order = orderRepository.findByIdAndUser(orderId,new AppUser(userId))
                        .thenApply(orders -> buildInvoice(orders))
                                .thenAccept(invoiceInfo -> sendInvoiceMailTo(invoiceInfo))
                                        .exceptionally(ex->{
                                            ex.printStackTrace();
                                            return null;
                                        });

         */


        System.out.println("request thread name"+Thread.currentThread().getName());
    }



    private void sendInvoiceMailTo(InvoiceInfo invoiceInfo) {
        try{
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setFrom("makhsodurrahmanhridoy@gmail.com");
            helper.setTo("makhsodur.rahman@naztech.us.com");
            helper.setSubject("test mail");
            helper.setText(invoiceInfo.content(),true);
            mailSender.send(mimeMessage);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public InvoiceInfo buildInvoice(Orders order){
        System.out.println("buildInvoice by :   "+Thread.currentThread().getName());
        sleep(3000L);
        var user = order.getUser();
        var invoiceItem = order.getOrderItems().stream()
                .map(i -> new InvoiceDescItem(i.getProduct().getName(),i.getQuantity(),i.getProduct().getPrice(),i.getProduct().getPrice() * i.getQuantity())).toList();

        var total = invoiceItem.stream()
                .map(InvoiceDescItem::amount).reduce(0L,Long::sum);

        Context context = new Context();
        context.setVariable("order_id",order.getId());
        context.setVariable("user_name",user.getName());
        context.setVariable("user_email",user.getEmail());
        context.setVariable("user_address",user.getAddress());
        context.setVariable("items",invoiceItem);
        context.setVariable("total_amount",total);

        String content  = templateEngine.process("invoice", context);

        return  new InvoiceInfo(content,user.getEmail());
    }

    public static void sleep(Long ms)  {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
