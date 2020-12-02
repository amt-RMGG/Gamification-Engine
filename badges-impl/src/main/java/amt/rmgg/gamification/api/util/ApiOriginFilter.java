package amt.rmgg.gamification.api.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Enumeration;
import javax.net.ssl.KeyManager;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ApiOriginFilter implements javax.servlet.Filter {


    @Autowired
    private ApiKeyManager apiKeyManager;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpServletResponse res = (HttpServletResponse) response;
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestTarget = httpRequest.getServletPath();
        String method = httpRequest.getMethod();

        if(requestTarget.equals("/badges/") || requestTarget.equals("/application/") || requestTarget.equals("/events/")){
            // Seul cas ou on a pas besoin de la clé API est lorsqu'on ajoute une nouvelle application
            if (requestTarget.equals("/applications/") && method.equals("POST")) {
                System.out.println("No need to check API KEY");
            } else {
                String apiKey = httpRequest.getHeader("x-api-key");
                if (apiKey == "" || apiKey == null || !apiKeyManager.isKeyValid(apiKey)) {
                    res.sendError(403, "Key is not valid");
                    return;
                }
            }
        }

        res.addHeader("Access-Control-Allow-Origin", "*");
        res.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        res.addHeader("Access-Control-Allow-Headers", "Content-Type");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
}
