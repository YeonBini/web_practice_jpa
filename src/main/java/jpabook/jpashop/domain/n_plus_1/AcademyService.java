package jpabook.jpashop.domain.n_plus_1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AcademyService {

    @Autowired
    private AcademyRepository academyRepository;

    @Transactional(readOnly = true)
    public List<String> findAllSubjectNames() {
        return extractSubjectNames(academyRepository.findAllJoinFetch());
    }

    private List<String> extractSubjectNames(List<Academy> academies) {
        log.info(">>>>>> 모든 과목 추출 <<<<<<");
        log.info("Academy Size : " + academies.size());

        return academies.stream()
                .map(academy -> {
                    List<Subject> subjects = academy.getSubjects();
                    return subjects.get(0).getName();
                })
                .collect(Collectors.toList());
    }
}
