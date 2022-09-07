package guru.springframework.services;

import guru.springframework.domain.Recipe;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;

@AllArgsConstructor
@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeService recipeService;


    //TODO: Если изменить что то в рецепте, изображение удаляется из БД
    @Transactional
    @Override
    public void saveImageFile(Long id, MultipartFile image) {
        Recipe recipe = recipeService.findById(id);

        try {
            Byte[] arr = new Byte[image.getBytes().length];

            int i = 0;
            for (byte aByte : image.getBytes()) {
                arr[i++] = aByte;
            }

            recipe.setImage(arr);
            recipeService.saveRecipe(recipe);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
