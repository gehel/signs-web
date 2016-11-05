package ch.ledcom.signs;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SignRepositoryTest {

    @Autowired private SignRepository signRepository;

    @Test
    public void storeSignsInElasticsearch() {
        signRepository.save(new Sign("hello", "a greeting", "some-image"));
        Sign hello = signRepository.findByName("hello");

        assertThat(hello.getName()).isEqualTo("hello");
        assertThat(hello.getDescription()).isEqualTo("a greeting");
        assertThat(hello.getImage()).isEqualTo("some-image");
    }

    @Test
    public void findByDescription() {
        Page<Sign> fruits = signRepository.findByDescription("fruit", new PageRequest(0, 3));
        assertThat(fruits).hasSize(1);
    }

    @Test
    public void findByAllFields() {
        Page<Sign> fruits = signRepository.findByAllFields("fruit", new PageRequest(0, 3));
        assertThat(fruits).hasSize(1);
    }

}
