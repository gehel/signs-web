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

import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

@Data
public class SignResource extends ResourceSupport {

    private final String name;
    private final String description;
    private final String image;

    public static SignResource from(Sign sign) {
        return new SignResource(sign.getName(), sign.getDescription(), sign.getImage());
    }
}
