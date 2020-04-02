package com.sbs.sbsgroup7.security;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    protected Log logger= LogFactory.getLog(this.getClass());

    private RedirectStrategy redirectStrategy=new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication)
            throws IOException {

        handle(httpServletRequest, httpServletResponse, authentication);
        clearAuthenticationAttributes(httpServletRequest);
    }

    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException{

        String targetUrl = determineTargetUrl(authentication);

        if(response.isCommitted()){
            logger.debug("Response is already committed. Unable to redirect to" + targetUrl);
            return;
        }
        redirectStrategy.sendRedirect(request,response,targetUrl);
    }

    protected String determineTargetUrl(final Authentication authentication){

        Map<String, String> roleTargetUrlMap = new HashMap<>();
        roleTargetUrlMap.put("USER","/user/home");
        roleTargetUrlMap.put("MERCHANT", "/merchant/home");
        roleTargetUrlMap.put("TIER1","/tier1/home");
        roleTargetUrlMap.put("TIER2","/tier2/home");
        roleTargetUrlMap.put("ADMIN", "/admin/home");

        final Collection<? extends GrantedAuthority> authorities=authentication.getAuthorities();

        for(final GrantedAuthority grantedAuthority : authorities){
            String authorityName=grantedAuthority.getAuthority();
            if(roleTargetUrlMap.containsKey(authorityName)){
                return roleTargetUrlMap.get(authorityName);
            }
        }
        throw new IllegalStateException();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request){
        HttpSession httpSession=request.getSession(false);
        if(httpSession==null){
            return;
        }
        httpSession.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}