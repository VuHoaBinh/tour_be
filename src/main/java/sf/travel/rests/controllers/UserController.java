package sf.travel.rests.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import sf.travel.entities.User;
import sf.travel.rests.types.*;
import sf.travel.services.UserService;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    @Autowired
    private final UserService userService;

    @PostMapping("/")
    public User create(@RequestBody CreateUserReq req) {
        return userService.create(req);
    }

    @PostMapping("/login")
    public Optional<User> login(@RequestBody LoginReq req) {
        return userService.login(req);
    }

    @GetMapping("")
    public ApiResponse<User> findAll(@ModelAttribute UserFilter filter){
        Page<User> pageResult = userService.findAll(filter);
        ApiResponse<User> response = new ApiResponse<>();
        response.setItems(pageResult.getContent());
        response.setTotal(pageResult.getTotalElements());
        response.setSize(pageResult.getSize());
        response.setPage(pageResult.getNumber());

        return response;
    }

    @GetMapping("/{id}")
    public Optional<User> findById(@PathVariable Long id){
        return userService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        userService.delete(id);
    }

    @PutMapping("/{id}")
    public User partialUpdate(@PathVariable Long id, @RequestBody UpdateUserReq req){
        return userService.partialUpdate(id, req);
    }
}
