package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.domain.Category;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class CategoryCommandToCategory implements Converter<CategoryCommand, Category> {

    @Synchronized
    @NonNull
    @Override
    public Category convert(CategoryCommand source) {

        if (source == null)
            throw new RuntimeException("Source is null!");

        final Category category = new Category();
        category.setId(source.getId());
        category.setCategoryName(source.getDescription());
        return category;
    }
}
