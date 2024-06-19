package com.academy.orders.boot.security;

import com.academy.orders.ModelsUtil;
import com.academy.orders.domain.account.repository.AccountRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceTest {
    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;
    @Mock
    private AccountRepository accountRepository;

    @Test
    void loadUserByUsernameTest(){
        var account = ModelsUtil.getAccount();
        when(accountRepository.getAccountByEmail(Mockito.anyString()))
            .thenReturn(Optional.of(account));

        var result = (SecurityUser) userDetailsService.loadUserByUsername(account.email());

        assertEquals(result.getUsername(), account.email());
        assertEquals(result.getPassword(), account.password());
        assertEquals(result.getId(), account.id());
        assertTrue(result.getAuthorities().contains(new SimpleGrantedAuthority(account.role().name())));

        verify(accountRepository).getAccountByEmail(Mockito.anyString());
    }

    @Test
    void loadUserByUsernameThrowsUsernameNotFoundExceptionTest(){
        when(accountRepository.getAccountByEmail(Mockito.anyString()))
            .thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
            ()-> userDetailsService.loadUserByUsername(anyString()));
        verify(accountRepository).getAccountByEmail(Mockito.anyString());
    }
}