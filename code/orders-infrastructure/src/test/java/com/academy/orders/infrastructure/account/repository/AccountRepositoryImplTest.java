package com.academy.orders.infrastructure.account.repository;

import com.academy.orders.infrastructure.account.AccountMapper;
import com.academy.orders.infrastructure.account.entity.AccountEntity;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.academy.orders.infrastructure.ModelUtils.getAccount;
import static com.academy.orders.infrastructure.ModelUtils.getAccountEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountRepositoryImplTest {
    @InjectMocks
    private AccountRepositoryImpl repository;
    @Mock
    private AccountJpaAdapter accountJpaAdapter;
    @Mock
    private AccountMapper accountMapper;


    @Test
    void testGetAccountByEmail(){
        var accountEntity = getAccountEntity();
        var accountDomain = getAccount();

        when(accountJpaAdapter.findByEmail(anyString())).thenReturn(Optional.of(accountEntity));
        when(accountMapper.fromEntity(accountEntity)).thenReturn(accountDomain);

        var actualAccount = repository.getAccountByEmail(accountEntity.getEmail());

        assertEquals(accountDomain, actualAccount.get());

        verify(accountJpaAdapter).findByEmail(anyString());
        verify(accountMapper).fromEntity(any(AccountEntity.class));
    }

    @Test
    void testGetAccountByEmailIfAccountAbsent(){
        var mail = "test@mail.com";

        when(accountJpaAdapter.findByEmail(anyString())).thenReturn(Optional.empty());

        var actualAccount = repository.getAccountByEmail(mail);

        assertTrue(actualAccount.isEmpty());
        verify(accountJpaAdapter).findByEmail(anyString());
        verify(accountMapper, never()).fromEntity(any(AccountEntity.class));
    }

}
