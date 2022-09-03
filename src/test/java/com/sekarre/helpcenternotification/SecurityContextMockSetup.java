package com.sekarre.helpcenternotification;

import com.sekarre.helpcenternotification.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static com.sekarre.helpcenternotification.factories.UserMockFactory.getCurrentUserMock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SecurityContextMockSetup {

    public void setUpSecurityContext() {
        User user = getCurrentUserMock();
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(user);
    }

    public void setUpSecurityContext(User user) {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(user);
    }
}