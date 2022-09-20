package co.zw.santech.onlinepayments.repositories;

import co.zw.santech.onlinepayments.models.Role;
import co.zw.santech.onlinepayments.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByUsername(String username);

    Page<User> findAllByRolesEquals(Role role, Pageable pageable);

    Long countUsersByRolesEquals(Role role);

//    @Modifying
//    @Transactional
//    @Query(value = "UPDATE users SET shop_id =:shopId, description =:shopName WHERE username =:un", nativeQuery = true)
//    void setUserShopOther(String un, Integer shopId, String shopName);
//
//    @Modifying
//    @Transactional
//    @Query(value = "UPDATE users SET shop_id =:shopId, description='' WHERE username =:un", nativeQuery = true)
//    void setUserShop(String un, Integer shopId);


}