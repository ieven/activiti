package com.hxkj.zncrm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxkj.zncrm.dao.domain.TodoEntity;
import com.hxkj.zncrm.dao.mapper.TodoMapper;
import com.hxkj.zncrm.service.TodoService;

@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoMapper mapper;

    @Override
    public long addTodo(Map<String, String> input) {

        return mapper.addTodo(input);
    }

    @Override
    public TodoEntity getTodoById(Map<String, String> input) {

        return mapper.getTodoById(input);
    }

    @Override
    public int updateTodoById(Map<String, String> input) {

        return mapper.updateTodoById(input);
    }

    @Override
    public int delTodoById(Map<String, String> input) {

        return mapper.delTodoById(input);
    }

    @Override
    public List<TodoEntity> getTodoList(Map<String, String> input) {

        return mapper.getTodoList(input);
    }

    @Override
    public String getTodoCount(Map<String, String> input) {

        return mapper.getTodoCount(input);
    }

}
