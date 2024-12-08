package bth1.example.bth2.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import bth1.example.bth2.filter.JwtFilter;
import bth1.example.bth2.service.userService;

@Configuration
public class FilterConfig {

	@Bean
    public FilterRegistrationBean<JwtFilter> jwtFilter(userService userService) {
        FilterRegistrationBean<JwtFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JwtFilter(userService));
        registrationBean.addUrlPatterns("/auth/*");  // Áp dụng cho các endpoint có token
        registrationBean.setOrder(1);  // Đảm bảo filter này chạy trước các filter khác
        return registrationBean;
    }
}
