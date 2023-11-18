package accounts;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cglib.core.Local;

import accounts.dtos.AccountDto;
import accounts.model.Account;
import accounts.services.AccountsService;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AccountsServiceTests {

    @InjectMocks
    private AccountsService accountsService;

    @Test
    void convertToEntity_shouldReturnAccount() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // Given
        LocalDate today = LocalDate.now();
        AccountDto accountDto = new AccountDto(today, 100.0, "123456");

        // When
        Method convertToEntityMethod = AccountsService.class.getDeclaredMethod("convertToEntity", AccountDto.class);
        convertToEntityMethod.setAccessible(true);  // Make the private method accessible
        Account result = (Account) convertToEntityMethod.invoke(accountsService, accountDto);

        // Then
        assertEquals(today, result.getRegistrationDate());
        assertEquals(100.0, result.getBalance());
        assertEquals("123456", result.getMercadoPagoId());
    }
}
