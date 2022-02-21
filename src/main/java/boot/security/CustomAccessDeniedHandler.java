package boot.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private static final Logger logger = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);

    @Override
    public void handle(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       AccessDeniedException e) throws IOException, ServletException {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            logger.info("Principal: " + auth.getPrincipal());
            logger.info("Authorities: " + auth.getAuthorities());
            logger.info("Credentials: " + auth.getCredentials());
            logger.info("Is the requesting user an authorized: " + auth.isAuthenticated());
            boolean isAdmin = false;
            for (Object role : auth.getAuthorities().toArray()) {
                if ("ROLE_ADMIN".equals(role.toString())) {
                    isAdmin = true;
                    break;
                }
            }
            if (!auth.isAuthenticated() || !isAdmin) {
                logger.info("User '" + auth.getName() + "' attempted to access the protected URL: " +
                        httpServletRequest.getRequestURI());
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/403");
            } else {
                logger.info("There was a problem with the user's access rights. Attempt to redirect the user to the same address");
                httpServletResponse.sendRedirect(httpServletRequest.getRequestURI());
            }
        } else {
            logger.info("Authentication object is null, please check security settings");
        }
    }
}