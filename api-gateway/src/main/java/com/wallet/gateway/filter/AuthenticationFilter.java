package com.wallet.gateway.filter;

import com.wallet.gateway.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator validator;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                // header contains token or not
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("missing authorization header");
                }

                String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
                try {
                    //REST call to AUTH service - DEPRECATED in favor of local validation for speed
                    //template.getForObject("http://auth-service//validate?token" + authHeader, String.class);
                    jwtUtil.validateToken(authHeader);
                    
                    // Extract userId and add to header
                    Long userId = jwtUtil.getUserId(authHeader);
                    if (userId != null) {
                         exchange.mutate().request(
                                exchange.getRequest().mutate()
                                        .header("loggedInUser", userId.toString())
                                        .build()
                        ).build();
                    }

                } catch (Exception e) {
                    System.out.println("invalid access...!" + e.getMessage());
                    throw new RuntimeException("un authorized access to application");
                }
            }
            return chain.filter(exchange);
        });
    }

    public static class Config {

    }
}
