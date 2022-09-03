package guru.springframework.converters;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureToUnitOfMeasureCommand implements Converter<UnitOfMeasure, UnitOfMeasureCommand> {

    @NonNull
    @Synchronized
    @Override
    public UnitOfMeasureCommand convert(UnitOfMeasure source) {

        if (source == null)
            throw new RuntimeException("Source is null!");

        final UnitOfMeasureCommand unit = new UnitOfMeasureCommand();
        unit.setDescription(source.getUnitOfMeasure());
        unit.setId(source.getId());

        return unit;
    }
}
