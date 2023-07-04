package vn.edu.iuh;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import vn.edu.iuh.entity.Role;
import vn.edu.iuh.service.RoleService;

@SpringBootApplication
@EnableJpaAuditing
public class SpringBoot2xApiJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBoot2xApiJwtApplication.class, args);
    }

//    @Bean
    CommandLineRunner run(RoleService roleService) {
        return args -> {
            roleService.save(new Role("User Read", "ROLE_USER_READ", null, null));
            roleService.save(new Role("Manager", "ROLE_MANAGER", null, null));
            roleService.save(new Role("Admin", "ROLE_ADMIN", null, null));
            roleService.save(new Role("Super Admin", "ROLE_SUPER_ADMIN", null, null));
        };
    }
}
