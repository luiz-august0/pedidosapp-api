package com.pedidosapp.api.config.multitenancy;

import com.auth0.jwt.JWT;
import com.pedidosapp.api.service.exceptions.ApplicationGenericsException;
import com.pedidosapp.api.service.exceptions.enums.EnumGenericsException;
import com.pedidosapp.api.utils.Utils;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

import static com.pedidosapp.api.constants.Headers.TENANT_HEADER;
import static com.pedidosapp.api.constants.Headers.TOKEN_HEADER;

@Component
public class TenantInterceptor implements WebRequestInterceptor {
    @Override
    public void preHandle(WebRequest request) {
        var sessionHeader = request.getHeader(TOKEN_HEADER);

        if (Utils.isEmpty(sessionHeader)) {
            TenantContext.setCurrentTenant(request.getHeader(TENANT_HEADER));
        } else {
            String token = sessionHeader.replace("Bearer ", "");
            String schema = String.valueOf(JWT.decode(token).getAudience()).replaceAll("]", "").replaceAll("\\[", "");

            if (Utils.isNotEmpty(schema)) {
                TenantContext.setCurrentTenant(schema);
            } else {
                throw new ApplicationGenericsException(EnumGenericsException.TOKEN_WITHOUT_SCHEMA);
            }
        }
    }

    @Override
    public void postHandle(WebRequest webRequest, ModelMap modelMap) {
        TenantContext.clear();
    }

    @Override
    public void afterCompletion(WebRequest webRequest, Exception e) {

    }
}