package com.budget.app.repository;

import com.budget.app.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {
	@NativeQuery(value = "SELECT * FROM GROUPS G WHERE G.BUDGET_ID = ?1")
	List<Group> findById(int budgetId);
}