package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryCommandToCategoryTest {

    CategoryCommandToCategory categoryCommandToCategory;

    @BeforeEach
    void setUp() {
        categoryCommandToCategory = new CategoryCommandToCategory();
    }

    @Test
    void convertNull() {
        CategoryCommand command = null;
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> categoryCommandToCategory.convert(command));
        assertTrue(exception.getMessage().contains("Source is null!"));
    }

    @Test
    void convert() {
        CategoryCommand command = new CategoryCommand();
        command.setId(1L);
        command.setDescription("Some text");

        Category res = categoryCommandToCategory.convert(command);
        assertNotNull(res.getId());
        assertEquals(1L, res.getId());
        assertEquals("Some text", res.getCategoryName());

    }
}