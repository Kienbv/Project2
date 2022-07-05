package com.shopme.admin.user;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateNewUserWithOneRole() {
        Role roleAdmin = entityManager.find(Role.class, 1);
        User userKienbv = new User("kienbv@gmail.com", "kienbv2022", "kien", "bv");
//        User userKienbv = new User("cuongnh@gmail.com", "cuongnh2022", "cuong", "nguyen huu");
//        User userKienbv = new User("kientt@gmail.com", "kientt2022", "kien", "ta trung");
        userKienbv.addRole(roleAdmin);

        User savedUser = repo.save(userKienbv);

        assertThat(savedUser.getId()).isGreaterThan(0);
    }


    @Test
	public void testCreateNewUserWithTwoRoles() {
        User userLamhs = new User("lamhs@gmail.com", "lamhs2022", "lam", "hoang son");
        Role roleEditor = new Role(3);
        Role roleAssistant = new Role(5);

        userLamhs.addRole(roleEditor);
        userLamhs.addRole(roleAssistant);

        User savedUser = repo.save(userLamhs);

        assertThat(savedUser.getId()).isGreaterThan(0);
    }
	
	@Test
	public void testListAllUsers() {
		Iterable<User> listUsers = repo.findAll();
		listUsers.forEach(user -> System.out.println(user));
	}
	
	@Test
	public void testGetUserById() {
        User userKienbv = repo.findById(1).get();
        System.out.println(userKienbv);
        assertThat(userKienbv).isNotNull();
    }
	
	@Test
	public void testUpdateUserDetails() {
        User userKienbv = repo.findById(1).get();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String rawPassword = "kienbv2022";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        userKienbv.setEnabled(true);
        userKienbv.setLastName("bui van");
        userKienbv.setPassword(encodedPassword);

        repo.save(userKienbv);
    }
	
	@Test
	public void testUpdateUserRoles() {
        User userId6 = repo.findById(6).get();
        Role roleEditor = new Role(1);
        Role roleSalesperson = new Role(2);

        userId6.getRoles().remove(roleEditor);
        userId6.addRole(roleSalesperson);

        repo.save(userId6);
    }
	
	@Test
	public void testDeleteUser() {
        Integer userId = 10;
        repo.deleteById(userId);

    }
	
	@Test
	public void testGetUserByEmail() {
        String email = "lamhs@gmail.com";
        User user = repo.getUserByEmail(email);

        assertThat(user).isNotNull();
    }
	
	@Test
	public void testCountById() {
        Integer id = 8;
        Long countById = repo.countById(id);

        assertThat(countById).isNotNull().isGreaterThan(0);
    }
	
	@Test
	public void testDisableUser() {
        Integer id = 8;
        repo.updateEnabledStatus(id, false);

    }
	
	@Test
	public void testEnableUser() {
        Integer id = 8;
        repo.updateEnabledStatus(id, true);

    }
	
	@Test
	public void testListFirstPage() {
		int pageNumber = 0;
		int pageSize = 4;
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<User> page = repo.findAll(pageable);
		
		List<User> listUsers = page.getContent();
		
		listUsers.forEach(user -> System.out.println(user));
		
		assertThat(listUsers.size()).isEqualTo(pageSize);
	}
	
	@Test
	public void testSearchUsers() {
        String keyword = "bv";

        int pageNumber = 0;
        int pageSize = 4;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> page = repo.findAll(keyword, pageable);

        List<User> listUsers = page.getContent();

        listUsers.forEach(user -> System.out.println(user));
		
		assertThat(listUsers.size()).isGreaterThan(0);
	}
}
