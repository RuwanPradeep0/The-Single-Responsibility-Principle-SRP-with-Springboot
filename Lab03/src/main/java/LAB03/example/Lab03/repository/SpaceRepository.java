package LAB03.example.Lab03.repository;


import LAB03.example.Lab03.entity.Space;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpaceRepository extends JpaRepository<Space, Integer> {

    Space getSpaceById(Integer spaceId);

}
