package com.job.service;

import com.job.model.Transaction;
import com.job.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository repository;

    public List<Transaction> findAll() {
        return repository.findAll();
    }

    public Transaction findById(int id) {
        return repository.findById(id);
    }

    

    
    public void add(Transaction servicePackage) {
       repository.add(servicePackage);
    }

    public void update(Transaction servicePackage) {
        repository.update(servicePackage);
    }

   public boolean deleteByID(int id) {
        return repository.deleteById(id);
    }

    public List<Transaction> search(String keyword) {
        return repository.search(keyword);
    }

    public List<Transaction> getPage(List<Transaction> list, int page, int size) {
        return repository.getPage(list, page, size);
    }

    public int countPages(List<Transaction> list, int size) {
       return repository.countPages(list, size);
    }
}
