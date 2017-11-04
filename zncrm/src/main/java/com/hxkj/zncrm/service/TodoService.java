package com.hxkj.zncrm.service;

import java.util.List;
import java.util.Map;

import com.hxkj.zncrm.dao.domain.TodoEntity;

public interface TodoService {

    public long addTodo(Map<String, String> input);

    public TodoEntity getTodoById(Map<String, String> input);

    public int updateTodoById(Map<String, String> input);

    public int delTodoById(Map<String, String> input);

    public List<TodoEntity> getTodoList(Map<String, String> input);

    public String getTodoCount(Map<String, String> input);
}