package iseng.cafe.pos.services.jdbc.impl;

import iseng.cafe.pos.entities.Snack;
import iseng.cafe.pos.repositories.jdbc.SnackRepository;
import iseng.cafe.pos.services.jdbc.SnackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SnackServiceImpl implements SnackService {
    @Autowired
    private SnackRepository snackRepository;

    @Override
    public Snack save(Snack entity) {
        return snackRepository.save(entity);
    }

    @Override
    public Boolean remove(Integer id) {
        return snackRepository.remove(id);
    }

    @Override
    public Snack findById(Integer id) {
        return snackRepository.findById(id);
    }

    @Override
    public List<Snack> findAll() {
        return snackRepository.findAll();
    }
}
