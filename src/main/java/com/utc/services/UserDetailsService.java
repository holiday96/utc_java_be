package com.utc.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Project_name : UTC_Java
 *
 * @author : XuShiTa
 * @version : 1.0
 * @since : 27.5.2024
 * Description :
 */
public interface UserDetailsService {
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
