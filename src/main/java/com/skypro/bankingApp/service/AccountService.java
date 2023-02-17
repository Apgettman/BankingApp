package com.skypro.bankingApp.service;


import com.skypro.bankingApp.exception.AccountNotFoundException;
import com.skypro.bankingApp.exception.InsufficientFundsException;
import com.skypro.bankingApp.exception.InvalidChangeAmountException;
import com.skypro.bankingApp.model.Account;
import com.skypro.bankingApp.model.User;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final UserService userService;

    public AccountService(UserService userService) {
        this.userService = userService;
    }

    public Account changeBalance(
            String username, String accountNumber, Operation operation, double amount) {
        if (amount <= 0) {
            throw new InvalidChangeAmountException();
        }
        User user = userService.getUser(username);
        Account account =
                user.getAccounts().stream()
                        .filter(acc -> acc.getAccountNumber().equals(accountNumber))
                        .findFirst()
                        .orElseThrow(AccountNotFoundException::new);
        if (operation.equals(Operation.DEPOSIT)) {
            return depositOnAccount(account, amount);
        } else {
            return withdrawFromAccount(account, amount);
        }
    }

    private Account withdrawFromAccount(Account account, double amount) {
        if (account.getBalance() < amount) {
            throw new InsufficientFundsException();
        }
        account.setBalance(account.getBalance() - amount);
        return account;
    }

    private Account depositOnAccount(Account account, double amount) {
        account.setBalance(account.getBalance() + amount);
        return account;
    }
}
