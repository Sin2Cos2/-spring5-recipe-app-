package guru.springframework.converters;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import lombok.AllArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

    private final IngredientCommandToIngredient commandToIngredient;
    private final CategoryCommandToCategory commandToCategory;
    private final NotesCommandToNotes commandToNotes;

    @Synchronized
    @NonNull
    @Override
    public Recipe convert(RecipeCommand source) {

        if (source == null)
            throw new RuntimeException("Source is null!");

        final Recipe recipe = new Recipe();
        recipe.setId(source.getId());
        recipe.setDescription(source.getDescription());
        recipe.setPrepTime(source.getPrepTime());
        recipe.setCookTime(source.getCookTime());
        recipe.setServings(source.getServings());
        recipe.setSource(source.getSource());
        recipe.setUrl(source.getUrl());
        recipe.setDirections(source.getDirections());
        recipe.setDifficulty(source.getDifficulty());
        recipe.setNotes(commandToNotes.convert(source.getNotes()));

        if (source.getIngredients() != null && source.getIngredients().size() > 0)
            source.getIngredients()
                    .forEach(ingredient -> recipe.getIngredients().add(commandToIngredient.convert(ingredient)));

        if(source.getCategories() != null && source.getCategories().size() > 0)
            source.getCategories()
                    .forEach(category -> recipe.getCategories().add(commandToCategory.convert(category)));

        int i = 0;

        return recipe;
    }
}
