//Il controller gestisce tutte le richieste HTTP e le manda al service adatto
package com.example.apiUsers.controller;

import lombok.RequiredArgsConstructor;
import com.example.apiUsers.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.example.apiUsers.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user/v1")
@RequiredArgsConstructor
@Validated
@Slf4j
public class UserController {

    private final UserService userService;

    //percorso di richiesta GET per accedere a tutti gli utenti
    @GetMapping("/all")
    //ResponseEntity è una classe di spring boot che rappresenta la risposta HTTP(Status Code, Headers e Body)
    public ResponseEntity<List<User>> getAllUsers() {
        //creazione di un ResponseEntity con lo status settato ad OK e con un boy specificato
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    //percorso di richiesta GET per trovare un user specifico
    //all'interno verrà inserito un id specifico
    @GetMapping("/{id}")
    //L'annotazione PathVariable serve per chiamare l'id che è stato passato tramite la richiesta
    public ResponseEntity<User> getUserById(@PathVariable Integer id){
        //risposta che verrà data, lo user è stato preso tramite il metodo dello UserService
        return ResponseEntity.ok().body(userService.getUserById(id));
    }

    //percorso di richiesta POST per creare un utente
    @PostMapping("/")
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        try {
            // Log di controllo per controllare il contenuto della richiesta
            log.info("Richiesta di creazione dell'utente ricevuta: {}", user);

            // validazione input
            if (user == null) {
                log.error("null user object");
                return ResponseEntity.badRequest().body("dati user invalidi");
            }

            // Validazione input
            if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
                log.error("Username richiesta, contenuto vuoto");
                return ResponseEntity.badRequest().body("Username richiesto");
            }

            User savedUser = userService.saveUser(user);

            if (savedUser == null){
                return ResponseEntity.badRequest().body("credenziali user già utilizzate");
            }

            log.info("User creato con successo: {}", savedUser);
            return ResponseEntity.ok(savedUser + "user salvato con successo");

        } catch (Exception e) {
            // Log che genera l'errore
            log.error("Erroe di creazione user", e);
            //risposta con con errore
            return ResponseEntity.internalServerError()
                    .body("Error processing user: " + e.getMessage());
        }
    }

    //percorso di richiesta PUT per aggiornare un user, il metodo PUT di solito
    //viene usato per creare o modificare una risorsa
    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        return ResponseEntity.ok().body(userService.updateUser(user));
    }

    //percorso di richiesta DELETE per eliminare un dato utente
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Integer id){
        userService.deleteUser(id);
        return ResponseEntity.ok().body("User eliminato con successo");
    }

}
