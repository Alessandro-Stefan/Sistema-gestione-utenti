//Sono delle interfacce che ci permettono di interagire con il database
package com.example.apiUsers.repository;

import com.example.apiUsers.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
//L'estensione a JpaRepositry ci permette di usare i suoi metodi
//per le classiche CRUD operations e per le query

//"User" rappresenta l'entità mentre Integer il tipo dato
//dell'identificatore di questa entità
public interface UserRepository extends JpaRepository<User, Integer> {
    //grazie alla JpaRepository qua dentro vengono gestite automaticamente, tramite dei metodi, query e operazioni con  il database

    Optional<User> findByUsername(String username);

    // Find by email
    Optional<User> findByEmail(String email);

    // Complex dynamic search
    @Query("SELECT u FROM User u WHERE " +
            "(:username IS NULL OR u.username = :username) AND " +
            "(:email IS NULL OR u.email = :email)")
    Optional<User> findByDynamicParams(
            @Param("username") String username,
            @Param("email") String email
    );
}
