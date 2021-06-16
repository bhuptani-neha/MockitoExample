package org.example;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MockitoRunnerTest {

    @Mock
    TodoService todoService;

    @InjectMocks
    TodoBusinessImpl todoBusiness;

    @Captor
    ArgumentCaptor<String> argumentCaptor;


    @Before
    public void setUp(){
        List<String> allToDos = Arrays.asList("Learn Java MVC", "Learn Java", "Learn to Dance");
        List<String> newAllToDos = Arrays.asList("Learn Java MVC", "Learn", "Learn to Dance");
        when(todoService.retrieveTodos("Neha")).thenReturn(allToDos).thenReturn(newAllToDos).thenThrow(IllegalStateException.class);
        when(todoService.retrieveTodos("XYZ")).thenReturn(allToDos.subList(2,2));
        when(todoService.retrieveTodos("Epam")).thenReturn(allToDos.subList(0,1));
    }

    @Test(expected = IllegalStateException.class)
    public void testRetrieve(){
        List<String> todos = todoBusiness.retrieveTodos("Neha");
        assertEquals(2,todos.size());

        todos = todoBusiness.retrieveTodos("Neha"); // 2nd time Call Neha Size Update
        assertEquals(1,todos.size());

        todos = todoBusiness.retrieveTodos("XYZ");
        assertEquals(0,todos.size());

        todos = todoBusiness.retrieveTodos("Epam");
        assertEquals(1,todos.size());

        todos = todoBusiness.retrieveTodos("Spam"); //Value is not Mocked
        assertEquals(0,todos.size());

        todos = todoBusiness.retrieveTodos("Neha"); // 3nd time Call Neha Throws Exception
        assertEquals(1,todos.size());
    }

    @Test
    public void testDelete(){
        todoBusiness.deleteTodosNotRelated("Neha");

        verify(todoService).deleteTodo("Learn to Dance");

        verify(todoService, Mockito.never()).deleteTodo("Learn Java");

        verify(todoService,Mockito.never()).deleteTodo("Learn Java MVC");

        verify(todoService,Mockito.atLeast(1)).deleteTodo("Learn to Dance");

    }

    @Test
    public void captureArgument(){
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        todoBusiness.deleteTodosNotRelated("Neha");
        Mockito.verify(todoService).deleteTodo(argumentCaptor.capture());
        assertEquals("Learn to Dance",argumentCaptor.getValue());
    }
}
