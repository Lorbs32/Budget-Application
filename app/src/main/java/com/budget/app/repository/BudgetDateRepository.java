package com.budget.app.repository;

import com.budget.app.entity.BudgetDate;
import com.budget.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface BudgetDateRepository extends JpaRepository<BudgetDate, Integer> {
	@NativeQuery(value = "SELECT TOP 1 * FROM BUDGET_DATES BD WHERE ?1 BETWEEN BD.START_DATE AND BD.END_DATE")
	BudgetDate findByBudgetDateBetween(LocalDate currentDate);

	List<BudgetDate> findByIdIn(List<Integer> ids);
}
