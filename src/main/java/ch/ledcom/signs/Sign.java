/**
 * Copyright © 2016 Guillaume Lederrey (guillaume.lederrey@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.ledcom.signs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.InputStream;
import java.util.List;

import static org.springframework.data.elasticsearch.annotations.FieldIndex.analyzed;

@Data
@Document(indexName = "signs")
public class Sign {

    @Id private final String name;
    @Field(index = analyzed) private final String description;
    @Field(index = analyzed) private final String definition;
    private final String image;
    private final String configLeft;
    private final String configRight;
    @Field(index = analyzed) private final List<String> tags;

    @JsonIgnore
    public InputStream getImageInputStream() {
        return Sign.class.getResourceAsStream(image);
    }
}
