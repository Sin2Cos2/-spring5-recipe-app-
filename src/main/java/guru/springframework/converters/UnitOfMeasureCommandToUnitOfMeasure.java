package guru.springframework.converters;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureCommandToUnitOfMeasure implements Converter<UnitOfMeasureCommand, UnitOfMeasure> {

    @NonNull
    @Synchronized
    @Override
    public UnitOfMeasure convert(UnitOfMeasureCommand source) {

        if (source == null)
            throw new RuntimeException("Source is null!");

        final UnitOfMeasure unit = new UnitOfMeasure();
        unit.setUnitOfMeasure(source.getDescription());
        unit.setId(source.getId());

        return unit;
    }
}
