package ru.surf.core.service.impl

import org.springframework.stereotype.Service
import ru.surf.core.entity.Trainee
import ru.surf.core.repository.TraineeRepository
import ru.surf.core.service.TraineeService
import java.util.*

@Service
class TraineeServiceImpl(private val traineeRepository: TraineeRepository) : TraineeService {

    override fun findTraineesByIds(ids: List<UUID>): MutableList<Trainee> = traineeRepository.findTraineesByIdIn(ids)

}