package co.zw.santech.onlinepayments.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetail extends User implements UserDetails {
    private static final long serialVersionUID = 1L;

    public CustomUserDetail(User user) {
        super(user);
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authourityList = new ArrayList();
        super.getRoles().forEach(role -> authourityList.add(new SimpleGrantedAuthority(role.getName())));
        return authourityList;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
