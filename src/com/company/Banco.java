package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Banco {
    HashMap<Integer,Account> accounts = new HashMap<>();
    Lock l = new ReentrantLock();
    int last_id = 0;

    int createAccount(int balance) {
        //construtor e metodo devem ser chamados sem locks
        //adquiridos para não ocupar o lock desnecessariamente
        Account c = new Account();
        c.deposit(balance);

        l.lock();
        try {
            last_id += 1;
            accounts.put(last_id, c);
            //garantimos com o try finally que o return executa depois do unlock
            return last_id;
        } finally {
            l.unlock();
        }

    }

/*
    // O parametro é o numero de contas a criar
    public Banco (int N) {
        contas = new Account[N] ;
        for (int i=0; i<N; i++) { contas[i] = new Account(i); }
        this.contas = contas;
    }
*/

    public HashMap<Integer, Account> getAccounts() {
        return this.accounts;
    }


    interface Bank {
        void deposit(int id, int val) throws InvalidAccount;
        void withdraw(int id, int val) throws InvalidAccount, Account.NotEnoughFunds;
        int totalBalance(int accounts[]) throws InvalidAccount;
        void transfer(int from, int to, int amount) throws InvalidAccount, Account.NotEnoughFunds;
    }
/*
    public void deposit(int id, int val) throws InvalidAccount {
        Account c = get(id);
        synchronized (this) { c.deposit(val); }
    }

    public void withdraw(int id, int val) throws Account.NotEnoughFunds, InvalidAccount {
        Account c = get(id);
        synchronized (this) { c.withdraw(val); }
    }
*/
    public void deposit(int id, int val) throws InvalidAccount {
        Account c;
        //lock do banco
        l.lock();
        try {
            c = this.accounts.get(id);
            if (c == null) throw new InvalidAccount();
            // return ...
        } finally {
            l.unlock();
        }
        /*lock da conta
        c.l.lock();
        try {} finally {
            c.l.unlock();
        }
        */
        c.deposit(val);
    }

    public void withdraw(int id, int val) throws Account.NotEnoughFunds, InvalidAccount {
        Account c;
        l.lock();
        try {
            c = this.accounts.get(id);
            if (c == null) throw new InvalidAccount();
        } finally {
            l.unlock();
        }
        c.withdraw(val);
    }

    public synchronized int totalBalance () throws InvalidAccount {
        int total = 0;
        for (int id=0; id<this.last_id; id++) {
            Account c = this.accounts.get(id);
            synchronized (this) { total += c.balance(); }
        }
        return total;
    }

    public void transfer (int from, int to, int amount) throws InvalidAccount, Account.NotEnoughFunds {
        Account source = this.accounts.get(from);
        Account dest = this.accounts.get(to);
        Account c1,c2;
        if (from<to) {c1=source;c2=dest;}
        else {c1=dest;c2=source;}
        //two phase locking
        synchronized (c1) {
            synchronized (c2) {
                source.withdraw(amount);
                dest.deposit(amount);
            }
        }
    }


    public static class InvalidAccount extends Exception {}
}
