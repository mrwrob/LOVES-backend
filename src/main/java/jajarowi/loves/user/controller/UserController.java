package jajarowi.loves.user.controller;

import jajarowi.loves.user.dto.UpdatePasswordRequest;
import jajarowi.loves.user.dto.UpdateUsernameRequest;
import jajarowi.loves.user.entity.User;
import jajarowi.loves.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("{login}/username")
    public ResponseEntity<Void> updateUsername(@RequestBody UpdateUsernameRequest request, @PathVariable String login) {
        Optional<User> user = userService.find(login);
        if (user.isPresent()) {
            if(userService.find(request.getUsername()).isEmpty()) {
                userService.updateUsername(user.get(), request.getUsername());
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else return ResponseEntity.notFound().build();
    }

    @PutMapping("{login}/password")
    public ResponseEntity<Void> updatePassword(@RequestBody UpdatePasswordRequest request, @PathVariable String login) {
        Optional<User> user = userService.find(login);
        if (user.isPresent()) {
            if(userService.updatePassword(user.get(), request.getOldPassword(), request.getNewPassword()) != null) {
                return ResponseEntity.ok().build();
            }
            else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } else return ResponseEntity.notFound().build();

    }
}
