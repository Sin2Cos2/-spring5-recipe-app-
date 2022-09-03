package guru.springframework.converters;

import guru.springframework.commands.NotesCommand;
import guru.springframework.domain.Notes;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class NotesCommandToNotes implements Converter<NotesCommand, Notes> {

    @Synchronized
    @NonNull
    @Override
    public Notes convert(NotesCommand source) {

        if (source == null)
            throw new RuntimeException("Source is null!");

        final Notes notes = new Notes();
        notes.setRecipeNotes(source.getRecipeNotes());
        notes.setId(source.getId());

        return notes;
    }
}
