package com.company;

import java.util.ArrayList;
import java.util.List;

public class Banco {
    private Account contas[];

    // O parametro é o numero de contas a criar
    public Banco (int N) {
        contas = new Account[N] ;
        for (int i=0; i<N; i++) { contas[i] = new Account(i); }
        this.contas = contas;
    }

    public Account[] getAccounts() {
        return this.contas;
    }

    private Account get(int id) throws InvalidAccount {
        if (id<0 || id>=contas.length ) throw new InvalidAccount();
        return contas[id];
    }

    interface Bank {
        void deposit(int id, int val) throws InvalidAccount;
        void withdraw(int id, int val) throws InvalidAccount, Account.NotEnoughFunds;
        int totalBalance(int accounts[]) throws InvalidAccount;
    }

    public void deposit(int id, int val) throws InvalidAccount {
        Account c = get(id);
        synchronized (this) { c.deposit(val); }
    }

    public void withdraw(int id, int val) throws Account.NotEnoughFunds, InvalidAccount {
        Account c = get(id);
        synchronized (this) { c.withdraw(val); }
    }

    public synchronized int totalBalance () throws InvalidAccount {
        int total = 0;
        for (int id=0; id<contas.length; id++) {
            Account c = get(id);
            synchronized (this) { total += c.balance(); }
        }
        return total;
    }



    public static class InvalidAccount extends Exception {}
}
