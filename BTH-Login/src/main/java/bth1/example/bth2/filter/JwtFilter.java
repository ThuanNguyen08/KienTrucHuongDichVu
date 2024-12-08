package bth1.example.bth2.filter;

import java.io.IOException;

import bth1.example.bth2.service.userService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter(urlPatterns = "/auth/*")
public class JwtFilter implements Filter {

	private final userService service;

	public JwtFilter(userService service) {
		this.service = service;
	}

	@Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Lấy token từ header
        String token = httpRequest.getHeader("Authorization");

        if (token != null && !token.isEmpty()) {
            try {
                // Giải mã token và lấy thông tin claims
                Claims claims = service.validateToken(token);

                // Lấy vai trò từ claims
                String role = claims.get("role", String.class);

                // Kiểm tra quyền truy cập (có thể thay đổi logic phân quyền tại đây)
                if ("user".equals(role)) {
                    // Nếu người dùng có vai trò user, không cho phép truy cập vào các endpoint admin
                    if (httpRequest.getRequestURI().contains("/auth")) {
                        httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);  // Trả về lỗi 403
                        httpResponse.getWriter().write("Forbidden: User role cannot access this resource");
                        return;
                    }
                }

                // Nếu không có lỗi, tiếp tục xử lý request
                httpRequest.setAttribute("claims", claims);
                chain.doFilter(request, response);
            } catch (Exception e) {
                // Nếu token không hợp lệ hoặc hết hạn
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  // Trả về lỗi 401
                httpResponse.getWriter().write("Invalid or expired token");
            }
        } else {
            // Nếu không có token
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  // Trả về lỗi 401
            httpResponse.getWriter().write("Missing token");
        }
    }
}