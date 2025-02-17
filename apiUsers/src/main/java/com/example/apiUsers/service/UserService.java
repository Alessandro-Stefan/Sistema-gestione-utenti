//dove vengono implementate tutte le operazioni e le regole dell'applicazione
package com.example.apiUsers.service;

import jakarta.persistence.Entity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.apiUsers.model.User;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.apiUsers.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository UserRepo;

    //metodo che ritorna tutti gli utenti
    public List<User> getAllUsers(){
        return UserRepo.findAll();
    }

    //metodo che prende un User tramite un id
    public User getUserById(Integer id){
        //Optional è una classe che ritorna un risultato in caso
        // trovi qualcosa altrimenti ritornerà null

        //Passo dentro questo oggetto la classe User per poi cercare
        // un user specifico tramite il metodo findById,
        // in cui ovviamente passiamo l'attributo id
        Optional<User> optionalUser = UserRepo.findById(id);
        //Se quel user viene trovato verr'a ritornato tramite
        // il metodo get() della classe Optional
        if (optionalUser.isPresent()){
            return optionalUser.get();

        }
        //Altrimenti restituirà una risposta con il null
        log.info("User con id: {} non esiste", id);
        return null;
    }

    //metodo che crea uno user
    public User saveUser (User user) {

        Optional<User> existedUser = UserRepo.findByDynamicParams(user.getUsername(), user.getEmail());

        if (existedUser.isPresent()){
            log.info("Credenziali utente già utilizzate");
            return null;
        }

        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        //chiamo la Repository tramite l'injection per usare il suo metodo di
        //salvataggio per lo user
        User savedUser = UserRepo.save(user);

        //risposta
        log.info("User con id {}: salvato con successo!", user.getId());
        return savedUser;
    }

    //metodo per modificare lo user
    public User updateUser(User user) {
        //chiamo l Optional per verificare che ci sia un utente con quell'id
        Optional<User> existUser = UserRepo.findById(user.getId());

        //controllo se lo user esiste
        if (existUser.isPresent()){
            user.setCreatedAt(existUser.get().getCreatedAt());
            user.setUpdatedAt(LocalDateTime.now());

            User updatedUser = UserRepo.save(user);

            log.info("user con id {}: modificato con successo!", user.getId());
            return  updatedUser;
        }
        //nel caso non ci sia ritorno una risposta con un null
        log.info("User con id {}: non trovato", user.getId());
        return null;
    }

    //metodo che elimina un utente specifio
    public void deleteUser(Integer id) {
        UserRepo.deleteById(id);
    }
}
