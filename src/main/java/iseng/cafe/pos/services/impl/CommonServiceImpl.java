package iseng.cafe.pos.services.impl;

import iseng.cafe.pos.services.CommonService;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public class CommonServiceImpl<T, ID> implements CommonService<T, ID> {

    protected final JpaRepository<T, ID> repository;

    public CommonServiceImpl(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }

    @Override
    public T save(T item) {
        return repository.save(item);
    }

    @Override
    public T removeById(ID id) {
        T item = findById(id);
        if(item != null){
            repository.deleteById(id);
            return item;
        } else {
            return null;
        }
    }

    @Override
    public T findById(ID id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<T> findAll(T search, int page, int size, Sort.Direction direction){
        Sort sort = Sort.Direction.DESC.equals(direction) ?
                Sort.by(direction, "id") : Sort.by("id");

        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        return repository.findAll(Example.of(search, matcher), PageRequest.of(page, size, sort));
    }
}
