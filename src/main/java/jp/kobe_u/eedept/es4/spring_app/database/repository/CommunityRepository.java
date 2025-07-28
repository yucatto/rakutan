package jp.kobe_u.eedept.es4.spring_app.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.kobe_u.eedept.es4.spring_app.database.entities.Community;

public interface CommunityRepository extends JpaRepository<Community, String> {
    // Additional query methods can be defined here if needed

}
