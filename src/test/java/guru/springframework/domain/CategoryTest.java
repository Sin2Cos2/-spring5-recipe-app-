package guru.springframework.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    Category category;

    @BeforeEach
    public void setUp(){
        category = new Category();
    }

    @Test
    void getId() {
        Long idValue = 4L;
        category.setId(idValue);

        assertEquals(idValue, category.getId());
    }

    @Test
    void getCategoryName() {
        String description = "Some text";
        category.setCategoryName(description);

        assertEquals(description, category.getCategoryName());
    }

    @Test
    void getRecipes() {
    }
}