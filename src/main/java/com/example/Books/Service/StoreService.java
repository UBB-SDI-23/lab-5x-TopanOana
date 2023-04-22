package com.example.Books.Service;

import com.example.Books.Exception.StoreNotFoundException;
import com.example.Books.Exception.StoreValidationException;
import com.example.Books.Model.Employee;
import com.example.Books.Model.Stock;
import com.example.Books.Model.Store;
import com.example.Books.Repository.StoreRepository;
import com.example.Books.Validation.ValidatorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StoreService {
    @Autowired
    private StoreRepository repository;

    public StoreService(StoreRepository repository){
        this.repository=repository;
    }


    public Page<Store> getAllStores(int page, int size){
        /*
        returns all stores in the repo
         */
        PageRequest pageRequest = PageRequest.of(page, size);
        return repository.findAll(pageRequest);
    }

    public List<Employee> getStoreEmployeesByID(Long id){
        /*
        gets a specific book from the repo with an id
         */

        return repository.findById(id).get().getEmployees().stream().collect(Collectors.toList());
    }

    public Store getStoreByID(Long id){
        return repository.findById(id).orElseThrow(() -> new StoreNotFoundException(id));
    }

    public Store addStoreToRepository(Store newStore) throws StoreValidationException {
        /*
        adds a store to the repository
         */
        ValidatorStore validatorStore = new ValidatorStore();
        validatorStore.validate(newStore);
        return repository.save(newStore);
    }

    public Store updateStoreInRepository(Long id, Store updatedStore){
        ValidatorStore validatorStore = new ValidatorStore();
        validatorStore.validate(updatedStore);
        return repository.findById(id).map(store->{
            store.setStoreName(updatedStore.getStoreName());
            store.setAddress(updatedStore.getAddress());
            store.setContactNumber(updatedStore.getContactNumber());
            store.setOpeningHour(updatedStore.getOpeningHour());
            store.setClosingHour(updatedStore.getClosingHour());
            return repository.save(store);
        }).orElseGet(() ->{
            updatedStore.setId(id);
            return repository.save(updatedStore);
        });
    }

    public void deleteStoreByID(Long id){
        /*
        deletes a store in the repository by id
         */
        repository.deleteById(id);
    }


    public Store addEmployeeToStore(Long id, Employee employee){
        return this.repository.findById(id).get().addEmployeeToStore(employee);
    }

    public Stock addStock(Long storeID, Stock stock){
        stock.setStore(getStoreByID(storeID));
        return repository.findById(storeID).get().addStockToStore(stock);
    }

    public List<Employee> addEmployeesToStore(Long storeID, List<Employee> employees){
        for (Employee e:employees){
            addEmployeeToStore(storeID, e);
        }
        return employees;
    }

    public Page<Store> getStoresWithNameLike(String input, int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size);
        return this.repository.findStoresByStoreNameContainsIgnoreCase(input, pageRequest);
    }

    public Page<Store> getStoresSorted(int page, int size, String column, String order){
        PageRequest pageRequest = PageRequest.of(page, size);
        switch(column){
            case "storeName"->{
                if (order.equals("asc"))
                    return repository.findByOrderByStoreNameAsc(pageRequest);
                else
                    return repository.findByOrderByStoreNameDesc(pageRequest);
            }
            case "address"->{
                if (order.equals("asc"))
                    return repository.findByOrderByAddressAsc(pageRequest);
                else
                    return repository.findByOrderByAddressDesc(pageRequest);
            }
            default -> {return null;}
        }
    }


}
