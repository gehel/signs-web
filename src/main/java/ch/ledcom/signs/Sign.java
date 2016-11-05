package ch.ledcom.signs;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import static org.springframework.data.elasticsearch.annotations.FieldIndex.analyzed;

@Data
@Document(indexName = "signs")
public class Sign {

    @Id private final String name;
    @Field(index = analyzed) private final String description;
    private final String image;

}
