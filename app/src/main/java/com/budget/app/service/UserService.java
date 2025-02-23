package com.budget.app.service;

import com.budget.app.entity.User;

import java.util.List;

public interface UserService
{
    public List<User> getUsers();
    public User getUserByEmail(String email);
}
