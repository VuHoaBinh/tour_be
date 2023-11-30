package sf.travel.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import sf.travel.entities.User;
import sf.travel.errors.ConflictError;
import sf.travel.errors.ErrorCode;
import sf.travel.helper.specifications.UserSpec;
import sf.travel.repositories.UserRepository;
import sf.travel.rests.types.CreateUserReq;
import sf.travel.rests.types.LoginReq;
import sf.travel.rests.types.UserFilter;
import sf.travel.rests.types.UpdateUserReq;


import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    @Autowired private final UserRepository userRepo;
    @Autowired private final UserSpec userSpec;

    public User create(CreateUserReq input) {
        User user = new User();
        user.setName(input.getName());
        user.setEmail(input.getEmail());
        user.setPassword(input.getPassword());
        user.setRole(input.getRole());
        return userRepo.save(user);
    }

    public Optional<User> login(LoginReq input) {
        Optional<User> user = userRepo.findByEmail(input.getEmail());
        if (user.isPresent()) {
            User newUser = user.get();
            if (newUser.getPassword().equals(input.getPassword())) {
                return user;
            }
        }
        throw new ConflictError(ErrorCode.USER_NOT_FOUND);
    }

    public Page<User> findAll(UserFilter filter){
        Specification<User> spec = null;
        if (filter.getName() != null) {
            spec = userSpec.createSpecification("name", filter.getName());
        }
        if (filter.getEmail() != null) {
            spec = userSpec.createSpecification("email", filter.getEmail());
        }

        if (filter.getSearchText() != null) {
            spec = userSpec.createLikeSpecification("email", filter.getSearchText())
                    .or(userSpec.createLikeSpecification("name", filter.getSearchText()));
        }

        System.out.println("Conditions: " + spec);

        Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize(), Sort.by("id").descending());

        Page<User> res = userRepo.findAll(spec, pageable);
        return res;
    }

    public Optional<User> findById(Long id){
        return userRepo.findById(id);
    }

    public User partialUpdate(Long id, UpdateUserReq input){
        Optional<User> customer = userRepo.findById(id);
        if (customer.isPresent()){
            User newCustomer = customer.get();
            if (input.getRole() != null){
                newCustomer.setRole(input.getRole());
            }

            return userRepo.save(newCustomer);
        } else {
            throw new ConflictError(ErrorCode.USER_NOT_FOUND);
        }
    }


    public void delete(Long id){
        userRepo.deleteById(id);
    }
}
