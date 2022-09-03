package guru.springframework.converters;

import guru.springframework.commands.NotesCommand;
import guru.springframework.domain.Notes;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class NotesToNotesCommand implements Converter<Notes, NotesCommand> {

    @Synchronized
    @NonNull
    @Override
    public NotesCommand convert(Notes source) {

        if (source == null)
            throw new RuntimeException("Source is null!");

        final NotesCommand notes = new NotesCommand();
        notes.setRecipeNotes(source.getRecipeNotes());
        notes.setId(source.getId());

        return notes;
    }
}
