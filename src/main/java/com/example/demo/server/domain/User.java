package com.example.demo.server.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author : songtc
 * @since : 2023/12/09 15:05
 */
@Setter
@Getter
public class User {

    private Long id;
    private String username;
    private String password;
    private List<Coupons> couponsList;

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Coupons {
        private Long id;
        private String name;
        /**
         * 优惠卷到期时间
         */
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime lostDateTime;
    }
}
