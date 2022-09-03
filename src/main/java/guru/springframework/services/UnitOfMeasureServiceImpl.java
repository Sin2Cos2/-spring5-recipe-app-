package guru.springframework.services;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.UnitOfMeasureRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureRepository repository;
    private final UnitOfMeasureToUnitOfMeasureCommand toUnitOfMeasureCommand;

    @Override
    public Set<UnitOfMeasureCommand> findAll() {
        Set<UnitOfMeasureCommand> uomCommands = new HashSet<>();
        repository.findAll().forEach(uom -> uomCommands.add(toUnitOfMeasureCommand.convert(uom)));

        return uomCommands;
    }
}
