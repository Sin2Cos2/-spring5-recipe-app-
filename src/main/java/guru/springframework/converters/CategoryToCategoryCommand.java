package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.domain.Category;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class CategoryToCategoryCommand implements Converter<Category, CategoryCommand> {

    @Synchronized
    @NonNull
    @Override
    public CategoryCommand convert(Category source) {

        if (source == null)
            throw new RuntimeException("Source is null!");

        final CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setDescription(source.getCategoryName());
        categoryCommand.setId(source.getId());

        return categoryCommand;
    }
}
