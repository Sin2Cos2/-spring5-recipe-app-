package guru.springframework.controllers;

import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
class IndexControllerTest {

    IndexController indexController;

    @Mock
    RecipeService recipeService;
    @Mock
    Model model;

    @BeforeEach
    void setUp() {
        indexController = new IndexController(recipeService);
    }

    //Как тестировать контроллеры TODO: Заметка
    @Test
    public void testMockMVC() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void getIndexPage() {

        Set<Recipe> recipeSet = new HashSet<>();
        recipeSet.add(new Recipe());
        recipeSet.add(Recipe.builder().id(2L).build());

        when(recipeService.getRecipes()).thenReturn(recipeSet);

        ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        String res = indexController.getIndexPage(model);

        assertEquals("index", res);
        verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());
        verify(recipeService, times(1)).getRecipes();
        Set<Recipe> setInController = argumentCaptor.getValue();
        assertEquals(2, setInController.size());
    }
}